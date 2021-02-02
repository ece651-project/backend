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
            user.addOwnedApartments(apartmentEntity);
            apartmentRepository.save(apartmentEntity);
            return apartmentEntity.getAid();
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
                userCnt++;
            }
            // the new number of apart,ents after insertion - the number of users - the previous number of apartments
            return apartmentRepository.findAll().size() - userCnt - beforeApartmentNum;
        });
        // the number diff should be 0
        assertEquals(numDiff, 0);
    }

    // TODO: add tests
}