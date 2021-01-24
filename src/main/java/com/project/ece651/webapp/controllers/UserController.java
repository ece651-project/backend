package com.project.ece651.webapp.controllers;

import com.project.ece651.webapp.models.CreateUserRequestModel;
import com.project.ece651.webapp.models.CreateUserResponseModel;
import com.project.ece651.webapp.services.UserService;
import com.project.ece651.webapp.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final String USER_FORM_URL = "users/userform";   // resources/templates/users/userform.html

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService) {
        this.userService = userService;
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user", new CreateUserRequestModel());

        return USER_FORM_URL;
    }

    @PostMapping()
    public String createUser(@Valid @ModelAttribute("user") CreateUserRequestModel createUserRequestModel,
                             BindingResult bindingResult) {

        // for user data input validation
        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> {
                logger.info(objectError.toString());
            });

            return USER_FORM_URL;
        }

        UserDto userDto = modelMapper.map(createUserRequestModel, UserDto.class);
        UserDto createdUserDto = userService.createUser(userDto);

        return "redirect:/users/" + createdUserDto.getUid() + "/show";
    }

    // for Postman: could run without frontend
    @PostMapping(value = "/create-postman",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CreateUserResponseModel> createUserPostman(@Valid @RequestBody CreateUserRequestModel userDetails) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);

        CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping("/{userId}/show")
    public String showById(@PathVariable String userId, Model model) {

        model.addAttribute("user", userService.getUserByUserId(userId));

        return "users/show";
    }

//    TODO:
//    1. login
//    2. update
//    3. delete
}
