package com.example.pharmacyandroidapplication.models;

public class Product {
    private String name;
    private int img;
    private int price;
    public Product(String name, int price, int img){
        this.img = img;
        this.name = name;
        this.price = price;
    }

    public String getProductName() {
        return name;
    }

    public void setProductName(String product_name) {
        this.name = name;
    }

    public int getProductImg() {
        return img;
    }

    public void setProductImg(int img) {
        this.img = img;
    }

    public int getProductPrice() {
        return price;
    }

    public void setProductPrice(int price) {
        this.price = price;
    }
}
