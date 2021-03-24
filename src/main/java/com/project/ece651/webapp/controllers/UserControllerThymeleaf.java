//package com.project.ece651.webapp.controllers;
//
//import com.project.ece651.webapp.models.CreateUserRequestModel;
//import com.project.ece651.webapp.models.CreateUserResponseModel;
//import com.project.ece651.webapp.services.UserService;
//import com.project.ece651.webapp.shared.UserDto;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.convention.MatchingStrategies;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
///*TODO:
//    1. change the html file name to be consistent with frontend
//    2. add update and delete controllers
//    3. login
//*/
////@Controller
//@RequestMapping("/user")
//public class UserControllerThymeleaf {
//    private static final String USER_FORM_URL = "users/userform";   // resources/templates/users/userform.html
//
//    private final UserService userService;
//    private final ModelMapper modelMapper;
//
//    Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    public UserControllerThymeleaf(UserService userService, ModelMapper modelMapper) {
//        this.userService = userService;
////        modelMapper = new ModelMapper();
////        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        this.modelMapper = modelMapper;
//    }
//
//    @GetMapping("/add_user")
//    public String addUser(Model model){
//        model.addAttribute("user", new CreateUserRequestModel());
//
//        return USER_FORM_URL;
//    }
//
//    @PostMapping()  // TODO: post URL may change to be consistent with frontend
//    public String createUser(@Valid @ModelAttribute("user") CreateUserRequestModel createUserRequestModel,
//                             BindingResult bindingResult) {
//
//        // for user data input validation
//        if (bindingResult.hasErrors()) {
//
//            bindingResult.getAllErrors().forEach(objectError -> {
//                logger.info(objectError.toString());
//            });
//
//            return USER_FORM_URL;
//        }
//
//        UserDto userDto = modelMapper.map(createUserRequestModel, UserDto.class);
//        UserDto createdUserDto = userService.addUser(userDto);
//
//        return "redirect:/user/" + createdUserDto.getUid();
//    }
//
//    // for Postman: could run without frontend
//    @PostMapping(value = "/add_user-postman",
//            consumes = {MediaType.APPLICATION_JSON_VALUE},
//            produces = {MediaType.APPLICATION_JSON_VALUE}
//    )
//    public ResponseEntity<CreateUserResponseModel> createUserPostman(@Valid @RequestBody CreateUserRequestModel userDetails) {
//
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//
//        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
//        UserDto createdUser = userService.addUser(userDto);
//
//        CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
//    }
//
//    @GetMapping("/{userId}")
//    public String showById(@PathVariable String userId, Model model) {
//
//        model.addAttribute("user", userService.findByUid(userId));
//
//        return "users/show";
//    }
//}
