package com.example.pharmacyandroidapplication.models;

import android.widget.CheckBox;
import android.widget.EditText;

public class CheckboxData {
    private CheckBox checkBox;
    private EditText editTextPrice;
    private EditText editTextSellPrice;

    public CheckboxData(CheckBox checkBox, EditText editTextPrice, EditText editTextSellPrice) {
        this.checkBox = checkBox;
        this.editTextPrice = editTextPrice;
        this.editTextSellPrice = editTextSellPrice;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public EditText getEditTextPrice() {
        return editTextPrice;
    }

    public EditText getEditTextSellPrice() {
        return editTextSellPrice;
    }
}