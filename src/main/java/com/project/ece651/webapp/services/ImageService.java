package com.project.ece651.webapp.services;

// reference https://www.callicoder.com/spring-boot-file-upload-download-jpa-hibernate-mysql-database-example/
// for image uploading, downloading and storing in database

import com.project.ece651.webapp.entities.ApartmentEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    // store the image into its data base
    public void storeImages(ApartmentEntity apartmentEntity, MultipartFile[] images) throws IOException;
}
