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
import com.example.pharmacyandroidapplication.activities.LoginActivity;
import com.example.pharmacyandroidapplication.activities.customer.SignUpActivity;
import com.example.pharmacyandroidapplication.adapters.ProductGVAdapter;
import com.example.pharmacyandroidapplication.adapters.StockInAdapter;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.StockIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WarehouseStockInActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<StockIn> StockArrayList;
    GridView StockInWH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_stock_in);
        StockInWH= findViewById(R.id.list_stock_in);

        StockArrayList = new ArrayList<StockIn>();
        loadStockInFromFirebase();

        // Đặt sự kiện click cho mỗi item trong GridView
        StockInWH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị của item được click
                StockIn itemStockIn = StockArrayList.get(position);

                // Truyền giá trị của item qua layout tiếp theo để hiển thị
                Intent intent = new Intent(WarehouseStockInActivity.this, WarehouseStockInDetailsActivity.class);
                intent.putExtra("selectedStockInID", itemStockIn.getId());
                startActivity(intent);
            }
        });

        ImageView btn_add = findViewById(R.id.btn_add_stockin);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarehouseStockInActivity.this, AddStockInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView btn_back = findViewById(R.id.back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarehouseStockInActivity.this, WarehouseHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void loadStockInFromFirebase() {

        DatabaseReference productsRef = database.getReference("stockIn");
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String stockInID = snapshot.getKey();
                    Integer totalPrice = snapshot.child("totalPrice").getValue(Integer.class);
                    if(totalPrice==null){
                        totalPrice = 0;
                    }
                    String stockInDate = snapshot.child("stockInDate").getValue(String.class);
//                    String pattern = "dd/MM/yyyy"; // Định dạng của chuỗi ngày tháng
//                    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
//                    try {
//                        Date date = dateFormat.parse(stockInDate);
                        StockArrayList.add(new StockIn(stockInID,stockInDate, totalPrice));
//                    } catch (ParseException e) {
//                        System.out.println("Error parsing date: " + e.getMessage());
//                    }

                }
                StockInAdapter adapter = new StockInAdapter(WarehouseStockInActivity.this, StockArrayList);
                StockInWH.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}