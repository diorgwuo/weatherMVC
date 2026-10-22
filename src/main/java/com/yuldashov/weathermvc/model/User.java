package com.yuldashov.weathermvc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Location> locations;

    @OneToMany(mappedBy = "user")
    private List<Session> sessions;

    public User(String login,String password) {
        this.login = login;
        this.password = password;
        locations = new ArrayList<>();
    }
}
