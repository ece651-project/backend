package com.project.ece651.webapp.controllers;

import com.project.ece651.webapp.services.UserService;
import com.project.ece651.webapp.shared.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    @Mock
    UserService userService;

    UserController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        controller = new UserController(userService, modelMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    // for Postman
    @Test
    void createUserPostman() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setNickname("Jack");

        when(userService.createUser(any())).thenReturn(userDto);

        String userRequestBody = "{\"userName\":\"aaaaaaaa\",\"email\":\"aaaaaa@163.com\",\"password\":\"11111111\", \"phoneNum\":\"12345678910\"}";

        mockMvc.perform(post("/user/add_user-postman")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequestBody)
        )
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
    }

    // TODO: other tests
}