package com.project.ece651.webapp.entities;

import javax.persistence.*;
import java.util.Arrays;
// reference https://www.callicoder.com/spring-boot-file-upload-download-jpa-hibernate-mysql-database-example/
// for image uploading, downloading and storing in database

@Entity
@Table(name = "image")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // This GenerationType indicates that the persistence provider must assign primary keys for the entity using a database identity column.
    // Default AUTO is also ok.
    private long imageId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ApartmentEntity apartment;

    @Column(nullable=false)
    private String imageType;

    @Lob
    private byte[] data;

    protected ImageEntity() {}

    public ImageEntity (byte[] data) {
        this.data = data;
    }

    public ImageEntity (ApartmentEntity apartmentEntity, byte[] data) {
        this.apartment = apartmentEntity;
        this.data = data;
    }

    public ImageEntity (String imageType, byte[] data) {
        this.imageType = imageType;
        this.data = data;
    }

    public ApartmentEntity getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentEntity apartment) {
        this.apartment = apartment;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageEntity that = (ImageEntity) o;

        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}
