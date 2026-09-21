package com.hackerman.activitytracker.activity;

import jakarta.validation.constraints.*;

public class UserCreateDTO {

    @NotBlank
    @Email
    public String email;

    @NotBlank
    @Size(min=6,max=128)
    public String password;


    @NotBlank
    @Size(min=6,max=128)
    public String matchingPassword;

    public UserCreateDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserCreateDTO(String email, String password,String matchingPassword) {
        this.email = email;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }


    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
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

    @Override
    public String toString() {
        return "UserCreateDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", matchingPassword='" + matchingPassword + '\'' +
                '}';
    }


}
