package com.project.ece651.webapp.shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.ece651.webapp.entities.Type;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

// data transfer object that contains all information to be conveyed about a typical apartment entity
// extend MsgDto to support an error message (will be useful when the query fails)
public class ApartmentDto implements Serializable {
    private static final long serialVersionUID = 9031456972238819242L;
    private long aid;
    private String landlordId;
    private Type type;
    private String address;
    private Timestamp uploadTime;
    @JsonFormat(pattern="yyyy-MM",timezone = "GMT+8")
    private Date startMonth;
    @JsonFormat(pattern="yyyy-MM",timezone = "GMT+8")
    private Date endMonth;
    private String description;
    private int price;

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

    public Date getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Date endMonth) {
        this.endMonth = endMonth;
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
