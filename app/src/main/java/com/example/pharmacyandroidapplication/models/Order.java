package com.example.pharmacyandroidapplication.models;

public class Order {
    private String id_order, id_account;
    private int total_payment;
    private boolean payment_status, order_status;
    private String note;
    private String address;

    public Order(String id_order, String id_account, int total_payment, boolean payment_status, boolean order_status, String note, String address) {
        this.id_order = id_order;
        this.id_account = id_account;
        this.total_payment = total_payment;
        this.payment_status = payment_status;
        this.order_status = order_status;
        this.note = note;
        this.address = address;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getId_account() {
        return id_account;
    }

    public void setId_account(String id_account) {
        this.id_account = id_account;
    }

    public int getTotal_payment() {
        return total_payment;
    }

    public void setTotal_payment(int total_payment) {
        this.total_payment = total_payment;
    }

    public boolean isPayment_status() {
        return payment_status;
    }

    public void setPayment_status(boolean payment_status) {
        this.payment_status = payment_status;
    }

    public boolean isOrder_status() {
        return order_status;
    }

    public void setOrder_status(boolean order_status) {
        this.order_status = order_status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
