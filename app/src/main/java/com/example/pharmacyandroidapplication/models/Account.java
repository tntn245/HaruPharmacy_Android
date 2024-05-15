package com.example.pharmacyandroidapplication.models;

public class Account {
    private String id;
    private String username;
    private String role;
    private String img;

    public Account(String id, String img, String role, String username) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.img = img;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
