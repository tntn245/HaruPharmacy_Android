package com.example.pharmacyandroidapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.LoginActivity;
import com.example.pharmacyandroidapplication.models.Unit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ChangeRulesActivity extends AppCompatActivity {
    Integer minInventory, minStockIn, percentProfit;
    EditText percent_profit, min_inventory, min_stockin, unit_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_rules);

        percent_profit = findViewById(R.id.percent_profit);
        min_inventory = findViewById(R.id.min_inventory);
        min_stockin = findViewById(R.id.min_stockin);
        unit_name = findViewById(R.id.unit_name);

        Button btn_update = findViewById(R.id.btn_update);
        Button btn_cancel = findViewById(R.id.btn_cancel);
        Button btn_add_unit = findViewById(R.id.btn_add_unit);

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
                Intent intent = new Intent(ChangeRulesActivity.this, ProductManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_add_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseUnits = FirebaseDatabase.getInstance().getReference("unit");
                String unitName = unit_name.getText().toString().trim();
                if (!TextUtils.isEmpty(unitName)) {
                    String unitID = databaseUnits.push().getKey();

                    Map<String, Object> unitMap = new HashMap<>();
                    unitMap.put("unit_name", unitName);

                    // Save the unit to Firebase
                    if (unitID != null) {
                        databaseUnits.child(unitID).setValue(unitMap).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ChangeRulesActivity.this, "Thêm đơn vị thành công", Toast.LENGTH_SHORT).show();
                                unit_name.setText(""); // Clear the input field
                            } else {
                                Toast.makeText(ChangeRulesActivity.this, "Thêm đơn vị thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(ChangeRulesActivity.this, "Vui lòng điền đơn vị cần nhập", Toast.LENGTH_SHORT).show();
                }

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
        if (min_inventory.getText().toString().isEmpty() ||
                min_stockin.getText().toString().isEmpty() ||
                percent_profit.getText().toString().isEmpty()) {
            Toast.makeText(ChangeRulesActivity.this, "Các quy định không được để trống", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("attribute");
            productRef.child("min_inventory").setValue(Integer.parseInt(min_inventory.getText().toString()));
            productRef.child("min_stock_in").setValue(Integer.parseInt(min_stockin.getText().toString()));
            productRef.child("percent_profit").setValue(Integer.parseInt(percent_profit.getText().toString()));
            Intent intent = new Intent(ChangeRulesActivity.this, AdminHomepageActivity.class);
            startActivity(intent);
            finish();
        }
    }
}