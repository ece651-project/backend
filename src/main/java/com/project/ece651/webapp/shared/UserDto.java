package com.project.ece651.webapp.shared;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.project.ece651.webapp.models.MsgResponse;

import java.io.Serializable;

// DTO: data transfer object
public class UserDto extends MsgResponse implements Serializable {
    private static final long serialVersionUID = 3031456972238819242L;

    // public interface MsgView {}
    public interface AddView extends MsgView {}
    public interface GetView extends AddView {}

    @JsonView(MsgView.class)
    private boolean success;

    @JsonView(MsgView.class)
    private String msg;

    @JsonView(AddView.class)
    private String uid;

    @JsonView(GetView.class)
    private String email;

    @JsonView(GetView.class)
    private String nickname;

    @JsonView(GetView.class)
    private String phoneNum;

    private String password;

    private String encryptedPassword;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
}
