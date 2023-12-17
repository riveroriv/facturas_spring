package edu.upb.facturas.entity.service.impl;

import edu.upb.facturas.dao.entity.UserDto;
import edu.upb.facturas.dao.request.ChangePasswordRequest;
import edu.upb.facturas.entity.model.Role;
import edu.upb.facturas.entity.model.User;
import edu.upb.facturas.entity.repository.UserRepository;
import edu.upb.facturas.entity.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDto user() {
        return getCurrentUser().getDto();
    }

    @Override
    public UserDto update(UserDto user) {
        User foundUser = this.getCurrentUser();
        foundUser.setName(user.getName());
        foundUser.setEmail(user.getEmail());
        foundUser.setUsername(user.getUsername());
        foundUser.setBirthday(user.getBirthday());

        userRepository.save(foundUser);
        return foundUser.getDto();
    }

    @Override
    public UserDto update(String username, Role role) {
        User foundUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        foundUser.setRole(role);
        userRepository.save(foundUser);
        return foundUser.getDto();
    }

    @Override
    public Long deleteAccount() {
        User foundUser = this.getCurrentUser();
        userRepository.delete(foundUser);
        return foundUser.getId();
    }

    @Override
    public UserDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found")).getDto();
    }

    @Override
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found")).getDto();
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(User::getDto).toList();
    }

    private User getCurrentUser() {
        return userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
