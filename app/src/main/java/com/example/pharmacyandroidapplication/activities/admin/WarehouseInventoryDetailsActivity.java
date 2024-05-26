package com.example.pharmacyandroidapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ProductGVAdapter;
import com.example.pharmacyandroidapplication.adapters.ProductInventoryAdapter;
import com.example.pharmacyandroidapplication.adapters.ProductInventoryLotNumDetailsAdapter;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.ProductStockInDetails;

import java.util.ArrayList;
import java.util.Date;

public class WarehouseInventoryDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_inventory_details);

        GridView lotnumGV= findViewById(R.id.list_lotnum);
        ArrayList<ProductStockInDetails> productStockInDetails = new ArrayList<ProductStockInDetails>();

        productStockInDetails.add(new ProductStockInDetails("","Chromium", "SDGDSE", "1/1/2020", "2/2/2020", 10, 8, 100000, "lọ", ""));

        ProductInventoryLotNumDetailsAdapter adapter = new ProductInventoryLotNumDetailsAdapter(this, productStockInDetails);
        lotnumGV.setAdapter(adapter);

        // Nhận giá trị của item từ Intent
        String selectedProductID = getIntent().getStringExtra("selectedProductID");
        String selectedProductName = getIntent().getStringExtra("selectedProductName");
        int selectedProductImg = getIntent().getIntExtra("selectedProductImg", 0);
        int selectedProductInventoryQuantity = getIntent().getIntExtra("selectedProductInventoryQuantity", 0);

        // Hiển thị giá trị của item trong layout
        TextView ProductName = findViewById(R.id.product_name);
        ProductName.setText(selectedProductName);

        TextView ProductInventoryQuantity = findViewById(R.id.total_product_inventory_quantity);
        ProductInventoryQuantity.setText(Integer.toString(selectedProductInventoryQuantity));

        ImageView ProductImg = findViewById(R.id.product_img);
        ProductImg.setImageResource(selectedProductImg);
    }
}