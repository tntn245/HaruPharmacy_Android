package com.example.pharmacyandroidapplication.models;

import java.util.Date;

public class StockIn {
    private Date date;
    private int total_payment;

    public StockIn(Date date, int total_payment) {
        this.date = date;
        this.total_payment = total_payment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotal_payment() {
        return total_payment;
    }

    public void setTotal_payment(int total_payment) {
        this.total_payment = total_payment;
    }
}
