package com.example.pharmacyandroidapplication.models;

public class ShipmentInf {
    private String address_id;
    private String user_id;
    private String receiverName;
    private String phone;
    private String province;
    private String district;
    private String commune;
    private String address_details;


    public ShipmentInf(String address_id, String user_id, String phone, String province, String district, String commune, String address_details) {
        this.address_id = address_id;
        this.user_id = user_id;
        this.phone = phone;
        this.address_details = address_details;
        this.province = province;
        this.district = district;
        this.commune = commune;
        this.address_details = address_details;
    }

    public ShipmentInf(String address_id, String user_id, String receiverName,
                       String phone, String province, String district, String commune, String address_details) {
        this.address_id = address_id;
        this.user_id = user_id;
        this.receiverName = receiverName;
        this.phone = phone;
        this.province = province;
        this.district = district;
        this.commune = commune;
        this.address_details = address_details;
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

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getAddress_details() {
        return address_details;
    }

    public void setAddress_details(String address_details) {
        this.address_details = address_details;
    }
}
