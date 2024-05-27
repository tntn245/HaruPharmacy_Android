package com.example.pharmacyandroidapplication.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ProductStockInDetailsAdapter;
import com.example.pharmacyandroidapplication.adapters.ProductStockOutDetailsAdapter;
import com.example.pharmacyandroidapplication.models.ProductStockInDetails;
import com.example.pharmacyandroidapplication.models.ProductStockOutDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddStockOutActivity extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    String productID, productName, lotNumber, unitName, noted;
    int outQuantity;
    ArrayList<ProductStockOutDetails> productStockOutDetails;
    GridView StockOutDetails;
    ProductStockOutDetailsAdapter adapter;
    EditText stockout_date, txt_noted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stockout);

        StockOutDetails = findViewById(R.id.list_product_stock_out);
        productStockOutDetails = new ArrayList<ProductStockOutDetails>();
        adapter = new ProductStockOutDetailsAdapter(this, productStockOutDetails);
        StockOutDetails.setAdapter(adapter);

        txt_noted = findViewById(R.id.noted);
        stockout_date = findViewById(R.id.stockout_date);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        stockout_date.setText(currentDate);
        stockout_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        Button btn_add_product = findViewById(R.id.btn_add_product_stockout);
        btn_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddStockOutActivity.this, AddProductStockOutActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        Button btn_add_stockin =findViewById(R.id.btn_add_stockout);
        btn_add_stockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFirebase();
            }
        });

        Button btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            productID = data.getStringExtra("productID");
            productName = data.getStringExtra("productName");
            lotNumber = data.getStringExtra("lotNumber");
            unitName = data.getStringExtra("unitName");
            outQuantity = data.getIntExtra("outQuantity", 0);
            noted = data.getStringExtra("noted");
            Toast.makeText(this, "Đã chọn " + productName, Toast.LENGTH_LONG).show();

            productStockOutDetails.add(new ProductStockOutDetails(productName, lotNumber,outQuantity,""));
            adapter.notifyDataSetChanged();
        }
    }
    private void addFirebase(){
        if(txt_noted.getText().toString().isEmpty()){
            Toast.makeText(AddStockOutActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            Log.d("thongbaone", "Vui lòng điền đầy đủ thông tin");
        }
        else if(productStockOutDetails.size() == 0){
            Toast.makeText(AddStockOutActivity.this, "Bạn chưa chọn sản phẩm nào để xuất kho", Toast.LENGTH_SHORT).show();
            Log.d("thongbaone", "Bạn chưa chọn sản phẩm nào để xuất kho");
        }
        else {
            DatabaseReference stockOutRef = database.child("stockOut").push();
            stockOutRef.child("productStockInInf").setValue(productStockOutDetails);
            stockOutRef.child("noted").setValue(txt_noted.getText().toString());
            stockOutRef.child("stockOutDate").setValue(stockout_date.getText().toString());

            DatabaseReference databaseInventory = FirebaseDatabase.getInstance().getReference("inventory");
//        DatabaseReference quantityRef = databaseInventory.child(productID).child(unitName).child(lotNumber).child("stock_quantity");
            DatabaseReference quantityRef = databaseInventory.child(productID).child(unitName).child("inventory_quantity");
            quantityRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Integer currentQuantity = dataSnapshot.getValue(Integer.class);
                    if (currentQuantity != null) {
                        int newQuantity = currentQuantity - outQuantity;
                        quantityRef.setValue(newQuantity).addOnSuccessListener(aVoid -> {
                            Toast.makeText(AddStockOutActivity.this, "Cập nhật số lượng thành công", Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(e -> {
                            Toast.makeText(AddStockOutActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    } else {
                        Toast.makeText(AddStockOutActivity.this, "Số lượng tồn kho hiện tại null", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(AddStockOutActivity.this, "Failed to read stock quantity: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();                }
            });

            Intent intent = new Intent(AddStockOutActivity.this, WarehouseStockOutActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void showDatePickerDialog(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Khi người dùng chọn ngày, cập nhật TextView với ngày đã chọn
                Calendar selectedCalendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                selectedCalendar.set(year, month, dayOfMonth);
                String selectedDate = dateFormat.format(selectedCalendar.getTime());
                stockout_date.setText(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
}