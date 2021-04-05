package com.project.ece651.webapp.services;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.ImageEntity;
import com.project.ece651.webapp.entities.UserEntity;
import com.project.ece651.webapp.exceptions.ActionNotAllowedException;
import com.project.ece651.webapp.exceptions.ApartmentNotFoundException;
import com.project.ece651.webapp.exceptions.UserNotFoundException;
import com.project.ece651.webapp.repositories.ApartmentRepository;
import com.project.ece651.webapp.repositories.UserRepository;
import com.project.ece651.webapp.shared.ApartmentDto;
import com.project.ece651.webapp.utils.ApartmentUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;

    public ApartmentServiceImpl(UserRepository userRepository, ApartmentRepository apartmentRepository) {
        this.userRepository = userRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public void addApartment(ApartmentDto apartmentDto) throws UserNotFoundException {
        // check whether the landlord is in the database
        UserEntity userEntity = userRepository.findByUid(apartmentDto.getLandlordId());
        if (userEntity != null) {
            // convert the apartmentEto to apartmentEntity
            ApartmentEntity apartmentEntity = ApartmentUtils.apartmentDtoToEntity(apartmentDto);

            // add the apartment into those owned by the user entity
            userEntity.addOwnedApartments(apartmentEntity);

            // store images to the apartment
            if (apartmentDto.getImages() != null && !apartmentDto.getImages().isEmpty()) {
                storeImages(apartmentEntity, apartmentDto.getImages());
            }

            userRepository.save(userEntity);
        }
        else {
            // user not in database case
            throw new UserNotFoundException("Landlord user not in database");
        }
    }

    @Override
    public void updateApartment(long aid, ApartmentDto apartmentDto) throws ApartmentNotFoundException {
        ApartmentEntity apartmentEntity = apartmentRepository.findByAid(aid);
        if (apartmentEntity != null) {
            // apartment in database case
            // check whether the user has right to do the updating
            if (!apartmentDto.getLandlordId().equals(apartmentEntity.getLandlord().getUid()))
                throw new ActionNotAllowedException("User other than landlord has no right to perform the update");
            // update the apartment and persist to database
            ApartmentUtils.apartmentDtoToEntity(apartmentDto, apartmentEntity);
            apartmentRepository.save(apartmentEntity);
        }
        else {
            // apartment not found case
            throw new ApartmentNotFoundException("Apartment to be updated not in database");
        }
    }

    @Override
    public void deleteApartment(String uid, long aid) throws ActionNotAllowedException, ApartmentNotFoundException {
        ApartmentEntity apartmentEntity = apartmentRepository.findByAid(aid);
        if (apartmentEntity != null) {
            UserEntity landlord = apartmentEntity.getLandlord();
            if (!landlord.getUid().equals(uid)) {
                // the user has not right to do the delete case
                throw new ActionNotAllowedException("User other than landlord has no right to perform the delete");
            }
            landlord.getOwnedApartments().remove(apartmentEntity);
            userRepository.save(landlord);
        }
        else {
            // apartment not in the database case
            throw new ApartmentNotFoundException("Apartment to be deleted not in database");
        }
    }

    @Override
    public List<ApartmentDto> listAllApartments() {
        List<ApartmentEntity> apartmentEntities = apartmentRepository.findAll();
        List<ApartmentDto> apartmentDtos = new ArrayList<>();
        for (ApartmentEntity apartmentEntity : apartmentEntities) {
            apartmentDtos.add(ApartmentUtils.apartmentEntityToDto(apartmentEntity));
        }
        return apartmentDtos;
    }

    @Override
    public ApartmentDto findApartmentByAid(long aid) {
        ApartmentEntity apartmentEntity = apartmentRepository.findByAid(aid);
        ApartmentDto apartmentDto = ApartmentUtils.apartmentEntityToDto(apartmentEntity);
        return apartmentDto;
    }

//    @Override
//    public void storeImages(Long aid, MultipartFile[] images) throws IOException {
//        ApartmentEntity apartmentEntity = apartmentRepository.findByAid(aid);
//        for (MultipartFile image : images) {
//            ImageEntity imageEntity = new ImageEntity(image.getContentType(), image.getBytes());
//            apartmentEntity.addImage(imageEntity);
//        }
//        apartmentRepository.save(apartmentEntity);
//    }

    @Override
    public void storeImages(ApartmentEntity apartmentEntity, List<String> images) {
        for (String image: images) {
            ImageEntity imageEntity = new ImageEntity(Base64.getDecoder().decode(image));
            apartmentEntity.addImage(imageEntity);
        }
        apartmentRepository.save(apartmentEntity);
    }
}
