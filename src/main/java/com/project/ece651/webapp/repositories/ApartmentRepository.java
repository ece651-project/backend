package com.project.ece651.webapp.repositories;

import com.project.ece651.webapp.domains.ApartmentEntity;
import org.springframework.data.repository.CrudRepository;

public interface ApartmentRepository extends CrudRepository<ApartmentEntity, Long> {
}
