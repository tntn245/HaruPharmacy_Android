package com.example.pharmacyandroidapplication.models;

import java.util.Date;

public class ProductStockInDetails {
    private String product_name;
    private String lot_number;
    private Date production_date;
    private Date expiration_date;
    private int quantity;
    private int unit_price;
    private int img;

    public ProductStockInDetails(String product_name, String lot_number, Date production_date, Date expiration_date, int quantity, int unit_price, int img) {
        this.product_name = product_name;
        this.lot_number = lot_number;
        this.production_date = production_date;
        this.expiration_date = expiration_date;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.img = img;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getLot_number() {
        return lot_number;
    }

    public void setLot_number(String lot_number) {
        this.lot_number = lot_number;
    }

    public Date getProduction_date() {
        return production_date;
    }

    public void setProduction_date(Date production_date) {
        this.production_date = production_date;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(int unit_price) {
        this.unit_price = unit_price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int totalPrice(){
        return this.quantity*this.unit_price;
    }
}
