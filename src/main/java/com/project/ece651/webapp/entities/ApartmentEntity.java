package com.project.ece651.webapp.entities;

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

    @ManyToOne
    private UserEntity landlord;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @Column(nullable=false)
    private String address;

    /* TODO: add
        1. upload date
        2. Start month
        3. End month
     */

    @Column(nullable=false)
    private String description;

    /* TODO: Add images
        add ImageEntity. @OneToMany
        List<ImageEntity> images
        ImageEntity:
            @Lob    //BLOB field inside db
            private Byte[] image;
     */

    @Column(nullable=false)
    private int price;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public UserEntity getLandlord() {
        return landlord;
    }

    public void setLandlord(UserEntity landlord) {
        this.landlord = landlord;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
