package com.project.ece651.webapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.*;
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
        ObjectMapper jsonMapper = new ObjectMapper();
        controller = new UserController(userService, jsonMapper, modelMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    /* ------------------ addUser() tests ---------------------- */
    // cover different branches of addUser() method
    @Test
    void testAddUserSuccess() throws Exception {
        UserDto userDto = new UserDto();
        String expectedUid = "0d2adfec-1ce9-483c-a1e7-59d31df";
        userDto.setUid(expectedUid);
        userDto.setNickname("aaaaaaaa");
        userDto.setEmail("aaaaaa@163.com");
        userDto.setPhoneNum("1111111111111");
        userDto.setPassword("11111111");
        userDto.setEncryptedPassword("$$$$$");

        when(userService.createUser(any())).thenReturn(userDto);

        // in JSON format: the content does not matter
        String userRequestBody = "{\"nickname\":\"bbbbbbbb\",\"email\":\"bbbbbb@163.com\",\"password\":\"22222222\", \"phoneNum\":\"12345678910\"}";

        String res = mockMvc.perform(post("/user/add_user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequestBody)
        )
                .andReturn().getResponse().getContentAsString();
        String resUid = res.substring(1, res.length() - 1); // remove the leading and trailing double quote

        assertEquals(expectedUid, resUid);
    }
}