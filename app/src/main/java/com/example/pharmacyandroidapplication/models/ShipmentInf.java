package com.example.pharmacyandroidapplication.models;

public class ShipmentInf {
    private String address_id;
    private String user_id;
    private String phone;
    private String address_details;
    private String city;
    private String province;

    public ShipmentInf(String address_id, String user_id, String phone, String address_details, String city, String province) {
        this.address_id = address_id;
        this.user_id = user_id;
        this.phone = phone;
        this.address_details = address_details;
        this.city = city;
        this.province = province;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress_details() {
        return address_details;
    }

    public void setAddress_details(String address_details) {
        this.address_details = address_details;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
