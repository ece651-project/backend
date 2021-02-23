//package com.project.ece651.webapp.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.ece651.webapp.services.UserService;
//import com.project.ece651.webapp.services.UserServiceImpl;
//import com.project.ece651.webapp.shared.UserDto;
////import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.convention.MatchingStrategies;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.WebApplicationType;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//
//import static org.mockito.Mockito.mock;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = UserController.class)
////@RunWith(SpringRunner.class)
////@SpringBootTest
////@ContextConfiguration(classes= {TestControllerConfiguration.class})
////@AutoConfigureMockMvc
//public class UserControllerIT {
//
////    @Autowired
////    @Mock
//    UserService userService;
//
//    UserController controller;
//
////    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    WebApplicationContext context;
//
//    ObjectMapper jsonMapper;
//    BCryptPasswordEncoder bCryptPasswordEncoder;
//    ModelMapper modelMapper;
//
//    @BeforeEach
//    void setUp() {
//        // Set up mockMvc
////        userService = mock(UserService.class);
//
//        jsonMapper = new ObjectMapper();
//        bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        controller = new UserController(userService, jsonMapper, bCryptPasswordEncoder, modelMapper);
//        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//
////        UserDto user1 = new UserDto();
////        user1.setNickname("Li Lei");
////        user1.setEmail("lilei@gmail.com");
////        user1.setPassword("leileili");
////        user1.setPhoneNum("1231231231231");
////        userService.addUser(user1);
////
////        UserDto user2 = new UserDto();
////        user2.setNickname("Han Meimei");
////        user2.setEmail("hanmeimei@gmail.com");
////        user2.setPassword("meimeihan");
////        user2.setPhoneNum("4564564564564");
////        userService.addUser(user2);
//
//        jsonMapper = new ObjectMapper();
////        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
//    }
//
//    @Test
//    public void testBeforeEach() throws Exception {
//        UserDto user1 = new UserDto();
//        user1.setNickname("Li Lei");
//        user1.setEmail("lilei@gmail.com");
//        user1.setPassword("leileili");
//        user1.setPhoneNum("1231231231231");
//        userService.addUser(user1);
//
//        UserDto user2 = new UserDto();
//        user2.setNickname("Han Meimei");
//        user2.setEmail("hanmeimei@gmail.com");
//        user2.setPassword("meimeihan");
//        user2.setPhoneNum("4564564564564");
//        userService.addUser(user2);
//        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
//
////        UserDto user = userService.findByEmail("lilei@gmail.com");
////        System.out.println(user.toString());
//
//        String userRequestBody = "{\"email\":\"aaaaa@163.com\"}";
//
//        String response = mockMvc.perform(post("/user/add_user")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(userRequestBody)
//        )
//                .andExpect(status().is2xxSuccessful())
//                .andReturn().getResponse().getContentAsString();
//
//        UserDto responseDto = jsonMapper
//                .readerWithView(UserDto.AddView.class)
//                .readValue(response, UserDto.class);
//    }
//}
