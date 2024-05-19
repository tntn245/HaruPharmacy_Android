package com.example.pharmacyandroidapplication.models;

public class Cart {
    private String id_account;
    private String id_product;
    private String name_product;
    private String unit;
    private int price_product;
    private int quanlity;
    private boolean isChecked;
    private String img;
    public Cart(String userId, String productId, String productName, float price, int quantity, boolean isChecked,String unit, String imageURL){}
    public Cart(String id_account, String id_product,String name_product, int price_product, int quanlity, boolean isChecked,String unit,String img) {
        this.id_account = id_account;
        this.id_product = id_product;
        this.name_product = name_product;
        this.price_product = price_product;
        this.quanlity = quanlity;
        this.isChecked = isChecked;
        this.img = img;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
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
