package com.project.ece651.webapp.services;

import com.project.ece651.webapp.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDetails);
    UserDto getUserByUserId(String userName);
    UserDto getUserByEmail(String email);
}
