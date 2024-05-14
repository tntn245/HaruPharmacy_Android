package com.example.pharmacyandroidapplication.models;

public class Account {
    private String id;
    private String username;
    private String password;
    private String role;

    public Account(String id, String username, String role, String sex, String birth_day) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.sex = sex;
        this.birth_day = birth_day;
    }

    private String sex;
    private String birth_day;

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

    public Account(String id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Account(String id) {
        this.id = id;
        this.role = "customer";
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
