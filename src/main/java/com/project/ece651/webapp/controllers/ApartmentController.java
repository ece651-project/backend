package com.project.ece651.webapp.controllers;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.Type;
import com.project.ece651.webapp.entities.UserEntity;
import com.project.ece651.webapp.repositories.ApartmentRepository;
import com.project.ece651.webapp.repositories.UserRepository;
import com.project.ece651.webapp.shared.ApartmentDto;
import com.project.ece651.webapp.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;

@RestController
@RequestMapping("/apt")
public class ApartmentController {
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/add_apt")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean createApartment(@RequestBody ApartmentDto apartmentDto) {
        // sample url localhost:8080/apt/add_apt
        // add one new apartment according to the given apartment information
        // check whether the landlord is in the database
        UserEntity userEntity = userRepository.findByUid(apartmentDto.getLandlordId());
        if (userEntity != null) {
            ApartmentEntity apartmentEntity = new ApartmentEntity();
            apartmentEntity.setType(apartmentDto.getType());
            apartmentEntity.setAddress(apartmentDto.getAddress());
            apartmentEntity.setDescription(apartmentDto.getDescription());
            apartmentEntity.setPrice(apartmentDto.getPrice());
            // add the apartment into those own by the user entity
            userEntity.addOwnedApartments(apartmentEntity);
            userRepository.save(userEntity);
            return true;
        }
        return false;
    }

    @GetMapping("/get_apt/{aid}")
    @ResponseStatus(HttpStatus.OK)
    public ApartmentDto getApartment(@PathVariable long aid) {
        // sample url localhost:8080/apt/get_apt/1
        ApartmentDto apartmentDto = new ApartmentDto();
        ApartmentEntity apartmentEntity = apartmentRepository.findByAid(aid);
        if (apartmentEntity == null) {
            // apartment not exist case
            // set the response msg to reflect this for now
            apartmentDto.setResponseMsg("Apartment not in database！");
        }
        else {
            // apartment in database case
            apartmentDto.setAid(apartmentEntity.getAid());
            apartmentDto.setLandlordId(apartmentEntity.getLandlord().getUid());
            apartmentDto.setType(apartmentEntity.getType());
            apartmentDto.setAddress(apartmentEntity.getAddress());
            apartmentDto.setUploadTime(apartmentEntity.getUploadTime());
            apartmentDto.setStartMonth(apartmentEntity.getStartMonth());
            apartmentDto.setEndMonth(apartmentEntity.getEndMonth());
            apartmentDto.setDescription(apartmentEntity.getDescription());
            apartmentDto.setPrice(apartmentEntity.getPrice());
        }
        return apartmentDto;
    }
}