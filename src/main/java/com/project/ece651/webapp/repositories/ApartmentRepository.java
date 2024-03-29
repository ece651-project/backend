package com.project.ece651.webapp.repositories;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ApartmentRepository extends CrudRepository<ApartmentEntity, Long> {
    // current set to list because of easiest to play around
    // will change to iterable
    List<ApartmentEntity> findAll();
    ApartmentEntity findByAid(long aid);
}
