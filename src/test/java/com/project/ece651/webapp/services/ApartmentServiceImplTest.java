package com.project.ece651.webapp.services;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.Type;
import com.project.ece651.webapp.entities.UserEntity;
import com.project.ece651.webapp.exceptions.ActionNotAllowedException;
import com.project.ece651.webapp.exceptions.ApartmentNotFoundException;
import com.project.ece651.webapp.exceptions.UserNotFoundException;
import com.project.ece651.webapp.repositories.ApartmentRepository;
import com.project.ece651.webapp.repositories.UserRepository;
import com.project.ece651.webapp.shared.ApartmentDto;
import com.project.ece651.webapp.shared.UserDto;
import com.project.ece651.webapp.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class ApartmentServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ApartmentRepository apartmentRepository;

    ApartmentService apartmentService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        apartmentRepository = mock(ApartmentRepository.class);
        apartmentService = new ApartmentServiceImpl(userRepository, apartmentRepository);
    }

    @Test
    void addApartmentSuccessTest() {
        String landlordId = "1111111111222222222333333";
        // mock behaviour
        when(userRepository.findByUid(landlordId)).thenReturn(new UserEntity());
        // given
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setType(Type.HOUSE);
        apartmentDto.setAddress("Empty address");
        apartmentDto.setStartMonth(DateUtils.calDate(2021, 5, 3));
        apartmentDto.setEndMonth(null);
        apartmentDto.setDescription("Empty description");
        apartmentDto.setPrice(500);
        apartmentDto.setLandlordId(landlordId);
        // when
        apartmentService.addApartment(apartmentDto);
        // no exception should be throw
    }

    @Test
    void addApartmentLandlordNotFoundTest() {
        String landlordId = "1111111111222222222333333";
        // mock behaviour
        when(userRepository.findByUid(landlordId)).thenReturn(null);
        // given
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setType(Type.HOUSE);
        apartmentDto.setAddress("Empty address");
        apartmentDto.setStartMonth(DateUtils.calDate(2021, 5, 3));
        apartmentDto.setEndMonth(null);
        apartmentDto.setDescription("Empty description");
        apartmentDto.setPrice(500);
        apartmentDto.setLandlordId(landlordId);

        // a UserNotFoundException is expected
        assertThrows(UserNotFoundException.class,
                ()-> {
                    apartmentService.addApartment(apartmentDto);
                });
    }

    @Test
    void updateApartmentSuccessTest() {
        long aid = 1;
        String expectedLandlordId = "11111111111111";
        // mock behaviour
        ApartmentEntity originalEntity = new ApartmentEntity();
        UserEntity landlord = new UserEntity();
        landlord.setUid(expectedLandlordId);
        originalEntity.setLandlord(landlord);
        when(apartmentRepository.findByAid(aid)).thenReturn(originalEntity);
        // given
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setLandlordId(expectedLandlordId);
        apartmentDto.setAid(aid);
        apartmentDto.setType(Type.APARTMENT);
        apartmentDto.setAddress(null);
        apartmentDto.setStartMonth(DateUtils.calDate(2021, 5, 3));
        apartmentDto.setEndMonth(null);
        apartmentDto.setDescription("Empty description");
        apartmentDto.setPrice(500);
        // when
        apartmentService.updateApartment(aid, apartmentDto);
        // no exception should be throw
    }

    @Test
    void updateApartmentNotExistTest() {
        long aid = 1;
        String expectedLandlordId = "11111111111111";
        // mock behaviour
        when(apartmentRepository.findByAid(aid)).thenReturn(null);
        // given
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setLandlordId(expectedLandlordId);
        apartmentDto.setAid(aid);
        apartmentDto.setType(Type.APARTMENT);
        apartmentDto.setAddress(null);
        apartmentDto.setStartMonth(DateUtils.calDate(2021, 5, 3));
        apartmentDto.setEndMonth(null);
        apartmentDto.setDescription("Empty description");
        apartmentDto.setPrice(500);
        // an ApartmentNotFound Exception is expected
        assertThrows(ApartmentNotFoundException.class,
                ()-> {
                    apartmentService.updateApartment(aid, apartmentDto);
                });
    }

    @Test
    void updateApartmentNoRightTest() {
        long aid = 1;
        String expectedLandlordId = "11111111111111";
        // mock behaviour
        ApartmentEntity originalEntity = new ApartmentEntity();
        UserEntity landlord = new UserEntity();
        landlord.setUid(expectedLandlordId);
        originalEntity.setLandlord(landlord);
        when(apartmentRepository.findByAid(aid)).thenReturn(originalEntity);
        // given
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setLandlordId(expectedLandlordId + "PLACE HOLDER");
        apartmentDto.setAid(aid);
        apartmentDto.setType(Type.APARTMENT);
        apartmentDto.setAddress(null);
        apartmentDto.setStartMonth(DateUtils.calDate(2021, 5, 3));
        apartmentDto.setEndMonth(null);
        apartmentDto.setDescription("Empty description");
        apartmentDto.setPrice(500);
        // an ApartmentNotFound Exception is expected
        assertThrows(ActionNotAllowedException.class,
                ()-> {
                    apartmentService.updateApartment(aid, apartmentDto);
                });
    }

    @Test
    void deleteApartmentSuccessTest() {
        String uid = "123";
        long aid = 1;
        // mock behaviour
        ApartmentEntity apartmentEntity = new ApartmentEntity();
        apartmentEntity.setAid(aid);
        UserEntity userEntity = new UserEntity();
        userEntity.setUid(uid);
        apartmentEntity.setLandlord(userEntity);
        when(apartmentRepository.findByAid(aid)).thenReturn(apartmentEntity);
        // no exception is expected
        apartmentService.deleteApartment(uid, aid);
    }

    @Test
    void deleteApartmentNoRightTest() {
        String uid = "123";
        long aid = 1;
        // mock behaviour
        ApartmentEntity apartmentEntity = new ApartmentEntity();
        apartmentEntity.setAid(aid);
        UserEntity userEntity = new UserEntity();
        userEntity.setUid(uid + "placeholder");
        apartmentEntity.setLandlord(userEntity);
        when(apartmentRepository.findByAid(aid)).thenReturn(apartmentEntity);
        assertThrows(ActionNotAllowedException.class,
                ()-> {
                    apartmentService.deleteApartment(uid, aid);
                });
    }

    @Test
    void deleteApartmentNotFoundTest() {
        String uid = "123";
        long aid = 1;
        // mock behaviour
        when(apartmentRepository.findByAid(aid)).thenReturn(null);
        // an ApartmentNotFound Exception is expected
        assertThrows(ApartmentNotFoundException.class,
                ()-> {
                    apartmentService.deleteApartment(uid, aid);
                });
    }










}
