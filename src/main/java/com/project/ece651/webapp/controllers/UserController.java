package com.project.ece651.webapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.repositories.ApartmentRepository;
import com.project.ece651.webapp.shared.ApartmentDto;
import com.project.ece651.webapp.shared.MsgResponse;
import com.project.ece651.webapp.services.UserService;
import com.project.ece651.webapp.shared.UserDto;
import com.project.ece651.webapp.shared.UserFavDto;
import com.project.ece651.webapp.utils.ApartmentUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/*TODO:
    1. add get, update and delete controller methods
*/
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ApartmentRepository apartmentRepository;
    private final ObjectMapper jsonMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserController(UserService userService, ApartmentRepository apartmentRepository, ObjectMapper jsonMapper,
                          BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userService = userService;
        this.apartmentRepository = apartmentRepository;
        this.jsonMapper = jsonMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String login(@RequestBody String loginJson) throws JsonProcessingException {
        // read info from the request body
        UserDto userDto;
        try {
            userDto = jsonMapper
                    .readerWithView(UserDto.LoginView.class)
                    .readValue(loginJson, UserDto.class);
        } catch (IOException e) {
            String errMsg = "Json processing error.";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }
        String email = userDto.getEmail();
        String password = userDto.getPassword();

        // find the user in DB
        UserDto foundUserDto = userService.findByEmail(email);

        if (foundUserDto == null) {
            String errMsg = "User account with this email does not exist!";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }
        if (!bCryptPasswordEncoder.matches(password, foundUserDto.getEncryptedPassword())) {
            String errMsg = "Password is incorrect!";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }

        // login successfully
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
            // read info from the request body
            UserDto userDto;
            try {
                userDto = jsonMapper.readValue(userJson, UserDto.class);
            } catch (JsonProcessingException e) {
                String errMsg = "Json processing error.";
                return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
            }

            // add the new user to the DB
            UserDto createdUserDto = userService.addUser(userDto);

            // add the new user successfully
            createdUserDto.setSuccess(true);
            createdUserDto.setMsg("Successfully created the user.");
            return jsonMapper
                    .writerWithView(UserDto.AddView.class)
                    .writeValueAsString(createdUserDto);

        } catch (JsonProcessingException e) {

            String errMsg = "Json processing error.";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        } catch (Exception e) {
            e.printStackTrace();

            // Assumes that other constrains including length and email format have been checked by frontend
            // only consider uniqueness here
            String errMsg = e.getMessage();
            if (errMsg.contains("unique_email")) {
                errMsg = "You have already signed up an account with this email!";
            } else if (errMsg.contains("unique_nickname")) {
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

    @GetMapping("/get_apt/{uid}")
    @ResponseBody
    public String listAllOwnedApartments(@PathVariable String uid) throws JsonProcessingException {
        // Check if the user exists
        UserDto userFound = userService.findByUid(uid);
        if (userFound == null) {
            String errMsg = "User not found.";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        } else {
            List<ApartmentEntity> apartmentEntities = apartmentRepository.findAll();

            // Find the apartments that belong to this user
            List<ApartmentDto> apartmentDtos = apartmentEntities.stream()
                    .filter(apartmentEntity -> apartmentEntity.getLandlord().getUid().equals(uid))
                    .map(apartmentEntity -> ApartmentUtils.apartmentEntityToDto(apartmentEntity))
                    .collect(Collectors.toList());

            return jsonMapper.writeValueAsString(apartmentDtos);
        }
    }

    @PostMapping(value = "/add_fav",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String addFav(@RequestBody String addFavJson) throws JsonProcessingException {
        // Obtain the Json data
        UserFavDto userFav;
        try {
            userFav = jsonMapper.readValue(addFavJson, UserFavDto.class);
        } catch (IOException e) {
            String errMsg = "Json processing error.";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }

        // Check whether the user exists
        UserDto userFound = userService.findByUid(userFav.getUid());
        if (userFound == null) {
            String errMsg = "This user does not exist.";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }

        // Check whether the apartment exists
        ApartmentEntity apartment = apartmentRepository.findByAid(userFav.getAid());
        if (apartment == null) {
            String errMsg = "This apartment does not exist.";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }

        // Add the favourite apartment to the user
        userService.addFav(userFav.getUid(), apartment);

        String returnMsg = "Favourite apartment added for user " + userFav.getUid() + ".";
        return jsonMapper.writeValueAsString(new MsgResponse(true, returnMsg));
    }

    @DeleteMapping("/delete_fav/{uid}/{aid}")
    @ResponseBody
    public String deleteFav(@PathVariable String uid, @PathVariable Long aid) throws JsonProcessingException {
        // Check if the user exists
        UserDto userFound = userService.findByUid(uid);
        if (userFound == null) {
            String errMsg = "User not found.";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }

        // Check if the apartment exists
        ApartmentEntity apartment = apartmentRepository.findByAid(aid);
        if (apartment == null) {
            String errMsg = "This apartment does not exist.";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }

        // Delete the favourite apartment to the user
        userService.delFav(uid, apartment);

        String returnMsg = "Favourite apartment deleted for user " + uid + ".";
        return jsonMapper.writeValueAsString(new MsgResponse(true, returnMsg));
    }

    @GetMapping("/get_fav/{uid}")
    @ResponseBody
    public String getFavs(@PathVariable String uid) throws JsonProcessingException {
        // Check if the user exists
        UserDto userFound = userService.findByUid(uid);
        if (userFound == null) {
            String errMsg = "User not found.";
            return jsonMapper.writeValueAsString(new MsgResponse(false, errMsg));
        }

        /* this is wrong when single-side many-to-many: Infinite recursion (StackOverflowError) (through reference chain) */
//        List<ApartmentEntity> apartments = userFound.getFavoriteApartments();
//
//        List<ApartmentDto> apartmentDtos = apartments.stream()
//                .map(apartmentEntity -> modelMapper.map(apartmentEntity, ApartmentDto.class))
//                .collect(Collectors.toList());

        List<ApartmentDto> apartmentDtos = userFound.getFavoriteApartments();

        return jsonMapper.writeValueAsString(apartmentDtos);
    }
}

