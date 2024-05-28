package com.example.pharmacyandroidapplication.models;

import androidx.annotation.NonNull;

import java.util.Map;

public class Product {
    private String id;
    private String id_category;
    private String img;
    private String name;
    private int price;
    private int inventory_quantity;
    private String unit;
    private String uses;
    private String ingredient;
    private boolean flag_valid;
    private boolean prescription;
    private Map<String, Object> unitarrr;
    // No-argument constructor required for Firebase
    public Product() {
    }
    public Product(String id) {
        this.id = id;
    }

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
        this.id_category = name;
    }

    public Product(String id, String id_category, String img) {
        this.id = id;
        this.id_category = id_category;
        this.img = img;
    }

    public Product(String id, String id_category, String img, String name) {
        this.id = id;
        this.id_category = id_category;
        this.img = img;
        this.name = name;
    }

    public Product(String id, String id_category, String img, String name, int inventory_quantity) {
        this.id = id;
        this.id_category = id_category;
        this.img = img;
        this.name = name;
        this.inventory_quantity = inventory_quantity;
    }

    public Product(String id, String id_category, String img, String name, int inventory_quantity, int price) {
        this.id = id;
        this.id_category = id_category;
        this.img = img;
        this.name = name;
        this.inventory_quantity = inventory_quantity;
        this.price = price;
    }

    public Product(String id, String id_category, String img, String name, int inventory_quantity, int price, String unit) {
        this.id = id;
        this.id_category = id_category;
        this.img = img;
        this.name = name;
        this.inventory_quantity = inventory_quantity;
        this.price = price;
        this.unit = unit;
    }

    public Product(String id, String id_category, String img, String name, int inventory_quantity, int price, String unit, String uses) {
        this.id = id;
        this.id_category = id_category;
        this.img = img;
        this.name = name;
        this.inventory_quantity = inventory_quantity;
        this.price = price;
        this.unit = unit;
        this.uses = uses;
    }

    public Product(String id, String id_category, String img, String name, int inventory_quantity, int price, String unit, String uses, String ingredient) {
        this.id = id;
        this.id_category = id_category;
        this.img = img;
        this.name = name;
        this.inventory_quantity = inventory_quantity;
        this.price = price;
        this.unit = unit;
        this.uses = uses;
        this.ingredient = ingredient;
    }

    public Product(String id, String id_category, String img, String name, int inventory_quantity, int price, String unit, String uses, String ingredient, boolean flag_valid) {
        this.id = id;
        this.id_category = id_category;
        this.img = img;
        this.name = name;
        this.inventory_quantity = inventory_quantity;
        this.price = price;
        this.unit = unit;
        this.uses = uses;
        this.ingredient = ingredient;
        this.flag_valid = flag_valid;
    }

    public Product(String id, String id_category, String img, String name, int inventory_quantity, int price, String unit, String uses, String ingredient, boolean flag_valid, boolean prescription) {
        this.id = id;
        this.id_category = id_category;
        this.img = img;
        this.name = name;
        this.inventory_quantity = inventory_quantity;
        this.price = price;
        this.unit = unit;
        this.uses = uses;
        this.ingredient = ingredient;
        this.flag_valid = flag_valid;
        this.prescription = prescription;
    }

    public Product(String id, String id_category, String img, String name, int price, int inventory_quantity, String unit, String uses, String ingredient, boolean flag_valid, boolean prescription, Map<String, Object> unitarrr) {
        this.id = id;
        this.id_category = id_category;
        this.img = img;
        this.name = name;
        this.price = price;
        this.inventory_quantity = inventory_quantity;
        this.unit = unit;
        this.uses = uses;
        this.ingredient = ingredient;
        this.flag_valid = flag_valid;
        this.prescription = prescription;
        this.unitarrr = unitarrr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInventory_quantity() {
        return inventory_quantity;
    }

    public void setInventory_quantity(int inventory_quantity) {
        this.inventory_quantity = inventory_quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUses() {
        return uses;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public boolean isFlag_valid() {
        return flag_valid;
    }

    public void setFlag_valid(boolean flag_valid) {
        this.flag_valid = flag_valid;
    }

    public boolean isPrescription() {
        return prescription;
    }

    public void setPrescription(boolean prescription) {
        this.prescription = prescription;
    }

    public Map<String, Object> getUnitarrr() {
        return unitarrr;
    }

    public void setUnitarrr(Map<String, Object> unitarrr) {
        this.unitarrr = unitarrr;
    }
    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
