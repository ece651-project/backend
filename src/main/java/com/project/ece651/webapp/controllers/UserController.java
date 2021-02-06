package com.project.ece651.webapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ece651.webapp.shared.MsgResponse;
import com.project.ece651.webapp.services.UserService;
import com.project.ece651.webapp.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/*TODO:
    1. change the html file name to be consistent with frontend
    2. add update and delete controller methods
    3. login
*/
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ObjectMapper jsonMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserController(UserService userService, ObjectMapper jsonMapper,
                          BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userService = userService;
        this.jsonMapper = jsonMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody String loginJson) throws IOException {
        UserDto userDto = jsonMapper
                .readerWithView(UserDto.LoginView.class)
                .readValue(loginJson, UserDto.class);
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        UserDto foundUserDto = userService.findByEmail(email);
        if (foundUserDto == null) {
            String errMsg = "User account with this email does not exist!";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }
        if (!bCryptPasswordEncoder.matches(password, foundUserDto.getEncryptedPassword())) {
            String errMsg = "Password is incorrect!";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }
        foundUserDto.setSuccess(true);
        foundUserDto.setMsg("Login successfully!");
        return jsonMapper
                .writerWithView(UserDto.AddView.class)
                .writeValueAsString(foundUserDto);
    }

    // @JsonView(UserDto.AddView.class) // does not work
    @PostMapping(value = "/add_user",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String addUser(@RequestBody String userJson) throws JsonProcessingException {
        try {
            UserDto userDto = jsonMapper.readValue(userJson, UserDto.class);
            UserDto createdUserDto = userService.addUser(userDto);
            createdUserDto.setSuccess(true);
            createdUserDto.setMsg("Successfully created the user.");
            return jsonMapper
                    .writerWithView(UserDto.AddView.class)
                    .writeValueAsString(createdUserDto);
        } catch (Exception e) {
            // Assumes that other constrains including length and email format have been checked by frontend
            // only consider uniqueness here
            String errMsg = e.getMessage();
            if (errMsg.contains("EMAIL")) {
                errMsg = "You have already signed up an account with this email!";
            } else if (errMsg.contains("NICKNAME")) {
                errMsg = "This nickname has already been used! Please Change another one!";
            }
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }
    }
}

