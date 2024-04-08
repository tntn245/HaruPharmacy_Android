package com.example.pharmacyandroidapplication;

public class Product {
    private String product_name;
    private int imgProductid;
    public Product(String product_name, int imgProductid){
        this.imgProductid = imgProductid;
        this.product_name = product_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getImgProductid() {
        return imgProductid;
    }

    public void setImgProductid(int imgProductid) {
        this.imgProductid = imgProductid;
    }
}
