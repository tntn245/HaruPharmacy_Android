package com.example.pharmacyandroidapplication.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    private int day;
    private int month;
    private int year;

    public DateFormat(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public DateFormat(Date date) {
        this.day = date.getDate();
        this.month = date.getMonth();
        this.year = date.getYear();
    }
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public String formatDateToString() {
        Date date = new Date(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}
