package com.project.ece651.webapp.services;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.UserEntity;
import com.project.ece651.webapp.repositories.UserRepository;
import com.project.ece651.webapp.shared.UserDto;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class UserServiceImplTest {
    // TODO: how to include environment in tests?

    @Mock
    UserRepository userRepository;

    UserService userService;
    ModelMapper modelMapper;

    // user dto for tests
    UserEntity userEntity;
    UserDto userDto;
    static final String NICKNAME = "Tom";
    static final String EMAIL = "test@gmail.com";
    static final String PASSWORD = "asdfghjk";
    static final String PHONE_NUM = "1111222233331";
    static final String UID = "ae7daad2-2b23-4448-ace6-227e33f03b71";
    // apt dto for tests
    ApartmentEntity apartmentEntity;

    public UserServiceImplTest() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository, new BCryptPasswordEncoder(), modelMapper);

        // given
        userEntity = new UserEntity();
        userEntity.setUid(UID);
        userEntity.setNickname(NICKNAME);
        userEntity.setEmail(EMAIL);
        userEntity.setPhoneNum(PHONE_NUM);

        userDto = new UserDto();
        userDto.setUid(UID);
        userDto.setNickname(NICKNAME);
        userDto.setEmail(EMAIL);
        userDto.setPassword(PASSWORD);
        userDto.setPhoneNum(PHONE_NUM);

        apartmentEntity = new ApartmentEntity();
    }

    @Test
    void addUser() {
        UserDto createdUserDto = userService.addUser(userDto);

        assertNotNull(createdUserDto, "Null UserDto added");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void findByUid() {
        when(userRepository.findByUid(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.findByUid(UID);

        assertNotNull(userDto, "Null UserDto found");
        assertEquals(UID, userDto.getUid());
        verify(userRepository, times(1)).findByUid(anyString());
        verify(userRepository, never()).findAll();
    }

    @Test
    void findByEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.findByEmail(EMAIL);

        assertNotNull(userDto, "Null UserDto returned");
        assertEquals(EMAIL, userDto.getEmail());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, never()).findAll();
    }

    @Test
    void updateUser() {
        when(userRepository.findByUid(anyString())).thenReturn(userEntity);

        userService.updateUser(userDto);

        verify(userRepository, times(1)).findByUid(anyString());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void deleteUser() {
        when(userRepository.findByUid(anyString())).thenReturn(userEntity);

        userService.deleteUser(UID);

        verify(userRepository, times(1)).findByUid(anyString());
        verify(userRepository, times(1)).delete(any(UserEntity.class));
    }

    @Test
    void addFav() {
        when(userRepository.findByUid(anyString())).thenReturn(userEntity);

        userService.addFav(UID, apartmentEntity);

        verify(userRepository, times(1)).findByUid(anyString());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void delFav() {
        when(userRepository.findByUid(anyString())).thenReturn(userEntity);

        userService.addFav(UID, apartmentEntity);

        verify(userRepository, times(1)).findByUid(anyString());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
}