package com.example.pharmacyandroidapplication.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.MainActivity;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.ChatActivity;
import com.example.pharmacyandroidapplication.activities.LoginActivity;
import com.example.pharmacyandroidapplication.activities.customer.CustomerHomepageActivity;
import com.google.firebase.auth.FirebaseAuth;

import androidx.cardview.widget.CardView;

public class AdminHomepageActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);

        ImageView btnChat = findViewById(R.id.btnChat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomepageActivity.this, ChatHomepageActivity.class);
                startActivity(intent);
            }
        });

        CardView cardViewProduct = findViewById(R.id.item_qlsp);
        cardViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomepageActivity.this, ProductManagementActivity.class);
                startActivity(intent);
            }
        });

        CardView cardViewCategory = findViewById(R.id.item_qllsp);
        cardViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomepageActivity.this, CategoryManagementActivity.class);
                startActivity(intent);
            }
        });

        CardView cardViewOrder = findViewById(R.id.item_qldh);
        cardViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomepageActivity.this, OrderManagementActivity.class);
                startActivity(intent);
            }
        });

        CardView cardViewWH = findViewById(R.id.item_qlkho);
        cardViewWH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomepageActivity.this, WarehouseHomeActivity.class);
                startActivity(intent);
            }
        });

        CardView cardViewStatistic = findViewById(R.id.item_thongke);
        cardViewStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomepageActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });
        CardView cardViewLogout = findViewById(R.id.item_logout);
        cardViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AdminHomepageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
