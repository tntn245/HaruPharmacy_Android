package com.example.pharmacyandroidapplication.activities;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.HomeCategoryAdapter;
import com.example.pharmacyandroidapplication.adapters.HomeProductAdapter;
import com.example.pharmacyandroidapplication.models.Category;
import com.example.pharmacyandroidapplication.models.Product;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        GridView categoryGV= findViewById(R.id.rcv_category);
        ArrayList<Category> CategoryArrayList = new ArrayList<Category>();

        CategoryArrayList.add(new Category("Cải thiện giấc ngủ"));
        CategoryArrayList.add(new Category("Hỗ trợ gan"));
        CategoryArrayList.add(new Category("Hỗ trợ tim mạch"));

        HomeCategoryAdapter categoryAdapter = new HomeCategoryAdapter(this, CategoryArrayList);
        categoryGV.setAdapter(categoryAdapter);

        GridView productGV= findViewById(R.id.rcv_shopping);
        ArrayList<Product> ProductArrayList = new ArrayList<Product>();

        ProductArrayList.add(new Product("Chromium", 100000, R.drawable.pro1));
        ProductArrayList.add(new Product("Omega3",200000, R.drawable.pro2));
        ProductArrayList.add(new Product("Thyroid-Pro Formula",150000, R.drawable.pro3));

        HomeProductAdapter productAdapter = new HomeProductAdapter(this, ProductArrayList);
        productGV.setAdapter(productAdapter);

    }
}

