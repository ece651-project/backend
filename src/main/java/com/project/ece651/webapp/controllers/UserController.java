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
    1. add get, update and delete controller methods
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

    @PostMapping(value = "/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
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

    @GetMapping("/get_user/{uid}")
    @ResponseBody
    public String getUser(@PathVariable String uid) throws JsonProcessingException {
        try {
            UserDto user = userService.findByUid(uid);
            if (user == null) {
                String errMsg = "User not found.";
                return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
            } else {
                user.setSuccess(true);
                return jsonMapper.writerWithView(UserDto.GetView.class).writeValueAsString(user);
            }
        } catch (Exception e) {
            String errMsg = "Unknown error.";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }
    }

    // TODO to update a user, the uid of the user is also needed
    @PutMapping(value = "/update_user",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String updateUser(@RequestBody String userJson) throws JsonProcessingException {
        // Extract data from userJson
        UserDto updatedUser = null;
        try {
            updatedUser = jsonMapper.readValue(userJson, UserDto.class);
        } catch (Exception e) {
            String errMsg = "Json processing error.";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }

        if (updatedUser.getUid() == null) {
            String errMsg = "User ID not provided.";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }

        // Check whether the user exists
        UserDto userFound = userService.findByUid(updatedUser.getUid());
        if (userFound == null) {
            String errMsg = "This user does not exist.";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }

        // Update the user
        userService.updateUser(updatedUser);

        return jsonMapper.writeValueAsString(new MsgResponse(true, "User updated."));
    }

    @DeleteMapping("/delete_user/{uid}")
    @ResponseBody
    public String deleteUser(@PathVariable String uid) throws JsonProcessingException {
        // Check if the user exists
        UserDto userFound = userService.findByUid(uid);
        if (userFound == null) {
            String errMsg = "User not found.";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        } else {
            userService.deleteUser(uid);

            String successMsg = "User " + uid + " deleted.";
            return jsonMapper.writeValueAsString(new MsgResponse(true, successMsg));
        }
    }
}

