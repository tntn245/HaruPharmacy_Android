package com.example.pharmacyandroidapplication.models;

public class ProductInventoryDetails {
    private String lot_number;
    private String production_date;
    private String expiration_date;
    private int quantity_in_stock;

    public ProductInventoryDetails(String lot_number, String production_date, String expiration_date, int quantity_in_stock) {
        this.lot_number = lot_number;
        this.production_date = production_date;
        this.expiration_date = expiration_date;
        this.quantity_in_stock = quantity_in_stock;
    }

    public String getLot_number() {
        return lot_number;
    }

    public void setLot_number(String lot_number) {
        this.lot_number = lot_number;
    }

    public String getProduction_date() {
        return production_date;
    }

    public void setProduction_date(String production_date) {
        this.production_date = production_date;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public int getQuantity_in_stock() {
        return quantity_in_stock;
    }

    public void setQuantity_in_stock(int quantity_in_stock) {
        this.quantity_in_stock = quantity_in_stock;
    }
}
