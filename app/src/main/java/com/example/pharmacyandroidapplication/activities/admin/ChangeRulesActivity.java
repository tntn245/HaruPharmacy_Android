package com.example.pharmacyandroidapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pharmacyandroidapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeRulesActivity extends AppCompatActivity {
    Integer minInventory, minStockIn, percentProfit;
    EditText percent_profit, min_inventory, min_stockin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_rules);

        percent_profit = findViewById(R.id.percent_profit);
        min_inventory = findViewById(R.id.min_inventory);
        min_stockin = findViewById(R.id.min_stockin);

        Button btn_update = findViewById(R.id.btn_update);
        Button btn_cancel = findViewById(R.id.btn_cancel);

        loadData();
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void loadData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("attribute").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Lấy ra các giá trị từ DataSnapshot
                minInventory = dataSnapshot.child("min_inventory").getValue(Integer.class);
                minStockIn = dataSnapshot.child("min_stock_in").getValue(Integer.class);
                percentProfit = dataSnapshot.child("percent_profit").getValue(Integer.class);

                percent_profit.setText(String.valueOf(percentProfit));
                min_inventory.setText(String.valueOf(minInventory));
                min_stockin.setText(String.valueOf(minStockIn));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu
            }
        });

    }

    public void update() {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("attribute");
        productRef.child("min_inventory").setValue(Integer.parseInt(min_inventory.getText().toString()));
        productRef.child("min_stock_in").setValue(Integer.parseInt(min_stockin.getText().toString()));
        productRef.child("percent_profit").setValue(Integer.parseInt(percent_profit.getText().toString()));
    }
}