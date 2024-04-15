package com.example.pharmacyandroidapplication;

import javax.xml.namespace.QName;

public class Product {
    private int ProductId;
    private String Name;
    private int Price;
    private String Detail;

    public Product(int productId, String name,int price, String detail) {
        ProductId = productId;
        Name = name;
        Detail = detail;
        Price=price;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }
}
