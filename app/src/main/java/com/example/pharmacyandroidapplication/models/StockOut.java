package com.example.pharmacyandroidapplication.models;

import java.util.Date;

public class StockOut {
    private String id;
    private Date date;
    private String noted;

    public StockOut(String id, Date date, String noted) {
        this.id = id;
        this.date = date;
        this.noted = noted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNoted() {
        return noted;
    }

    public void setNoted(String noted) {
        this.noted = noted;
    }
}
