package com.project.ece651.webapp.repositories;

import com.project.ece651.webapp.domains.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.support.TransactionTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
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
    void findByUserId() {
        String userId = transactionTemplate.execute((ts) -> {
            UserEntity userEntity = new UserEntity();

            // id field in auto incremented by DB
            userEntity.setUserId("12345");
            userEntity.setUserName("David");
            userEntity.setEmail("david@126.com");
            userEntity.setEncryptedPassword("asfasc12c");

            userRepository.save(userEntity);

            return userEntity.getUserId();
        });

        transactionTemplate.execute((ts) -> {
            UserEntity resultUserEntity = userRepository.findByUserId(userId);

            assertEquals(1L, resultUserEntity.getId());
            assertEquals(userId, resultUserEntity.getUserId());
            assertEquals("David", resultUserEntity.getUserName());
            assertEquals("david@126.com", resultUserEntity.getEmail());
            assertEquals("asfasc12c", resultUserEntity.getEncryptedPassword());

            return null;
        });
    }

    // TODO: add tests
}