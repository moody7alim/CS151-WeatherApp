package com.example.springapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class User {
    @Id
    private String id;
    private String username;
    private String password; // Consider encrypting this in a real application
    private String email;
    private List<String> cities;

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(String username, String password, String email, List<String> cities) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.cities = cities;

    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }


}
