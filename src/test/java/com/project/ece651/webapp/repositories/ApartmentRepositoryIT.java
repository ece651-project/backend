package com.project.ece651.webapp.repositories;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.Type;
import com.project.ece651.webapp.entities.UserEntity;
import com.project.ece651.webapp.utils.ApartmentTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ApartmentRepositoryIT {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;

    private static final String DEFAULT_USER_ID = "12345";
    @BeforeEach
    void setUp() {
        // making sure at least one user is in the database
        // for the ease of testing apartments
        UserEntity userEntity = new UserEntity();
        userEntity.setUid(DEFAULT_USER_ID);
        userEntity.setNickname("David");
        userEntity.setEmail("david@126.com");
        userEntity.setEncryptedPassword("asfasc12c");
        userEntity.setPhoneNum("1231231231231");
        userRepository.save(userEntity);
        userRepository.save(userEntity);
    }

    /*
     Apart from its dependency on JPA, Persistent Integration Test will not make any assumption on the states of the database.
     Therefore, it does not need to rollback the database transactions.
     */
    @Test
    void testFindByAid() {
        Long aid = transactionTemplate.execute((ts) -> {
            UserEntity user = userRepository.findByUid(DEFAULT_USER_ID);
            ApartmentEntity apartmentEntity = ApartmentTestUtils.createDefaultApartment(user);
            // add create one default apartment to be owned by the user
            // and save them to the database
            user.addOwnedApartments(apartmentEntity);
            userRepository.save(user);
            List <ApartmentEntity> apartmentEntities = user.getOwnedApartments();
            return apartmentEntities.get(apartmentEntities.size() - 1).getAid();
        });

        transactionTemplate.execute((ts) -> {
            ApartmentEntity apartmentEntity = apartmentRepository.findByAid(aid);
            assertEquals(aid, apartmentEntity.getAid());
            assertEquals(ApartmentTestUtils.DEFAULT_APARTMENT_TYPE, apartmentEntity.getType());
            assertEquals(ApartmentTestUtils.DEFAULT_APARTMENT_ADDRESS, apartmentEntity.getAddress());
            assertEquals(ApartmentTestUtils.DEFAULT_APARTMENT_DESC, apartmentEntity.getDescription());
            assertEquals(ApartmentTestUtils.DEFAULT_APARTMENT_PRICE, apartmentEntity.getPrice());
            return null;
        });
    }

    @Test
    void testAddApartments() {
        // adding one apartment for each user
        // numDiff is the number of apartment after such operration - the number of apartments before - the number of users
        Integer numDiff = transactionTemplate.execute((ts) -> {
            // count the number of apartments before adding
            int beforeApartmentNum = apartmentRepository.findAll().size();
            Iterable<UserEntity> users = userRepository.findAll();
            int userCnt = 0;
            for (UserEntity user : users) {
                // each user insert one apartment
                ApartmentEntity apartmentEntity = ApartmentTestUtils.createDefaultApartment(user);
                user.addOwnedApartments(apartmentEntity);
                userRepository.save(user);
                userCnt++;
            }
            // the new number of apart,ents after insertion - the number of users - the previous number of apartments
            return apartmentRepository.findAll().size() - userCnt - beforeApartmentNum;
        });
        // the number diff should be 0
        assertEquals(numDiff, 0);
    }

    // TODO: add tests
    @Test
    void testFindAll() {
        // Two apartment/house info
        String a1_address = "a1 address";
        Timestamp a1_uploadTime = new Timestamp(999999998);
        String a1_desc = "a1 description";
        int a1_price = 11111;

        String h1_address = "h1 address";
        Timestamp h1_uploadTime = new Timestamp(999999997);
        String h1_desc = "h1 description";
        int h1_price = 22222;

        // Create Apartment/house
        ApartmentEntity a1 = new ApartmentEntity();
        a1.setAddress(a1_address);
        a1.setUploadTime(a1_uploadTime);
        a1.setDescription(a1_desc);
        a1.setPrice(a1_price);

        ApartmentEntity h1 = new ApartmentEntity();
        h1.setAddress(h1_address);
        h1.setUploadTime(h1_uploadTime);
        h1.setDescription(h1_desc);
        h1.setPrice(h1_price);

        // Add apartments
        apartmentRepository.save(a1);
        apartmentRepository.save(h1);

        List<ApartmentEntity> apartments = apartmentRepository.findAll();

        assertEquals(apartments.size(), 2);
        assertNotEquals(apartments.size(), 3);
    }
}