package com.project.ece651.webapp.controllers;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.repositories.ApartmentRepository;
import com.project.ece651.webapp.repositories.UserRepository;
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
    public boolean createApartment() {
        // sample url localhost:8080/apt/add_apt
        return true;
    }

    @GetMapping("/get_apt/{aid}")
    @ResponseStatus(HttpStatus.OK)
    public ApartmentEntity getApartment(@PathVariable long aid) {
        // sample url localhost:8080/apt/get_apt/12345
        ApartmentEntity apartmentEntity = new ApartmentEntity();
        return apartmentEntity;
    }
}