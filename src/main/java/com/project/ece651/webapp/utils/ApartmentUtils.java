package com.project.ece651.webapp.utils;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.ImageEntity;
import com.project.ece651.webapp.shared.ApartmentDto;

import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ApartmentUtils {
    // utils class contain methods to facilitate the conversion between ApartmentEntity and its corresponding Dto
    public static void apartmentDtoToEntity(ApartmentDto apartmentDto, ApartmentEntity apartmentEntity) {
        // extract core information from apartment dto and put the info into the corresponding entity
        // information to extract include type, address, start month, end month, description, and price
        apartmentEntity.setType(apartmentDto.getType());
        apartmentEntity.setVacancy(apartmentDto.getVacancy());
        apartmentEntity.setAddress(apartmentDto.getAddress());
        apartmentEntity.setStartDate(apartmentDto.getStartDate());
        apartmentEntity.setTerm(apartmentDto.getTerm());
        apartmentEntity.setDescription(apartmentDto.getDescription());
        apartmentEntity.setPrice(apartmentDto.getPrice());

        // images are handled in ApartmentServiceImpl::storeImage
    }

    public static ApartmentEntity apartmentDtoToEntity(ApartmentDto apartmentDto) {
        // extract core information from apartment dto and convert it into the corresponding entity
        ApartmentEntity apartmentEntity = new ApartmentEntity();
        apartmentDtoToEntity(apartmentDto, apartmentEntity);
        return apartmentEntity;
    }

    public static ApartmentDto apartmentEntityToDto(ApartmentEntity apartmentEntity) {
        // extract core information from apartment entity and convert it into dto
        // information to extract include aid, landlordId, uploadTime
        // and type, address, start month, end month, description, and price
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setAid(apartmentEntity.getAid());
        apartmentDto.setLandlordId(apartmentEntity.getLandlord().getUid());
        apartmentDto.setType(apartmentEntity.getType());
        apartmentDto.setVacancy(apartmentEntity.getVacancy());
        apartmentDto.setAddress(apartmentEntity.getAddress());
        apartmentDto.setUploadTime(apartmentEntity.getUploadTime());
        apartmentDto.setStartDate(apartmentEntity.getStartDate());
        apartmentDto.setTerm(apartmentEntity.getTerm());
        apartmentDto.setDescription(apartmentEntity.getDescription());
        apartmentDto.setPrice(apartmentEntity.getPrice());

        // deal with images:
        // https://www.baeldung.com/java-base64-image-string
        if (apartmentEntity.getImages() != null && !apartmentEntity.getImages().isEmpty()) {
            Set<String> imageStrings = apartmentEntity.getImages().stream()
                    .map(imageBytes -> Base64.getEncoder().encodeToString(imageBytes.getData()))
                    .collect(Collectors.toSet());
            apartmentDto.setImages(imageStrings);
        }

        return apartmentDto;
    }
}
