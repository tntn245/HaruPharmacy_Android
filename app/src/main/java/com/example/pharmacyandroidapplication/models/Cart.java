package com.example.pharmacyandroidapplication.models;

public class Cart {
    private String id_account;
    private String id_product;
    private String name_product;
    private int price_product;
    private int quanlity;
    public Cart(){}
    public Cart(String id_account, String id_product, String name_product, int price_product, int quanlity) {
        this.id_account = id_account;
        this.id_product = id_product;
        this.name_product = name_product;
        this.price_product = price_product;
        this.quanlity = quanlity;
    }

    public String getId_account() {
        return id_account;
    }

    public void setId_account(String id_account) {
        this.id_account = id_account;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public int getPrice_product() {
        return price_product;
    }

    public void setPrice_product(int price_product) {
        this.price_product = price_product;
    }

    public int getQuanlity() {
        return quanlity;
    }

    public void setQuanlity(int quanlity) {
        this.quanlity = quanlity;
    }
}
