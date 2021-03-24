package com.project.ece651.webapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ece651.webapp.exceptions.ActionNotAllowedException;
import com.project.ece651.webapp.exceptions.ApartmentNotFoundException;
import com.project.ece651.webapp.exceptions.UserNotFoundException;
import com.project.ece651.webapp.services.ApartmentService;
import com.project.ece651.webapp.services.UserService;
import com.project.ece651.webapp.shared.MsgDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles("test")
public class ApartmentControllerTest {
    @Mock
    ApartmentService apartmentService;

    @Mock
    UserService userService;

    MockMvc mockMvc;
    ObjectMapper jsonMapper;
    ApartmentController apartmentController;
    ModelMapper modelMapper;

    @BeforeEach
    void setup() {
        apartmentService = mock(ApartmentService.class);
        jsonMapper = new ObjectMapper();
        apartmentController = new ApartmentController(apartmentService, userService, modelMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(apartmentController).build();
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /* ------------------ createApartment() tests ---------------------- */
    @Test
    void testCreateApartmentSuccess() throws Exception {
        // mock behavior
        doNothing().when(apartmentService).addApartment(any());
        // in JSON format
        String apartmentRequestBody = "{\"landlordId\": \"12601d30-b1f6-448f-b3bc-a9acc4802ad8\"}";
        String response = mockMvc.perform(post("/apt/add_apt")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(apartmentRequestBody)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        MsgDto msgDto = jsonMapper.readValue(response, MsgDto.class);
        assertTrue(msgDto.isSuccess());
    }

    @Test
    void testCreateApartmentNotFound() throws Exception {
        // mock behavior
        doThrow(UserNotFoundException.class).when(apartmentService).addApartment(any());
        // in JSON format
        String apartmentRequestBody = "{\"landlordId\": \"12601d30-b1f6-448f-b3bc-a9acc4802ad8\"}";
        String response = mockMvc.perform(post("/apt/add_apt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(apartmentRequestBody)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        MsgDto msgDto = jsonMapper.readValue(response, MsgDto.class);
        assertFalse(msgDto.isSuccess());
    }

    /* ------------------ updateApartment() tests ---------------------- */
    @Test
    void testUpdateApartmentSuccess() throws Exception {
        long expectedAid = 1;
        // mock behavior
        doNothing().when(apartmentService).updateApartment(eq(expectedAid), any());
        // in JSON format
        String apartmentRequestBody = "{\"landlordId\": \"12601d30-b1f6-448f-b3bc-a9acc4802ad8\"}";
        String response = mockMvc.perform(put("/apt/update_apt/" + expectedAid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(apartmentRequestBody)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        MsgDto msgDto = jsonMapper.readValue(response, MsgDto.class);
        assertTrue(msgDto.isSuccess());
    }

    @Test
    void testUpdateApartmentNotFound() throws Exception {
        long expectedAid = 1;
        // mock behavior
        String msg = "Apartment to be updated not in database";
        doThrow(new ApartmentNotFoundException(msg)).when(apartmentService).updateApartment(eq(expectedAid), any());
        // in JSON format
        String apartmentRequestBody = "{\"landlordId\": \"12601d30-b1f6-448f-b3bc-a9acc4802ad8\"}";
        String response = mockMvc.perform(put("/apt/update_apt/" + expectedAid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(apartmentRequestBody)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        MsgDto msgDto = jsonMapper.readValue(response, MsgDto.class);
        assertFalse(msgDto.isSuccess());
        assertEquals(msg, msgDto.getResponseMsg());
    }

    /* ------------------ deleteApartment() tests ---------------------- */
    @Test
    void testDeleteApartmentSuccess() throws Exception {
        String expectedUid = "111222333";
        long expectedAid = 1;
        // mock behavior
        doNothing().when(apartmentService).deleteApartment(eq(expectedUid), eq(expectedAid));
        // in JSON format
        String apartmentRequestBody = "";
        String response = mockMvc.perform(delete("/apt/delete_apt/" + expectedUid + "/" + expectedAid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(apartmentRequestBody)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        MsgDto msgDto = jsonMapper.readValue(response, MsgDto.class);
        assertTrue(msgDto.isSuccess());
    }

    @Test
    void testDeleteApartmentNotFound() throws Exception {
        String expectedUid = "111222333";
        long expectedAid = -1;
        // mock behavior
        String msg = "Apartment to be deleted not in database";
        doThrow(new ApartmentNotFoundException(msg)).when(apartmentService).
                deleteApartment(any(), eq(expectedAid));
        // in JSON format
        String apartmentRequestBody = "";
        String response = mockMvc.perform(delete("/apt/delete_apt/" + expectedUid + "/" + expectedAid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(apartmentRequestBody)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        MsgDto msgDto = jsonMapper.readValue(response, MsgDto.class);
        assertFalse(msgDto.isSuccess());
        assertEquals(msg, msgDto.getResponseMsg());
    }

    @Test
    void testDeleteApartmentNoRight() throws Exception {
        String expectedUid = "111222333";
        long expectedAid = 1;
        // mock behavior
        String msg = "User other than landlord has not right to perform the delete";
        doThrow(new ActionNotAllowedException(msg)).when(apartmentService).
                deleteApartment(any(), eq(expectedAid));
        // in JSON format
        String apartmentRequestBody = "";
        String response = mockMvc.perform(delete("/apt/delete_apt/" + expectedUid + "/" + expectedAid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(apartmentRequestBody)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        MsgDto msgDto = jsonMapper.readValue(response, MsgDto.class);
        assertFalse(msgDto.isSuccess());
        assertEquals(msg, msgDto.getResponseMsg());
    }
}
