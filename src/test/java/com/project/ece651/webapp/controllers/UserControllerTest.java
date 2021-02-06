package com.project.ece651.webapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ece651.webapp.services.UserService;
import com.project.ece651.webapp.shared.MsgResponse;
import com.project.ece651.webapp.shared.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    @Mock
    UserService userService;

    UserController controller;

    MockMvc mockMvc;

    ObjectMapper jsonMapper;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);

        jsonMapper = new ObjectMapper();
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        controller = new UserController(userService, jsonMapper, bCryptPasswordEncoder, modelMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    /* ------------------ addUser() test ---------------------- */
    @Test
    void testAddUserSuccess() throws Exception {
        UserDto userDto = new UserDto();
        String expectedUid = "0d2adfec-1ce9-483c-a1e7-59d31df";
        userDto.setUid(expectedUid);

        when(userService.addUser(any())).thenReturn(userDto);

        // in JSON format: the content does not matter, but could not be empty.
        String userRequestBody = "{\"email\":\"bbbbbb@163.com\"}";

        String response = mockMvc.perform(post("/user/add_user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequestBody)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        // should be {"success":true,"msg":"Successfully created the user.","uid":"0d2adfec-1ce9-483c-a1e7-59d31df"}

        UserDto responseDto = jsonMapper
                .readerWithView(UserDto.AddView.class)
                .readValue(response, UserDto.class);

        assertTrue(responseDto.isSuccess());
        assertEquals("Successfully created the user.", responseDto.getMsg());
        assertEquals(expectedUid, responseDto.getUid());
    }

    /* ------------------ login() tests ---------------------- */
    // cover diff branches
    @Test
    void loginSuccess() throws Exception {
        UserDto userDto = new UserDto();
        String expectedUid = "0d2adfec-1ce9-483c-a1e7-59d31df";
        String email = "lilei@gmail.com";
        String password = "leileili";
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        userDto.setUid(expectedUid);
        userDto.setEmail(email);
        userDto.setPassword(password);
        userDto.setEncryptedPassword(encryptedPassword);

        when(userService.findByEmail(anyString())).thenReturn(userDto);

        String userRequestBody = "{\"email\":\"lilei@gmail.com\", \"nickname\":\"Li Lei\", " +
                "\"password\":\"leileili\"}";

        String response = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequestBody)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        UserDto responseDto = jsonMapper
                .readerWithView(UserDto.AddView.class)
                .readValue(response, UserDto.class);

        assertTrue(responseDto.isSuccess());
        assertEquals("Login successfully!", responseDto.getMsg());
        assertEquals(expectedUid, responseDto.getUid());
    }

    @Test
    void loginEmailNotExist() throws Exception {

        when(userService.findByEmail(anyString())).thenReturn(null);

        String userRequestBody = "{\"email\":\"lilei@gmail.com\", \"nickname\":\"Li Lei\", " +
                "\"password\":\"leileili\"}";

        String response = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequestBody)
        )
                .andReturn().getResponse().getContentAsString();

        MsgResponse responseMsg = jsonMapper
                .readValue(response, MsgResponse.class);

        assertFalse(responseMsg.isSuccess());
        assertEquals("User account with this email does not exist!", responseMsg.getMsg());
    }

    @Test
    void loginPasswordNotMatch() throws Exception {
        UserDto userDto = new UserDto();
        String expectedUid = "0d2adfec-1ce9-483c-a1e7-59d31df";
        String email = "lilei@gmail.com";
        String password = "leileili";
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        userDto.setUid(expectedUid);
        userDto.setEmail(email);
        userDto.setPassword(password);
        userDto.setEncryptedPassword(encryptedPassword);

        userDto.setSuccess(true);

        when(userService.findByEmail(anyString())).thenReturn(userDto);

        String userRequestBody = "{\"email\":\"lilei@gmail.com\", \"nickname\":\"Li Lei\", " +
                "\"password\":\"leileili5\"}";

        String response = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequestBody)
        )
                .andReturn().getResponse().getContentAsString();

        MsgResponse responseMsg = jsonMapper
                .readValue(response, MsgResponse.class);

        assertFalse(responseMsg.isSuccess());
        assertEquals("Password is incorrect!", responseMsg.getMsg());
    }
}