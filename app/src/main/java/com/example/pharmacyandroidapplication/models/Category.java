package com.example.pharmacyandroidapplication.models;

public class Category {
    private String id;
    private String name;
    private boolean flag_valid;

    public Category(String id, String name, boolean flag_valid) {
        this.id = id;
        this.name = name;
        this.flag_valid = flag_valid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag_valid() {
        return flag_valid;
    }

    public void setFlag_valid(boolean flag_valid) {
        this.flag_valid = flag_valid;
    }
}
