package com.project.ece651.webapp.repositories;

// reference https://www.callicoder.com/spring-boot-file-upload-download-jpa-hibernate-mysql-database-example/
// for image uploading, downloading and storing in database

import com.project.ece651.webapp.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

}
