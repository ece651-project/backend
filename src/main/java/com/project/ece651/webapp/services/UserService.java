package com.project.ece651.webapp.services;

import com.project.ece651.webapp.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDto addUser(UserDto userDetails);
    UserDto findByUid(String userName);
    UserDto findByEmail(String email);
    void updateUser(UserDto updatedUser);
    void deleteUser(String uid);
}
