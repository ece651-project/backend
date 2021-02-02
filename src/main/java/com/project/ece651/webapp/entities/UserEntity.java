package com.project.ece651.webapp.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity implements Serializable{
    // not necessary, but preferable
    private static final long serialVersionUID = 4315090579219428812L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // This GenerationType indicates that the persistence provider must assign primary keys for the entity using a database identity column.
    // Default AUTO is also ok.
    private long Id;

    @Column(nullable=false, unique=true)
    private String uid;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 50, unique = true)
    private String nickname;

    // TODO: maybe another specialized constraint for phone number
    @Column(nullable=false)
    private String phoneNum;

    @Column(nullable=false, unique=true)
    private String encryptedPassword;

    // TODO: list or set? If set, add equals() and hashCode().
    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL)
    private List<ApartmentEntity> ownedApartments = new ArrayList<>();

    // TODO: list or set?
    // TODO: single-side or double-side relation? Does the apartment need to know its collectors? Curr is single-side.
    @ManyToMany(fetch = FetchType.EAGER)    // TODO: consider the FetchType
    @JoinTable(name = "favorite_apartment",
            joinColumns = {@JoinColumn(name = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "aid")})
    private List<ApartmentEntity> favoriteApartments = new ArrayList<>();

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public List<ApartmentEntity> getOwnedApartments() {
        return ownedApartments;
    }

    public UserEntity addOwnedApartments(ApartmentEntity apartmentEntity) {
        this.ownedApartments.add(apartmentEntity);
        apartmentEntity.setLandlord(this);
        return this;
    }

}
