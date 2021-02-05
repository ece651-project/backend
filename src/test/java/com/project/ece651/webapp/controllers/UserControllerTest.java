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

    ModelMapper modelMapper;
    ObjectMapper jsonMapper;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);

        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        jsonMapper = new ObjectMapper();
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

        when(userService.createUser(any())).thenReturn(userDto);

        // in JSON format: the content does not matter
        String userRequestBody = "{\"nickname\":\"bbbbbbbb\",\"email\":\"bbbbbb@163.com\",\"password\":\"22222222\", \"phoneNum\":\"12345678910\"}";

        String response = mockMvc.perform(post("/user/add_user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequestBody)
        )
                .andReturn().getResponse().getContentAsString();
        // should be {"success":true,"msg":"Successfully created the user.","uid":"0d2adfec-1ce9-483c-a1e7-59d31df"}

        UserDto responseDto = jsonMapper.readValue(response, UserDto.class);

        assertTrue(responseDto.isSuccess());
        assertEquals("Successfully created the user.", responseDto.getMsg());
        assertEquals(expectedUid, responseDto.getUid());
    }
}