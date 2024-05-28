package com.example.pharmacyandroidapplication.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.StockInAdapter;
import com.example.pharmacyandroidapplication.adapters.StockOutAdapter;
import com.example.pharmacyandroidapplication.models.StockIn;
import com.example.pharmacyandroidapplication.models.StockOut;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class WarehouseStockOutActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<StockOut> StockArrayList;
    GridView StockOutGV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_stock_out);

        StockOutGV= findViewById(R.id.list_stock_out);
        StockArrayList = new ArrayList<StockOut>();
        loadProductStockOut();

        // Đặt sự kiện click cho mỗi item trong GridView
        StockOutGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị của item được click
                StockOut itemStockOut = StockArrayList.get(position);

                // Truyền giá trị của item qua layout tiếp theo để hiển thị
                Intent intent = new Intent(WarehouseStockOutActivity.this, WarehouseStockOutDetailsActivity.class);
                intent.putExtra("selectedStockOutID", itemStockOut.getId());
                startActivity(intent);
            }
        });

        ImageView btn_add = findViewById(R.id.btn_add_stockout);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarehouseStockOutActivity.this, AddStockOutActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView btn_back = findViewById(R.id.back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarehouseStockOutActivity.this, WarehouseHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadProductStockOut() {
        DatabaseReference productsRef = database.getReference("stockOut");
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String stockOutID = snapshot.getKey();
                    String stockOutDate = snapshot.child("stockOutDate").getValue(String.class);
                    String noted = snapshot.child("noted").getValue(String.class);
                    if(noted==null){
                        noted = "";
                    }
                    StockArrayList.add(new StockOut(stockOutID,stockOutDate, noted));
                }
                StockOutAdapter adapter = new StockOutAdapter(WarehouseStockOutActivity.this, StockArrayList);
                StockOutGV.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}