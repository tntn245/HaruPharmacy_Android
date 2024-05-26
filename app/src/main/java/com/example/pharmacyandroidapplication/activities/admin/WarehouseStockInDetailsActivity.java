package com.example.pharmacyandroidapplication.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ProductStockInDetailsAdapter;
import com.example.pharmacyandroidapplication.models.ProductStockInDetails;

import java.util.ArrayList;
import java.util.Date;

public class WarehouseStockInDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_stock_in_details);


        GridView StockInDetails = findViewById(R.id.list_product_stock_in);
        ArrayList<ProductStockInDetails> productStockInDetails = new ArrayList<ProductStockInDetails>();

        productStockInDetails.add(new ProductStockInDetails("","Omega3", "UDXFDG", "1/1/2020", "1/1/2024", 15, 15,100000, "chai", ""));

        ProductStockInDetailsAdapter adapter = new ProductStockInDetailsAdapter(this, productStockInDetails, false);
        StockInDetails.setAdapter(adapter);

        // Nhận giá trị của item từ Intent
        String selectedStockInID = getIntent().getStringExtra("selectedStockInID");

        // Hiển thị giá trị của item trong layout
        TextView StockInID = findViewById(R.id.id_stock_in);
        StockInID.setText(selectedStockInID);


        ImageView btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(WarehouseStockInDetailsActivity.this, AddStockInActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
    }
}
