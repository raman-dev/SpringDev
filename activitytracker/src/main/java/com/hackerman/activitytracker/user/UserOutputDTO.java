package com.hackerman.activitytracker.user;

import org.jspecify.annotations.NonNull;

public class UserOutputDTO {
    String displayName;
    String email;

    public UserOutputDTO(String displayName, String email) {
        this.displayName = displayName;
        this.email = email;
    }

    public UserOutputDTO() {
    }

    public UserOutputDTO(@NonNull MyUser user) {
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