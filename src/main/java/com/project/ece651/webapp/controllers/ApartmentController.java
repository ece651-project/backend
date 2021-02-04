package com.project.ece651.webapp.controllers;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.Type;
import com.project.ece651.webapp.entities.UserEntity;
import com.project.ece651.webapp.repositories.ApartmentRepository;
import com.project.ece651.webapp.repositories.UserRepository;
import com.project.ece651.webapp.shared.ApartmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
//            // save the apartment to its table
//            apartmentRepository.save(apartmentEntity);
            return true;
        }
        return false;


        // create an apartment entity based on the given apartment dto
//        ApartmentEntity apartmentEntity = new ApartmentEntity();
//        apartmentEntity.setType(apartmentDto.getType());
//        apartmentEntity.setAddress(apartmentDto.getAddress());
//        apartmentEntity.setDescription(apartmentDto.getDescription());
//        apartmentEntity.setPrice(apartmentDto.getPrice());
//        UserEntity userEntity = userRepository.findByUid(apartmentDto.getLandlordId());
//        userEntity.addOwnedApartments(apartmentEntity);
    }

    @GetMapping("/get_apt/{aid}")
    @ResponseStatus(HttpStatus.OK)
    public ApartmentDto getApartment(@PathVariable long aid) {
        // sample url localhost:8080/apt/get_apt/12345
        // generate a sample apartment and return
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setLandlordId("e7fa3740-05ec-4653-8d64-be0f435fbe2a");
        apartmentDto.setType(Type.APARTMENT);
        apartmentDto.setAddress("Sample address");
        apartmentDto.setDescription("Sample desc");
        apartmentDto.setPrice(12345);
        return apartmentDto;
    }
}