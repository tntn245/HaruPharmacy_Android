package com.example.pharmacyandroidapplication.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ProductStockInDetailsAdapter;
import com.example.pharmacyandroidapplication.adapters.ProductStockOutDetailsAdapter;
import com.example.pharmacyandroidapplication.models.ProductStockInDetails;
import com.example.pharmacyandroidapplication.models.ProductStockOutDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class WarehouseStockOutDetailsActivity extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    String selectedStockOutID;
    ArrayList<ProductStockOutDetails> productStockOutDetails;
    GridView StockOutDetails;
    TextView date_stock_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_stock_out_details);

        selectedStockOutID = getIntent().getStringExtra("selectedStockOutID");

        date_stock_out = findViewById(R.id.date_stock_out);

        StockOutDetails = findViewById(R.id.list_product_stock_out);
        productStockOutDetails = new ArrayList<ProductStockOutDetails>();

        loadProductStockOutDetails();

        // Nhận giá trị của item từ Intent
        String selectedStockOutID = getIntent().getStringExtra("selectedStockInID");

        // Hiển thị giá trị của item trong layout
        TextView StockOutID = findViewById(R.id.id_stock_out);
        StockOutID.setText(selectedStockOutID);
    }

    private void loadProductStockOutDetails() {
        DatabaseReference stockInRef = database.child("stockOut").child(selectedStockOutID);
        stockInRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String date_stockout = dataSnapshot.child("stockOutDate").getValue(String.class);
                    date_stock_out.setText(date_stockout);

                    DataSnapshot productStockInInfSnapshot = dataSnapshot.child("productStockInInf");
                    for (DataSnapshot productSnapshot : productStockInInfSnapshot.getChildren()) {
                        Log.d("selectedStockOutID", selectedStockOutID);
                        int out_quantity = productSnapshot.child("out_quantity").getValue(Integer.class);
                        String lot_number = productSnapshot.child("lot_number").getValue(String.class);
                        String product_img = productSnapshot.child("product_img").getValue(String.class);
                        String product_name = productSnapshot.child("product_name").getValue(String.class);
                        productStockOutDetails.add(new ProductStockOutDetails(product_name,lot_number,out_quantity,product_img));
                    }

                    ProductStockOutDetailsAdapter adapter = new ProductStockOutDetailsAdapter(WarehouseStockOutDetailsActivity.this, productStockOutDetails);
                    StockOutDetails.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi
            }
        });
    }
}