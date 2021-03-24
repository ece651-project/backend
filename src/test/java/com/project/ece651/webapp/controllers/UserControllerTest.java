package com.project.ece651.webapp.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.UserEntity;
import com.project.ece651.webapp.repositories.ApartmentRepository;
import com.project.ece651.webapp.services.UserService;
import com.project.ece651.webapp.shared.ApartmentDto;
import com.project.ece651.webapp.shared.MsgResponse;
import com.project.ece651.webapp.shared.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    ApartmentRepository apartmentRepository;

    UserController controller;

    MockMvc mockMvc;

    ObjectMapper jsonMapper;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        apartmentRepository = mock(ApartmentRepository.class);

        jsonMapper = new ObjectMapper();
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        controller = new UserController(userService, apartmentRepository, jsonMapper, bCryptPasswordEncoder, modelMapper);

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
    void testLoginSuccess() throws Exception {
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
    void testLoginEmailNotExist() throws Exception {

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
    void testLoginPasswordNotMatch() throws Exception {
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

    /* ------------------ getUser() tests ---------------------- */
    @Test
    void testGetUserSuccess() throws Exception {
        // User info
        String uid = "0d2adfec-1ce9-483c-a1e7-59d31df";
        String nickname = "Li Lei";
        String email = "lilei@gmail.com";
        String password = "leileili";
        String phoneNum = "1231231231231";

        // Create User
        UserDto user = new UserDto();
        user.setUid(uid);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNum(phoneNum);
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(password));

        when(userService.findByUid(anyString())).thenReturn(user);

        // Get response message
        String response = mockMvc.perform(get("/user/get_user/" + uid))
                .andReturn().getResponse().getContentAsString();
        UserDto msg = jsonMapper.readValue(response, UserDto.class);

        // Check response message
        assertEquals(uid, msg.getUid());
        assertEquals(nickname, msg.getNickname());
        assertEquals(email, msg.getEmail());
        assertEquals(phoneNum, msg.getPhoneNum());
    }

    @Test
    void testGetUserNotFound() throws Exception {
        // User info
        String uid = "0d2adfec-1ce9-483c-a1e7-59d31df";

        when(userService.findByUid(anyString())).thenReturn(null);

        // Get response message
        String response = mockMvc.perform(get("/user/get_user/" + uid))
                .andReturn().getResponse().getContentAsString();
        UserDto msg = jsonMapper.readValue(response, UserDto.class);

        // Check response message
        assertFalse(msg.isSuccess());
        assertEquals("User not found.", msg.getMsg());
    }

    /* ------------------ updateUser() tests ---------------------- */
    @Test
    void testUpdateUserSuccess() throws Exception {
        UserDto userDto = new UserDto();

        when(userService.findByUid(anyString())).thenReturn(userDto);

        // matters here because it is used in the method body (not by mock objects, but json reader).
        String userRequestBody = "{\"uid\":\"0d2adfec-1ce9-483c-a1e7-59d31dg\"}";

        String response = mockMvc.perform(put("/user/update_user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequestBody)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        // should be {"success":true,"msg":"User updated."}

        MsgResponse msgResponse = jsonMapper
                .readerWithView(MsgResponse.MsgView.class)
                .readValue(response, MsgResponse.class);

        assertTrue(msgResponse.isSuccess());
        assertEquals("User updated.", msgResponse.getMsg());
    }

    @Test
    void testUpdateUserUserNotFound() throws Exception {
        when(userService.findByUid(anyString())).thenReturn(null);

        // Get response message
        String requestBody = "{" +
                "\"uid\": \"aab18deb-c279-46c1-a43b-7bd6a02dd473\"," +
                "\"nickname\": \"bbbbbbbb\"," +
                "\"email\": \"bbbbbb@163.com\"," +
                "\"password\": \"22222222\"," +
                "\"phoneNum\": \"123456789101\"}";
        String reponse = mockMvc.perform(put("/user/update_user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andReturn().getResponse().getContentAsString();
        MsgResponse responseMsg = jsonMapper.readValue(reponse, MsgResponse.class);

        assertFalse(responseMsg.isSuccess());
        assertEquals("This user does not exist.", responseMsg.getMsg());
    }

    @Test
    void testUpdateUserUidNotProvided() throws Exception {
        when(userService.findByUid(anyString())).thenReturn(null);

        // Get response message
        String requestBody = "{" +
                "\"nickname\": \"bbbbbbbb\"," +
                "\"email\": \"bbbbbb@163.com\"," +
                "\"password\": \"22222222\"," +
                "\"phoneNum\": \"123456789101\"}";
        String response = mockMvc.perform(put("/user/update_user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andReturn().getResponse().getContentAsString();
        MsgResponse responseMsg = jsonMapper.readValue(response, MsgResponse.class);

        assertFalse(responseMsg.isSuccess());
        assertEquals("User ID not provided.", responseMsg.getMsg());
    }

    /* ------------------ deleteUser() tests ---------------------- */
    @Test
    void testDeleteUserSuccess() throws Exception {
        UserDto userDto = new UserDto();

        when(userService.findByUid(anyString())).thenReturn(userDto);

        String uidToDelete = "0d2adfec-1ce9-483c-a1e7-59d31df";
        String response = mockMvc.perform(delete("/user/delete_user/" + uidToDelete)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        // should be {"success":true,"msg":"User updated."}

        MsgResponse msgResponse = jsonMapper
                .readerWithView(MsgResponse.MsgView.class)
                .readValue(response, MsgResponse.class);

        assertTrue(msgResponse.isSuccess());
        assertEquals("User " + uidToDelete + " deleted.", msgResponse.getMsg());
    }

    @Test
    void testDeleteUserNotFound() throws Exception {
        // User info
        String uid = "0d2adfec-1ce9-483c-a1e7-59d31df";

        when(userService.findByUid(anyString())).thenReturn(null);

        // Get response message
        String response = mockMvc.perform(delete("/user/delete_user/" + uid))
                .andReturn().getResponse().getContentAsString();
        UserDto msg = jsonMapper.readValue(response, UserDto.class);

        // Check response message
        assertFalse(msg.isSuccess());
        assertEquals("User not found.", msg.getMsg());
    }

    /* ------------- listAllOwnedApartments() tests ----------------- */
    @Test
    void testListAllOwnedApartmentsSuccess() throws Exception {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setUid("1");
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setUid("2");

        List<ApartmentEntity> apartmentEntities = new ArrayList<>();
        ApartmentEntity apartmentEntity1 = new ApartmentEntity();
        apartmentEntity1.setAid(1L);
        apartmentEntity1.setLandlord(userEntity1);
        apartmentEntities.add(apartmentEntity1);
        ApartmentEntity apartmentEntity2 = new ApartmentEntity();
        apartmentEntity2.setAid(2L);
        apartmentEntity2.setLandlord(userEntity2);
        apartmentEntities.add(apartmentEntity2);
        ApartmentEntity apartmentEntity3 = new ApartmentEntity();
        apartmentEntity3.setAid(3L);
        apartmentEntity3.setLandlord(userEntity2);
        apartmentEntities.add(apartmentEntity3);

        when(userService.findByUid(anyString())).thenReturn(new UserDto());
        when(apartmentRepository.findAll()).thenReturn(apartmentEntities);

        String uidForRequest = "2";
        String response = mockMvc.perform(get("/user/get_apt/" + uidForRequest)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        // read json string as an array of objects
        // https://blog.csdn.net/asdfgh0077/article/details/103888370
        List<ApartmentDto> responseDtoList = Arrays.asList(jsonMapper.readValue(response, ApartmentDto[].class));

        assertEquals(2, responseDtoList.size());
    }

    @Test
    void testListAllOwnedApartmentsUserNotFound() throws Exception {
        // User info
        String uid = "0d2adfec-1ce9-483c-a1e7-59d31df";

        when(userService.findByUid(anyString())).thenReturn(null);

        // Get response message
        String response = mockMvc.perform(get("/user/get_apt/" + uid))
                .andReturn().getResponse().getContentAsString();
        UserDto msg = jsonMapper.readValue(response, UserDto.class);

        // Check response message
        assertFalse(msg.isSuccess());
        assertEquals("User not found.", msg.getMsg());
    }

    /* ------------------ addFav() test ---------------------- */
    @Test
    void testAddFavSuccess() throws Exception {
        when(userService.findByUid(anyString())).thenReturn(new UserDto());
        when(apartmentRepository.findByAid(anyLong())).thenReturn(new ApartmentEntity());

        String favUid = "0d2adfec-1ce9-483c-a1e7-59d31df";
        // in JSON format: the content does not matter, but could not be empty.
        String favRequestBody = "{\"uid\":\"0d2adfec-1ce9-483c-a1e7-59d31df\", \"aid\":\"3\"}";

        String response = mockMvc.perform(post("/user/add_fav")
                .contentType(MediaType.APPLICATION_JSON)
                .content(favRequestBody)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        // should be {"success":true,"msg":"Favourite apartment added for user 0d2adfec-1ce9-483c-a1e7-59d31df."}

        MsgResponse responseDto = jsonMapper
                .readerWithView(MsgResponse.MsgView.class)
                .readValue(response, MsgResponse.class);

        assertTrue(responseDto.isSuccess());
        assertEquals("Favourite apartment added for user " + favUid + ".", responseDto.getMsg());
    }

    /* ------------------ getFavs() test ---------------------- */
    @Test
    void testGetFavsSuccess() throws Exception {
        UserDto userDto1 = new UserDto();
        userDto1.setUid("1");
        UserDto userDto2 = new UserDto();
        userDto2.setUid("2");

        List<ApartmentDto> apartmentDtoList = new ArrayList<>();
        ApartmentDto apartmentDto1 = new ApartmentDto();
        apartmentDto1.setAid(1L);
        apartmentDtoList.add(apartmentDto1);
        ApartmentDto apartmentDto2 = new ApartmentDto();
        apartmentDto1.setAid(2L);
        apartmentDtoList.add(apartmentDto2);

        UserDto userDtoForRequest = new UserDto();
        userDtoForRequest.setUid("3");
        userDtoForRequest.setFavoriteApartments(apartmentDtoList);

        when(userService.findByUid(anyString())).thenReturn(userDtoForRequest);

        String response = mockMvc.perform(get("/user/get_fav/" + userDtoForRequest.getUid())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        // read json string as an array of objects
        // https://blog.csdn.net/asdfgh0077/article/details/103888370
        List<ApartmentDto> responseDtoList = Arrays.asList(jsonMapper.readValue(response, ApartmentDto[].class));

        assertEquals(userDtoForRequest.getFavoriteApartments().size(), responseDtoList.size());
    }

    /* ------------------ deleteFav() test ---------------------- */
    @Test
    void testDeleteFavSuccess() throws Exception {
        when(userService.findByUid(anyString())).thenReturn(new UserDto());
        when(apartmentRepository.findByAid(anyLong())).thenReturn(new ApartmentEntity());

        String favUid = "0d2adfec-1ce9-483c-a1e7-59d31df";
        long favAid = 3;

        String response = mockMvc.perform(delete("/user/delete_fav/" + favUid + "/" + favAid)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        // should be {"success":true,"msg":""Favourite apartment deleted for user 0d2adfec-1ce9-483c-a1e7-59d31df."}

        MsgResponse responseDto = jsonMapper
                .readerWithView(MsgResponse.MsgView.class)
                .readValue(response, MsgResponse.class);

        assertTrue(responseDto.isSuccess());
        assertEquals("Favourite apartment deleted for user " + favUid + ".", responseDto.getMsg());
    }
}