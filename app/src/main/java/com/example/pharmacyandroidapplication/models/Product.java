package com.example.pharmacyandroidapplication.models;

public class Product {
    private String id;
    private String name;
    private int img;
    private int inventory_quantity;
    private int price;
    public Product(String name, int price, int img){
        this.id="";
        this.img = img;
        this.inventory_quantity = 1;
        this.name = name;
        this.price = price;
    }

    public Product(String id, String name, int img, int inventory_quantity, int price) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.inventory_quantity = inventory_quantity;
        this.price = price;
    }
    public Product(String name, int price, int inventory_quantity, int img) {
        this.name = name;
        this.img = img;
        this.inventory_quantity = inventory_quantity;
        this.price = price;
    }

    public int getInventory_quantity() {
        return inventory_quantity;
    }

    public void setInventory_quantity(int inventory_quantity) {
        this.inventory_quantity = inventory_quantity;
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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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
