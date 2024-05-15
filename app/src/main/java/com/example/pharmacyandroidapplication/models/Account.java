package com.example.pharmacyandroidapplication.models;

public class Account {
    private String id;
    private String username;
    private String role;
    private String img;
    private String sex;
    private String birth_day;

    public Account(String id, String img, String role, String username, String sex, String birth_day) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.img = img;
        this.sex = sex;
        this.birth_day = birth_day;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(String birth_day) {
        this.birth_day = birth_day;
    }
}
