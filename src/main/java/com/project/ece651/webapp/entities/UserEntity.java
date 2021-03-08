package com.project.ece651.webapp.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * Cascade: https://www.baeldung.com/jpa-cascade-types
 * Naming the constraints:
 *  1. https://stackoverflow.com/questions/15372654/uniqueconstraint-and-columnunique-true-in-hibernate-annotation
 *  2. https://stackoverflow.com/questions/23635633/renaming-uniqueconstraint-doesnt-work
 */

@Entity
@Table(name = "user",
        uniqueConstraints = {   // to distinguish from repetitive email and nickname error when adding users
        @UniqueConstraint(
                columnNames = "email",
                name="unique_email"
        ), @UniqueConstraint(
                columnNames = "nickname",
                name="unique_nickname"
        )
})
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

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    // TODO: maybe another specialized constraint for phone number
    @Column
    private String phoneNum;

    @Column(nullable=false, unique=true)
    private String encryptedPassword;

    // TODO: list or set? If set, add equals() and hashCode().
    @OneToMany(mappedBy = "landlord", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApartmentEntity> ownedApartments = new ArrayList<>();

    // TODO: list or set?
    // TODO: single-side or double-side relation? Does the apartment need to know its collectors? Curr is single-side.
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)    // TODO: consider the FetchType
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

    public List<ApartmentEntity> getFavoriteApartments() {
        return favoriteApartments;
    }

    public UserEntity addFavoriteApartments(ApartmentEntity favoriteApartment) {
        if (!this.favoriteApartments.contains(favoriteApartment)) {
            this.favoriteApartments.add(favoriteApartment);
        }
        return this;
    }

    public UserEntity delFavoriteApartments(ApartmentEntity favoriteApartment) {
        if (this.favoriteApartments.contains(favoriteApartment)) {
            this.favoriteApartments.remove(favoriteApartment);
        }
        return this;
    }
}
