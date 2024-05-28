package com.example.pharmacyandroidapplication.models;

import java.util.Date;

public class StockIn {
    private String id;
    private String date;
    private int total_payment;


    public StockIn(String id, String date, int total_payment) {
        this.id = id;
        this.date = date;
        this.total_payment = total_payment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal_payment() {
        return total_payment;
    }

    public void setTotal_payment(int total_payment) {
        this.total_payment = total_payment;
    }

}
