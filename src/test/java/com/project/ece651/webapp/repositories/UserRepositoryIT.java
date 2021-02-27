package com.project.ece651.webapp.repositories;

import com.project.ece651.webapp.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/* @ActiveProfiles is a class-level annotation that is used to activate profiles
 * while loading ApplicationContext in Spring integration test.
 * https://www.concretepage.com/spring-5/activeprofiles-example-spring-test
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = none)
class UserRepositoryIT {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @BeforeEach
    void setUp() {
    }

    /*
     Apart from its dependency on JPA, Persistent Integration Test will not make any assumption on the states of the database.
     Therefore, it does not need to rollback the database transactions.
     */
    @Test
    @Transactional
    void testFindByUid() {
        String userId = transactionTemplate.execute((ts) -> {
            UserEntity userEntity = new UserEntity();

            // id field in auto incremented by DB
            userEntity.setUid("12345");
            userEntity.setNickname("David");
            userEntity.setEmail("david@126.com");
            userEntity.setEncryptedPassword("asfasc12c");
            userEntity.setPhoneNum("1231231231231");

            userRepository.save(userEntity);

            return userEntity.getUid();
        });

        transactionTemplate.execute((ts) -> {
            UserEntity resultUserEntity = userRepository.findByUid(userId);

            assertEquals(userId, resultUserEntity.getUid());
            assertEquals("David", resultUserEntity.getNickname());
            assertEquals("david@126.com", resultUserEntity.getEmail());
            assertEquals("asfasc12c", resultUserEntity.getEncryptedPassword());

            return null;
        });
    }

    // TODO: add tests
    @Test
    void testAddUser() {
        // User info
        String uid = "00000";
        String email = "tempUser@125.com";
        String nickname = "tempUserNickname";
        String encryptedPassword = "thisIsEncrypted";
        String phoneNum = "8008208820";

        // Add one User
        Iterable<UserEntity> userEntities = transactionTemplate.execute((ts) -> {
            UserEntity tempUser = new UserEntity();
            tempUser.setUid(uid);
            tempUser.setEmail(email);
            tempUser.setNickname(nickname);
            tempUser.setEncryptedPassword(encryptedPassword);
            tempUser.setPhoneNum(phoneNum);
            userRepository.save(tempUser);

            return userRepository.findAll();
        });

        // Find the number of the users and the last user
        int userNum = 0;
        UserEntity lastUser = null;
        Iterator<UserEntity> iterator = userEntities.iterator();
        while (iterator.hasNext()) {
            userNum++;/**/
            lastUser = iterator.next();
        }

        // Test
        assertEquals(userNum, 1);
        assertEquals(lastUser.getUid(), uid);
        assertEquals(lastUser.getEmail(), email);
        assertEquals(lastUser.getNickname(), nickname);
        assertEquals(lastUser.getEncryptedPassword(), encryptedPassword);
        assertEquals(lastUser.getPhoneNum(), phoneNum);
    }

    @Test
    void testFindByEmail() {
        // User info
        String uid = "00000";
        String email = "tempUser@125.com";
        String nickname = "tempUserNickname";
        String encryptedPassword = "thisIsEncrypted";
        String phoneNum = "8008208820";

        // Create user
        UserEntity tempUser = new UserEntity();
        tempUser.setUid(uid);
        tempUser.setEmail(email);
        tempUser.setNickname(nickname);
        tempUser.setEncryptedPassword(encryptedPassword);
        tempUser.setPhoneNum(phoneNum);

        // Add one User
        userRepository.save(tempUser);

        // Find by email
        UserEntity userFound = userRepository.findByEmail(email);
        UserEntity userNotFound = userRepository.findByEmail("fakeEmail");

        // Check correct
        assertEquals(userFound, tempUser);
        assertNull(userNotFound);
    }

    @Test
    void testFindByNickname() {
        // User info
        String uid = "00000";
        String email = "tempUser@125.com";
        String nickname = "tempUserNickname";
        String encryptedPassword = "thisIsEncrypted";
        String phoneNum = "8008208820";

        // Create user
        UserEntity tempUser = new UserEntity();
        tempUser.setUid(uid);
        tempUser.setEmail(email);
        tempUser.setNickname(nickname);
        tempUser.setEncryptedPassword(encryptedPassword);
        tempUser.setPhoneNum(phoneNum);

        // Add one User
        userRepository.save(tempUser);

        // Find by nickname
        UserEntity userFound = userRepository.findByNickname(nickname);
        UserEntity userNotFound = userRepository.findByNickname("fakeNickname");

        // Check correct
        assertEquals(userFound, tempUser);
        assertNull(userNotFound);
    }
}