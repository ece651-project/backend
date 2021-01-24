package com.project.ece651.webapp.repositories;

import com.project.ece651.webapp.domains.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserId(String userId);
    UserEntity findByUserName(String userName);
    UserEntity findByEmail(String email);
}
