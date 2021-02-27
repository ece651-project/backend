package com.project.ece651.webapp.utils;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.Type;
import com.project.ece651.webapp.entities.UserEntity;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class ApartmentTestUtils {
    public static final Type DEFAULT_APARTMENT_TYPE = Type.APARTMENT;
    public static final String DEFAULT_APARTMENT_ADDRESS = "Default apartment address";
    public static final String DEFAULT_APARTMENT_DESC = "Default apartment description";
    public static final int DEFAULT_APARTMENT_PRICE = 12345;
    public static ApartmentEntity createDefaultApartment(UserEntity user) {
        // create a default apartment of the given user
        ApartmentEntity apartmentEntity = new ApartmentEntity();
        apartmentEntity.setLandlord(user);
        apartmentEntity.setType(DEFAULT_APARTMENT_TYPE);
        apartmentEntity.setAddress(DEFAULT_APARTMENT_ADDRESS);
        apartmentEntity.setDescription(DEFAULT_APARTMENT_DESC);
        apartmentEntity.setPrice(DEFAULT_APARTMENT_PRICE);
        return apartmentEntity;
    }
}
