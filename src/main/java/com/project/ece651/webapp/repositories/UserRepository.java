package com.project.ece651.webapp.repositories;

import com.project.ece651.webapp.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUid(String uid);
    UserEntity findByNickname(String nickname);
    UserEntity findByEmail(String email);
//    void deleteByUid(String uid);
}
