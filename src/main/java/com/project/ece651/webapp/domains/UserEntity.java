package com.project.ece651.webapp.domains;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable{
    // not necessary, but preferable
    private static final long serialVersionUID = 4315090579219428812L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // This GenerationType indicates that the persistence provider must assign primary keys for the entity using a database identity column.
    // Default AUTO is also ok.
    private long Id;

    @Column(nullable=false, unique=true)
    private String userId;

    @Column(nullable = false, length = 50, unique = true)
    private String userName;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable=false, unique=true)
    private String encryptedPassword;

    // TODO: phoneNumber
    // TODO: @Lob images
    // TODO: fields for "add to favorites"

    // TODO: List<ApartmentEntity> (or Set): one-to-many mapping
    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL)
    private List<ApartmentEntity> apartments = new ArrayList<>();

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public List<ApartmentEntity> getApartments() {
        return apartments;
    }

    public UserEntity addApartment(ApartmentEntity apartmentEntity) {
        this.apartments.add(apartmentEntity);
        apartmentEntity.setLandlord(this);
        return this;
    }
}
