package com.project.ece651.webapp.services;

import com.project.ece651.webapp.entities.ApartmentEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ApartmentService {
    public void storeImages(Long aid, MultipartFile[] images) throws IOException;

}
