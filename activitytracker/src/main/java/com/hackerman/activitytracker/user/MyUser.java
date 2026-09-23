package com.hackerman.activitytracker.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="users")
public class MyUser {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(name="email",unique = true)
    private String email;

    @Column(name="password")
    @Size(min=6,max=128)
    private String password;

    public MyUser(String email, String password) {
        this.email = email;
        this.password = password;
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
