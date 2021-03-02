package com.project.ece651.webapp.services;

import com.project.ece651.webapp.entities.ApartmentEntity;
import com.project.ece651.webapp.entities.ImageEntity;
import com.project.ece651.webapp.repositories.ApartmentRepository;
import com.project.ece651.webapp.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Override
    public void storeImages(Long aid, MultipartFile[] images) throws IOException {
        ApartmentEntity apartmentEntity = apartmentRepository.findByAid(aid);
        for (MultipartFile image : images) {
            ImageEntity imageEntity = new ImageEntity(image.getContentType(), image.getBytes());
            apartmentEntity.addImage(imageEntity);
        }
        apartmentRepository.save(apartmentEntity);
    }

}
