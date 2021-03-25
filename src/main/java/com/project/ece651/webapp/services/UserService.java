package com.project.ece651.webapp.services;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.shared.ApartmentDto;
import com.project.ece651.webapp.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDto addUser(UserDto userDetails);
    UserDto findByUid(String userName);
    UserDto findByEmail(String email);
    void updateUser(UserDto updatedUser);
    void deleteUser(String uid);
    void addFav(String uid, ApartmentEntity apartment);
    void delFav(String uid, ApartmentEntity apartment);
    List<ApartmentDto> getFav(String uid);
}
