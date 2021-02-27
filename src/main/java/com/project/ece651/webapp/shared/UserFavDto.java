package com.project.ece651.webapp.shared;

import java.io.Serializable;

public class UserFavDto implements Serializable {
    private String uid;
    private Long aid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }
}
