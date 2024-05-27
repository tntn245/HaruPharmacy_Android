package com.example.pharmacyandroidapplication.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.LoginActivity;
import com.example.pharmacyandroidapplication.adapters.ProductGVAdapter;
import com.example.pharmacyandroidapplication.adapters.ProductInventoryAdapter;
import com.example.pharmacyandroidapplication.adapters.ProductInventoryLotNumDetailsAdapter;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.ProductInventoryDetails;
import com.example.pharmacyandroidapplication.models.ProductStockInDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class WarehouseInventoryDetailsActivity extends AppCompatActivity {
    String selectedProductID, selectedProductName, selectedProductUnit, selectedProductImg;
    int selectedProductInventoryQuantity;
    ArrayList<ProductInventoryDetails> productStockInDetails;
    GridView lotnumGV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_inventory_details);

        lotnumGV = findViewById(R.id.list_lotnum);
        productStockInDetails = new ArrayList<ProductInventoryDetails>();

        // Nhận giá trị của item từ Intent
        selectedProductID = getIntent().getStringExtra("selectedProductID");
        selectedProductName = getIntent().getStringExtra("selectedProductName");
        selectedProductUnit = getIntent().getStringExtra("selectedProductUnit");
        selectedProductImg = getIntent().getStringExtra("selectedProductImg");
        selectedProductInventoryQuantity = getIntent().getIntExtra("selectedProductInventoryQuantity", 0);

        loadInventoryFromFirebase();

        // Hiển thị giá trị của item trong layout
        TextView ProductName = findViewById(R.id.product_name);
        ProductName.setText(selectedProductName);

        TextView unit = findViewById(R.id.unit);
        unit.setText(selectedProductUnit);

        TextView ProductInventoryQuantity = findViewById(R.id.total_product_inventory_quantity);
        ProductInventoryQuantity.setText(Integer.toString(selectedProductInventoryQuantity));

        ImageView ProductImg = findViewById(R.id.product_img);
        Glide.with(this.getApplicationContext())
                .load(selectedProductImg)
                .into(ProductImg);
    }

    private void loadInventoryFromFirebase() {
        DatabaseReference lotNumbersRef = FirebaseDatabase.getInstance().getReference("inventory").child(selectedProductID).child(selectedProductUnit);
        lotNumbersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String lotnumber = snapshot.getKey();
                    if (!lotnumber.equals("inventory_quantity")) {
                        Integer stockQuantity = snapshot.child("stock_quantity").getValue(Integer.class);
                        productStockInDetails.add(new ProductInventoryDetails(lotnumber, "", "", stockQuantity));

//                        DatabaseReference dateLotRef = FirebaseDatabase.getInstance().getReference().child("stockIn");
//                        dateLotRef.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                    String lot = snapshot.child("productStockInInf").getValue(String.class);
//                                    Toast.makeText(WarehouseInventoryDetailsActivity.this, lot, Toast.LENGTH_SHORT).show();
//
//                                    if (lotnumber.equals(lot)) {
//                                        String expiration_date = snapshot.child("expiration_date").getValue(String.class);
//                                        String production_date = snapshot.child("production_date").getValue(String.class);
//                                        productStockInDetails.add(new ProductInventoryDetails(lotnumber, production_date, expiration_date, stockQuantity));
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                // Xử lý khi có lỗi xảy ra
//                            }
//                        });
                    }
                }

                ProductInventoryLotNumDetailsAdapter adapter = new ProductInventoryLotNumDetailsAdapter(WarehouseInventoryDetailsActivity.this, productStockInDetails);
                lotnumGV.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });
    }
}