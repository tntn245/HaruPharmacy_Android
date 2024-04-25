package com.example.pharmacyandroidapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ProductStockInDetailsAdapter;
import com.example.pharmacyandroidapplication.adapters.ProductStockOutDetailsAdapter;
import com.example.pharmacyandroidapplication.models.ProductStockInDetails;
import com.example.pharmacyandroidapplication.models.ProductStockOutDetails;

import java.util.ArrayList;
import java.util.Date;

public class WarehouseStockOutDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_stock_out_details);

        GridView StockOutDetails = findViewById(R.id.list_product_stock_out);
        ArrayList<ProductStockOutDetails> productStockOutDetails = new ArrayList<ProductStockOutDetails>();

        productStockOutDetails.add(new ProductStockOutDetails("Chromium", "SDGDSE", 10, R.drawable.pro1));
        productStockOutDetails.add(new ProductStockOutDetails("Omega3", "UDXFDG", 10, R.drawable.pro2));
        productStockOutDetails.add(new ProductStockOutDetails("Thyroid-Pro Formula", "DGBDFS",10, R.drawable.pro3));

        ProductStockOutDetailsAdapter adapter = new ProductStockOutDetailsAdapter(this, productStockOutDetails);
        StockOutDetails.setAdapter(adapter);

        // Nhận giá trị của item từ Intent
        String selectedStockOutID = getIntent().getStringExtra("selectedStockInID");

        // Hiển thị giá trị của item trong layout
        TextView StockOutID = findViewById(R.id.id_stock_out);
        StockOutID.setText(selectedStockOutID);
    }
}