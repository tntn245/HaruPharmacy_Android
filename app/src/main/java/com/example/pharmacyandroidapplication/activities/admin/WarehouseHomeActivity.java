package com.example.pharmacyandroidapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.StockInAdapter;
import com.google.android.material.button.MaterialButton;

public class WarehouseHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_home);

        MaterialButton btnInventory = findViewById(R.id.inventory);
        btnInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarehouseHomeActivity.this, WarehouseInventoryActivity.class); // Thay OtherActivity bằng tên Activity hoặc Fragment bạn muốn chuyển đến
                startActivity(intent);
            }
        });

        MaterialButton btnStockIn = findViewById(R.id.stockin);
        btnStockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarehouseHomeActivity.this, WarehouseStockInActivity.class); // Thay OtherActivity bằng tên Activity hoặc Fragment bạn muốn chuyển đến
                startActivity(intent);
            }
        });

        MaterialButton btnStockOut = findViewById(R.id.stockout);
        btnStockOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarehouseHomeActivity.this, WarehouseStockOutActivity.class); // Thay OtherActivity bằng tên Activity hoặc Fragment bạn muốn chuyển đến
                startActivity(intent);
            }
        });
    }
}