package com.project.ece651.webapp.services;

import com.project.ece651.webapp.domains.UserEntity;
import com.project.ece651.webapp.repositories.UserRepository;
import com.project.ece651.webapp.shared.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceImplIT {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Test
    public void shouldCreateUser() {
        // UserBootstrap.java is invoked
        Iterable<UserEntity> users = userRepository.findAll();
        for (UserEntity userEntity: users) {
            System.out.println(userEntity.getUserName());   // Li Lei; Han Meimei
        }

        UserDto userDto = new UserDto();
        userDto.setUserName("Lee");
        userDto.setEmail("123@gmail.com");
        userDto.setPassword("abcdefgh");

        UserDto savedUserDto = userService.createUser(userDto);

        UserDto resultUserDto = userService.getUserByUserId(savedUserDto.getUserId());
        assertEquals("Lee", resultUserDto.getUserName());
        assertEquals("123@gmail.com", resultUserDto.getEmail());
        // resultUserDto.getPassword() will return encrypted password
        // assertEquals("abcdefgh", resultUserDto.getPassword());
    }

    // TODO: add tests
}
