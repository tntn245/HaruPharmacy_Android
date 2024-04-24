package com.example.pharmacyandroidapplication.activities.admin;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ProductStockInDetailsAdapter;
import com.example.pharmacyandroidapplication.models.ProductStockInDetails;

import java.util.ArrayList;
import java.util.Date;

public class WarehouseStockInDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_stock_in_details);
        GridView StockInDetails = findViewById(R.id.list_product_stock_in);
        ArrayList<ProductStockInDetails> productStockInDetails = new ArrayList<ProductStockInDetails>();

        productStockInDetails.add(new ProductStockInDetails("Chromium", "SDGDSE", new Date(2023, 1, 1), new Date(2024, 4, 2), 10, 100000, R.drawable.pro1));
        productStockInDetails.add(new ProductStockInDetails("Omega3", "UDXFDG", new Date(2023, 1, 1), new Date(2024, 4, 1), 10, 100000, R.drawable.pro2));
        productStockInDetails.add(new ProductStockInDetails("Thyroid-Pro Formula", "DGBDFS", new Date(2023, 1, 1), new Date(2024, 3, 29), 10, 100000, R.drawable.pro3));

        ProductStockInDetailsAdapter adapter = new ProductStockInDetailsAdapter(this, productStockInDetails);
        StockInDetails.setAdapter(adapter);
    }
}
