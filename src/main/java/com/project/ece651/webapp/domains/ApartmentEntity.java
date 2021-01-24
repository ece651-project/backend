package com.project.ece651.webapp.domains;

import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "apartments")
public class ApartmentEntity implements Serializable {
    // not necessary, but preferable
    private static final long serialVersionUID = -8788810915308195856L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // This GenerationType indicates that the persistence provider must assign primary keys for the entity using a database identity column.
    // Default AUTO is also ok.
    private long Id;

    @Column(nullable=false, unique=true)
    private String apartmentId;

    @Column(nullable=false)
    private int price;

    @Column(nullable=false)
    private String location;

    @Column(nullable=false)
    private String description;

    @ManyToOne
    private UserEntity landlord;

    @Lob    //BLOB field inside db
    private Byte[] image;

//    TODO:
//    1. fields: rent period, @Log images, price and payment cycle, notes, ...

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserEntity getLandlord() {
        return landlord;
    }

    public void setLandlord(UserEntity landlord) {
        this.landlord = landlord;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }
}
