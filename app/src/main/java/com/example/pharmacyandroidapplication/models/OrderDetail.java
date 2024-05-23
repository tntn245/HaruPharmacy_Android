package com.example.pharmacyandroidapplication.models;

public class OrderDetail {
    private String id_order,id_product,unit;
    private int lot_number,quantity,sell_price,price;

    public OrderDetail(String id_order, String id_product, String unit, int lot_number, int quantity, int sell_price, int price) {
        this.id_order = id_order;
        this.id_product = id_product;
        this.unit = unit;
        this.lot_number = lot_number;
        this.quantity = quantity;
        this.sell_price = sell_price;
        this.price = price;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getLot_number() {
        return lot_number;
    }

    public void setLot_number(int lot_number) {
        this.lot_number = lot_number;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSell_price() {
        return sell_price;
    }

    public void setSell_price(int sell_price) {
        this.sell_price = sell_price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
