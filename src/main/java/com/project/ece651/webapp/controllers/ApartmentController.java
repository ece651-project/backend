package com.project.ece651.webapp.controllers;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.UserEntity;
import com.project.ece651.webapp.services.ApartmentService;
import com.project.ece651.webapp.services.UserService;
import com.project.ece651.webapp.shared.ApartmentDto;
import com.project.ece651.webapp.shared.MsgDto;
import com.project.ece651.webapp.shared.UserDto;
import com.project.ece651.webapp.utils.ApartmentUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// reference https://www.callicoder.com/spring-boot-file-upload-download-jpa-hibernate-mysql-database-example/
// for image uploading, downloading and storing in database

@RestController
@RequestMapping("/apt")
public class ApartmentController {

    private final ApartmentService apartmentServiceImpl;

    private final UserService userServiceImpl;

    private final ModelMapper modelMapper;

    public ApartmentController(ApartmentService apartmentServiceImpl, UserService userServiceImpl, ModelMapper modelMapper) {
        this.apartmentServiceImpl = apartmentServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.modelMapper = modelMapper;
    }

    /*
        Example request body:
        {
            "landlordId": "12601d30-b1f6-448f-b3bc-a9acc4802ad8",
            "type": null,
            "address": "Empty address",
            "uploadTime": null,
            "startMonth": null,
            "endMonth": "2014-02-24",
            "description": "Empty description",
            "price": 10086
        }
    */
    @PostMapping("/add_apt")
    @ResponseStatus(HttpStatus.CREATED)
    public MsgDto createApartment(@RequestBody ApartmentDto apartmentDto) {
        // sample url localhost:8080/apt/add_apt
        // add one new apartment according to the given apartment information
        MsgDto response = new MsgDto();
        try {
            apartmentServiceImpl.addApartment(apartmentDto);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setResponseMsg(e.getMessage());
        }
        return response;
    }

    @PutMapping("/update_apt/{aid}")
    @ResponseStatus(HttpStatus.OK)
    public MsgDto updateApartment(@PathVariable long aid, @RequestBody ApartmentDto apartmentDto) {
        // sample url localhost:8080/apt/update_apt/1
        // add one new apartment according to the given apartment information
        MsgDto response = new MsgDto();
        try {
            apartmentServiceImpl.updateApartment(aid, apartmentDto);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setResponseMsg(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/delete_apt/{uid}/{aid}")
    @ResponseStatus(HttpStatus.OK)
    public MsgDto deleteApartment(@PathVariable String uid, @PathVariable long aid) {
        // sample url localhost:8080/apt/delete_apt/12601d30-b1f6-448f-b3bc-a9acc4802ad8/1
        MsgDto response = new MsgDto();
        ApartmentDto apartmentDto = apartmentServiceImpl.findApartmentByAid(aid);
        try {
            // delete related fav-apt relationship
            List<UserDto> userDtos = userServiceImpl.findAll();
            for (UserDto userDto: userDtos) {
                // Delete the favourite apartment to the user
                userServiceImpl.delFav(userDto.getUid(), modelMapper.map(apartmentDto, ApartmentEntity.class));
            }

            apartmentServiceImpl.deleteApartment(uid, aid);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setResponseMsg(e.getMessage());
        }
        return response;
    }

    @GetMapping("/get_all")
    @ResponseStatus(HttpStatus.OK)
    public List<ApartmentDto> getAllApartments() {
        // sample url localhost:8080/apt/get_all
        return apartmentServiceImpl.listAllApartments();
    }

    @GetMapping("/get_apt/{aid}")
    @ResponseStatus(HttpStatus.OK)
    public ApartmentDto getApartment(@PathVariable long aid) {
        // sample url localhost:8080/apt/get_apt/1
        return apartmentServiceImpl.findApartmentByAid(aid);
    }

    // experiment with images files
    // should by no means be included in the released code
    @PostMapping("/add_apt_imgs/{aid}")
    @ResponseStatus(HttpStatus.CREATED)
    public MsgDto addApartmentImgs(@PathVariable long aid, @RequestParam("images") MultipartFile[] images) {
        // sample url localhost:8080/apt/add_apt_imgs/3
        MsgDto response = new MsgDto();
        try {
            apartmentServiceImpl.storeImages(aid, images);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setResponseMsg("Image added to apartment error case");
            return response;
        }
        return response;
    }

}