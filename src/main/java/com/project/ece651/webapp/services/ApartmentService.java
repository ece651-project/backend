package com.project.ece651.webapp.services;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.UserEntity;
import com.project.ece651.webapp.exceptions.ActionNotAllowedException;
import com.project.ece651.webapp.exceptions.ApartmentNotFoundException;
import com.project.ece651.webapp.exceptions.UserNotFoundException;
import com.project.ece651.webapp.repositories.UserRepository;
import com.project.ece651.webapp.shared.ApartmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ApartmentService {

    void addApartment(ApartmentDto apartmentDto) throws UserNotFoundException;
    void updateApartment(long aid, ApartmentDto apartmentDto) throws ApartmentNotFoundException;
    void deleteApartment(String uid, long aid) throws ActionNotAllowedException, ApartmentNotFoundException;
    List<ApartmentDto> listAllApartments();
    ApartmentDto findApartmentByAid(long aid);

    // void storeImages(Long aid, MultipartFile[] images) throws IOException;
    void storeImages(ApartmentEntity apartmentEntity, Set<String> images);
}
