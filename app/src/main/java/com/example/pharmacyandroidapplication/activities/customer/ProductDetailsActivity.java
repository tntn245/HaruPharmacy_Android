package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.MainActivity;
import com.example.pharmacyandroidapplication.R;

public class ProductDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Nhận giá trị của item từ Intent
        int product_img = getIntent().getIntExtra("product_img", 0);
        String product_name = getIntent().getStringExtra("product_name");
        int product_price = getIntent().getIntExtra("product_price", 0);

        // Hiển thị giá trị của item trong layout
        ImageView ProductImg = findViewById(R.id.product_img);
        TextView ProductName = findViewById(R.id.product_name);
        TextView ProductPrice = findViewById(R.id.product_price);
        ProductImg.setImageResource(product_img);
        ProductName.setText(product_name);
        ProductPrice.setText(product_price);
    }
}
