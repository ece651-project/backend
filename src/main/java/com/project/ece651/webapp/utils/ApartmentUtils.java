package com.project.ece651.webapp.utils;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.shared.ApartmentDto;

public class ApartmentUtils {
    // utils class contain methods to faciliate the conversion between ApartmentEntity and its corresponding Dto
    public static void apartmentDtoToEntity(ApartmentDto apartmentDto, ApartmentEntity apartmentEntity) {
        // extract core information from apartment dto and put the info into the corresponding entity
        // information to extract include type, address, start month, end month, description, and price
        apartmentEntity.setType(apartmentDto.getType());
        apartmentEntity.setVacancy(apartmentDto.getVacancy());
        apartmentEntity.setAddress(apartmentDto.getAddress());
        apartmentEntity.setStartMonth(apartmentDto.getStartMonth());
        apartmentEntity.setTerm(apartmentDto.getTerm());
        apartmentEntity.setDescription(apartmentDto.getDescription());
        apartmentEntity.setPrice(apartmentDto.getPrice());
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
        apartmentDto.setStartMonth(apartmentEntity.getStartMonth());
        apartmentDto.setTerm(apartmentEntity.getTerm());
        apartmentDto.setDescription(apartmentEntity.getDescription());
        apartmentDto.setPrice(apartmentEntity.getPrice());
        return apartmentDto;
    }
}
