package com.project.ece651.webapp.shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.ece651.webapp.entities.Type;
import com.project.ece651.webapp.entities.UserEntity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

// data transfer object that contains all information to be conveyed about a typical apartment entity
// extend MsgDto to support an error message (will be useful when the query fails)
public class ApartmentDto implements Serializable {
    private static final long serialVersionUID = 9031456972238819242L;

    private long aid;

    // to avoid infinite recursion:
    // https://stackoverflow.com/questions/3325387/infinite-recursion-with-jackson-json-and-hibernate-jpa-issue
    @JsonIgnoreProperties("ownedApartments")
    private String landlordId;

    private Type type;

    private int vacancy;

    private String address;

    private Timestamp uploadTime;

    @JsonFormat(pattern="yyyy-MM",timezone = "GMT+8")
    private Date startMonth;

//    @JsonFormat(pattern="yyyy-MM",timezone = "GMT+8")
//    private Date endMonth;
    private int term;

    private String description;

    // list of images stored in base64 string
    private List<String> images;

    private int price;

//    @JsonIgnoreProperties("favoriteApartments")
//    private List<UserEntity> users;

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public String getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(String landlordId) {
        this.landlordId = landlordId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getVacancy() {
        return vacancy;
    }

    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
