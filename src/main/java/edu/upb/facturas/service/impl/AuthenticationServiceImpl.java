package edu.upb.facturas.service.impl;

import edu.upb.facturas.dao.request.ChangePasswordRequest;
import edu.upb.facturas.dao.request.SignUpRequest;
import edu.upb.facturas.dao.request.SigninRequest;
import edu.upb.facturas.dao.response.JwtAuthenticationResponse;
import edu.upb.facturas.entity.model.Role;
import edu.upb.facturas.entity.model.User;
import edu.upb.facturas.entity.repository.UserRepository;
import edu.upb.facturas.service.AuthenticationService;
import edu.upb.facturas.service.JwtService;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var userExists = userRepository.findByUsername(request.getUsername());

        if( userExists.isPresent()){
            throw new DataIntegrityViolationException("User already exists");
        }

        var user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setBirthday(request.getBirthday());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public Boolean changePassword(ChangePasswordRequest changePasswordRequest) {
        User foundUser = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
            ).orElseThrow(() -> new IllegalArgumentException("User not found"));
        foundUser.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(foundUser);
        return true;
    }
}