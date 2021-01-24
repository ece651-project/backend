package com.project.ece651.webapp.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {
    @NotNull(message="User name cannot be null")
    @Size(min=2, max = 32, message="User name must be >= 2 characters and < 32 characters")
    private String userName;

    @NotNull(message="Email cannot be null")
    @Email
    private String email;

    @NotNull(message="Password name cannot be null")
    @Size(min=8, max=16, message="Password must be >= 8 characters and < 16 characters")
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
