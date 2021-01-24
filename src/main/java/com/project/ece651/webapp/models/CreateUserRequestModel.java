package com.project.ece651.webapp.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {
    @NotNull(message="Email cannot be null")
    @Email
    private String email;

    @NotNull(message="Nickname cannot be null")
    @Size(min=2, max = 32, message="Nickname must be >= 2 characters and < 32 characters")
    private String nickname;

    @NotNull(message="Password name cannot be null")
    @Size(min=8, max=16, message="Password must be >= 8 characters and < 16 characters")
    private String password;

    // TODO: maybe anther constraint for phone
    @NotNull(message="Phone number cannot be null")
    private String phoneNum;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
