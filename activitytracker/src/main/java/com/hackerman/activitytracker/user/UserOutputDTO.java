package com.hackerman.activitytracker.user;

import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class UserOutputDTO {
    String displayName;
    String email;

    public UserOutputDTO(String displayName, String email) {
        this.displayName = displayName;
        this.email = email;
    }

    public UserOutputDTO() {
    }

    public UserOutputDTO(MyUser user) {
        if (user == null){
            throw new NullPointerException("User cannot be null");
        }
        this.displayName = user.getEmail();
        this.email = user.getEmail();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserOutputDTO{" +
                "displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}