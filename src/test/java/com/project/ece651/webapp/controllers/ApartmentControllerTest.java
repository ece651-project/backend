package com.project.ece651.webapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ece651.webapp.services.ApartmentService;
import com.project.ece651.webapp.shared.ApartmentDto;
import com.project.ece651.webapp.shared.MsgDto;
import com.project.ece651.webapp.shared.MsgResponse;
import com.project.ece651.webapp.shared.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles("test")
public class ApartmentControllerTest {
    @Mock
    ApartmentService apartmentService;
    MockMvc mockMvc;
    ObjectMapper jsonMapper;
    ApartmentController apartmentController;

    @BeforeEach
    void setup() {
        apartmentService = mock(ApartmentService.class);
        jsonMapper = new ObjectMapper();
        apartmentController = new ApartmentController(apartmentService);
        mockMvc = MockMvcBuilders.standaloneSetup(apartmentController).build();
    }

    @Test
    void testCreateApartmentSuccess() throws Exception {
        ApartmentDto apartmentDto = new ApartmentDto();
        // in JSON format: the content does not matter, but could not be empty.
        String apartmentRequestBody = "{}";
        String response = mockMvc.perform(post("/apt/add_apt")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(apartmentRequestBody)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        MsgDto msgDto = jsonMapper.readValue(response, MsgDto.class);
        assertTrue(msgDto.isSuccess());





//        UserDto userDto = new UserDto();
//        String expectedUid = "0d2adfec-1ce9-483c-a1e7-59d31df";
//        userDto.setUid(expectedUid);
//
//        when(userService.addUser(any())).thenReturn(userDto);
//
//        // in JSON format: the content does not matter, but could not be empty.
//        String userRequestBody = "{\"email\":\"bbbbbb@163.com\"}";
//
//        String response = mockMvc.perform(post("/user/add_user")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(userRequestBody)
//        )
//                .andExpect(status().is2xxSuccessful())
//                .andReturn().getResponse().getContentAsString();
//        // should be {"success":true,"msg":"Successfully created the user.","uid":"0d2adfec-1ce9-483c-a1e7-59d31df"}
//
//        UserDto responseDto = jsonMapper
//                .readerWithView(UserDto.AddView.class)
//                .readValue(response, UserDto.class);
//
//        assertTrue(responseDto.isSuccess());
//        assertEquals("Successfully created the user.", responseDto.getMsg());
//        assertEquals(expectedUid, responseDto.getUid());
    }

}
