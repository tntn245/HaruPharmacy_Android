package com.example.pharmacyandroidapplication;
import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.adapters.HomeCategoryAdapter;
import com.example.pharmacyandroidapplication.models.Category;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.adapters.HomeProductAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}