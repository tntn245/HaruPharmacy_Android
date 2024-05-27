package com.example.pharmacyandroidapplication.models;

import java.util.Date;

public class ProductStockOutDetails {
    private String product_name;
    private String lot_number;
    private int out_quantity;
    private String product_img;

    public ProductStockOutDetails(String product_name, String lot_number, int out_quantity, String product_img) {
        this.product_name = product_name;
        this.lot_number = lot_number;
        this.out_quantity = out_quantity;
        this.product_img = product_img;
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

    public int getOut_quantity() {
        return out_quantity;
    }

    public void setOut_quantity(int out_quantity) {
        this.out_quantity = out_quantity;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }
}
