package com.example.pharmacyandroidapplication.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ProductStockInDetailsAdapter;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.ProductStockInDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class WarehouseStockInDetailsActivity extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    String selectedStockInID;
    ArrayList<ProductStockInDetails> productStockInDetails;
    GridView StockInDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_stock_in_details);

        // Nhận giá trị của item từ Intent
        selectedStockInID = getIntent().getStringExtra("selectedStockInID");

        StockInDetails = findViewById(R.id.list_product_stock_in);
        productStockInDetails = new ArrayList<ProductStockInDetails>();

        loadProductStockInDetails();

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

    private void loadProductStockInDetails() {
        DatabaseReference stockInRef = database.child("stockIn").child(selectedStockInID);
        stockInRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot productStockInInfSnapshot = dataSnapshot.child("productStockInInf");
                    for (DataSnapshot productSnapshot : productStockInInfSnapshot.getChildren()) {
                        Log.d("selectedStockInID", selectedStockInID);
                        String expiration_date = productSnapshot.child("expiration_date").getValue(String.class);
                        int in_quantity = productSnapshot.child("in_quantity").getValue(Integer.class);
                        String lot_number = productSnapshot.child("lot_number").getValue(String.class);
                        String product_id = productSnapshot.child("product_id").getValue(String.class);
                        String product_name = productSnapshot.child("product_name").getValue(String.class);
                        String production_date = productSnapshot.child("production_date").getValue(String.class);
                        int quantity_in_stock = productSnapshot.child("quantity_in_stock").getValue(Integer.class);
                        String unit = productSnapshot.child("unit").getValue(String.class);
                        int unit_price = productSnapshot.child("unit_price").getValue(Integer.class);
                        String img = productSnapshot.child("img").getValue(String.class);
                        productStockInDetails.add(new ProductStockInDetails(product_id,product_name,lot_number,production_date,expiration_date,in_quantity,quantity_in_stock,unit_price,unit,img));
                    }
                    ProductStockInDetailsAdapter adapter = new ProductStockInDetailsAdapter(WarehouseStockInDetailsActivity.this, productStockInDetails, false);
                    StockInDetails.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi
            }
        });
    }
}
