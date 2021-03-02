package com.project.ece651.webapp.services;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.ImageEntity;
import com.project.ece651.webapp.repositories.ApartmentRepository;
import com.project.ece651.webapp.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


// reference https://www.callicoder.com/spring-boot-file-upload-download-jpa-hibernate-mysql-database-example/
// for image uploading, downloading and storing in database

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Override
    public void storeImages(ApartmentEntity apartmentEntity, MultipartFile[] images) throws IOException {
        for (MultipartFile image : images) {
            ImageEntity imageEntity = new ImageEntity(image.getContentType(), image.getBytes());
            apartmentEntity.addImage(imageEntity);
        }
        apartmentRepository.save(apartmentEntity);
    }
}
