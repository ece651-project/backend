package com.project.ece651.webapp.shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.ece651.webapp.entities.Type;
import com.project.ece651.webapp.entities.UserEntity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// data transfer object that contains all information to be conveyed about a typical apartment entity
// extend MsgDto to support an error message (will be useful when the query fails)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ApartmentDto implements Serializable {
    private static final long serialVersionUID = 9031456972238819242L;

    private Long aid;

    // to avoid infinite recursion:
    // https://stackoverflow.com/questions/3325387/infinite-recursion-with-jackson-json-and-hibernate-jpa-issue
    @JsonIgnoreProperties("ownedApartments")
    private String landlordId;

    private Type type;

    private Integer vacancy;

    private String address;

    private Timestamp uploadTime;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date startDate;

//    @JsonFormat(pattern="yyyy-MM",timezone = "GMT+8")
//    private Date endMonth;
    private Integer term;

    private String description;

    // set of images stored in base64 string
    private Set<String> images;
    // private List<String> images;

    private Integer price;

//    @JsonIgnoreProperties("favoriteApartments")
//    private List<UserEntity> users;

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
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

    public Integer getVacancy() {
        return vacancy;
    }

    public void setVacancy(Integer vacancy) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
