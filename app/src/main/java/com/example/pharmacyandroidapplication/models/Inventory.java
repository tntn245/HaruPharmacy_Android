package com.example.pharmacyandroidapplication.models;

public class Inventory {
    private String id;
    private String name;
    private String unit;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String img;
    int inventory_quantity;

    public Inventory(String id, String name, String unit, String img, int inventory_quantity) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.img = img;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getInventory_quantity() {
        return inventory_quantity;
    }

    public void setInventory_quantity(int inventory_quantity) {
        this.inventory_quantity = inventory_quantity;
    }
}
