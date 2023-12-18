package edu.upb.facturas.service;

import edu.upb.facturas.dao.entity.UserDto;
import edu.upb.facturas.dao.request.ChangePasswordRequest;
import edu.upb.facturas.entity.model.Role;
import edu.upb.facturas.entity.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();

    UserDto update(UserDto user);

    UserDto user();
    UserDto update(String username, Role role);
    Long deleteAccount();
    UserDto findByUsername(String username);
    UserDto findById(Long id);
    List<UserDto> findAll();


}