package com.project.ece651.webapp.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.project.ece651.webapp.entities.ApartmentEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// DTO: data transfer object
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class UserDto extends MsgResponse implements Serializable {
    private static final long serialVersionUID = 3031456972238819242L;

    // public interface MsgView {}
    public interface AddView extends MsgView {}
    public interface GetView {} // extends AddView {}
    public interface LoginView {}

    @JsonView({GetView.class, AddView.class})
    private String uid;

    @JsonView({GetView.class, LoginView.class})
    private String email;

    @JsonView(GetView.class)
    private String nickname;

    @JsonView(GetView.class)
    private String phoneNum;

    @JsonView(LoginView.class)
    private String password;

    private String encryptedPassword;

    private List<ApartmentDto> ownedApartments = new ArrayList<>();

    // @JsonIgnoreProperties("users")
    private List<ApartmentDto> favoriteApartments = new ArrayList<>();

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public List<ApartmentDto> getOwnedApartments() {
        return ownedApartments;
    }

    public void setOwnedApartments(List<ApartmentDto> ownedApartments) {
        this.ownedApartments = ownedApartments;
    }

    public List<ApartmentDto> getFavoriteApartments() {
        return favoriteApartments;
    }

    public void setFavoriteApartments(List<ApartmentDto> favoriteApartments) {
        this.favoriteApartments = favoriteApartments;
    }
}
