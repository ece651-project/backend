package com.project.ece651.webapp.services;

import com.project.ece651.webapp.entities.UserEntity;
import com.project.ece651.webapp.repositories.UserRepository;
import com.project.ece651.webapp.shared.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class UserServiceImplTest {
    // TODO: how to include environment in tests?

    @Mock
    UserRepository userRepository;

    UserService userService;
    ModelMapper modelMapper;

    public UserServiceImplTest() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository, new BCryptPasswordEncoder(), modelMapper);
    }

    @Test
    void createUser() {
        // given
        UserDto userDto = new UserDto();
        userDto.setNickname("Tom");
        userDto.setEmail("test@gmail.com");
        userDto.setPassword("asdfghjk");
        userDto.setPhoneNum("1111222233331");

        // when
        UserDto createdUserDto = userService.addUser(userDto);

        // then
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    // TODO: add tests
}