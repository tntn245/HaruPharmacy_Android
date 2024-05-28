package com.example.pharmacyandroidapplication.models;

public class Unit {
    private String name;
    private  int price;
    private  int quantity;
    public Unit() {
    }
    public Unit(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

