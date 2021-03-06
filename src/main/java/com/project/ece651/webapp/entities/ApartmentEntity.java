package com.project.ece651.webapp.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "apartment")
public class ApartmentEntity implements Serializable {
    // not necessary, but preferable
    private static final long serialVersionUID = -8788810915308195856L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // This GenerationType indicates that the persistence provider must assign primary keys for the entity using a database identity column.
    // Default AUTO is also ok.
    private long aid;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private UserEntity landlord;

    //@Column(name = "apt_type")  // type is a keyword in MySQL
    @Enumerated(value = EnumType.STRING)
    private Type type;

    @Column(nullable=false)
    private String address;

    /* TODO: add
        1. upload date
        2. Start month
        3. End month
     */
    // reference https://stackoverflow.com/questions/1335374/jpa-set-timestamp-field-to-current-timestamp-when-persisting
    @Column(nullable=false, updatable=false, insertable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp uploadTime;

    @Column(nullable=true)
    private Date startMonth;

    @Column(nullable=true)
    private Date endMonth;

    @Column(nullable=false)
    private String description;

    /* TODO: Add images
        add ImageEntity. @OneToMany
        List<ImageEntity> images
        ImageEntity:
            @Lob    //BLOB field inside db
            private Byte[] data;
     */
    @OneToMany(mappedBy = "apartment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageEntity> images = new ArrayList<>();

    @Column(nullable=false)
    private int price;

//    @ManyToMany(mappedBy = "favoriteApartments")
//    private List<UserEntity> users;

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
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

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Date startMonth) {
        this.startMonth = startMonth;
    }

    public Date getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Date endMonth) {
        this.endMonth = endMonth;
    }

    public List<ImageEntity> getImages() {
        return images;
    }

    public void setImages(List<ImageEntity> images) {
        this.images = images;
    }

    public ApartmentEntity addImage(ImageEntity imageEntity) {
        this.images.add(imageEntity);
        imageEntity.setApartment(this);
        return this;
    }
}
