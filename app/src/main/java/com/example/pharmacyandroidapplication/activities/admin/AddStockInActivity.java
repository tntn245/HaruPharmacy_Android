package com.example.pharmacyandroidapplication.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.LoginActivity;
import com.example.pharmacyandroidapplication.adapters.ProductStockInDetailsAdapter;
import com.example.pharmacyandroidapplication.models.ProductStockInDetails;
import com.example.pharmacyandroidapplication.models.StockIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddStockInActivity extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    int total_stockin_price = 0;
    TextView txt_total_stockin_price;
    String productID, productName, lotNumber, unitName, productionDate, expirationDate;
    int inQuantity, unitPrice;
    ArrayList<ProductStockInDetails> productStockInDetails;
    GridView StockInDetails;
    ProductStockInDetailsAdapter adapter;
    EditText date_stock_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stockin);

        StockInDetails = findViewById(R.id.list_product_stock_in);
        productStockInDetails = new ArrayList<ProductStockInDetails>();
        adapter = new ProductStockInDetailsAdapter(this, productStockInDetails, true);
        StockInDetails.setAdapter(adapter);
        StockInDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductStockInDetails selectedProduct = productStockInDetails.get(position);
                String productId = selectedProduct.getProduct_id();
                Toast.makeText(AddStockInActivity.this, "Mã phiếu: " + productId, Toast.LENGTH_SHORT).show();
                // Perform actions based on the selected product ID
            }
        });

        txt_total_stockin_price = findViewById(R.id.total_stockin_price);
        date_stock_in = findViewById(R.id.date_stock_in);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        date_stock_in.setText(currentDate);
        date_stock_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        Button btn_add = findViewById(R.id.btn_add_product_stockin);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddStockInActivity.this, AddProductStockInActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        Button btn_add_stockin = findViewById(R.id.btn_add_stockin);
        btn_add_stockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productStockInDetails.size()==0){
                    Toast.makeText(AddStockInActivity.this, "Không thể thêm phiếu khi chưa chọn sản phẩm", Toast.LENGTH_LONG).show();
                }
                else {
                    addFirebase();
                    Intent intent = new Intent(AddStockInActivity.this, WarehouseStockInActivity.class);
                    startActivity(intent);
                    finish();
                }
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
            productionDate = data.getStringExtra("productionDate");
            expirationDate = data.getStringExtra("expirationDate");
            inQuantity = data.getIntExtra("inQuantity", 0);
            unitPrice = data.getIntExtra("unitPrice", 0);
            Toast.makeText(this, "Đã chọn " + productName, Toast.LENGTH_LONG).show();

            total_stockin_price = total_stockin_price + inQuantity * unitPrice;
            txt_total_stockin_price.setText(String.valueOf(total_stockin_price));

            productStockInDetails.add(new ProductStockInDetails(productID, productName, lotNumber, productionDate, expirationDate, inQuantity, inQuantity, unitPrice, unitName, ""));
            adapter.notifyDataSetChanged();
        }
    }
    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận thêm phiếu nhập kho")
                .setMessage("Sau khi thêm, phiếu sẽ không được chỉnh sửa hay xóa. Bạn có muốn thêm phiếu này không?")
                .setPositiveButton("Xác nhận", (dialog, which) -> {
                    Toast.makeText(AddStockInActivity.this, "Xác nhận thêm phiếu", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }
    private void addFirebase() {
        DatabaseReference stockInRef = database.child("stockIn").push();
        stockInRef.child("productStockInInf").setValue(productStockInDetails);
        stockInRef.child("totalPrice").setValue(total_stockin_price);
        stockInRef.child("stockInDate").setValue(date_stock_in.getText().toString());

        DatabaseReference databaseInventory = FirebaseDatabase.getInstance().getReference("inventory");
        DatabaseReference quantityRef = databaseInventory.child(productID).child(unitName).child("inventory_quantity");

        quantityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    updateInventoryQuantity(productID, unitName);
                } else {
                    databaseInventory.child(productID).child(unitName).child("inventory_quantity").setValue(inQuantity);
                    databaseInventory.child(productID).child(unitName).child(lotNumber).child("stock_quantity").setValue(inQuantity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });

    }

    private void updateInventoryQuantity(String productID, String unitName) {
        DatabaseReference inventoryQuantityRef = FirebaseDatabase.getInstance().getReference("inventory").
                child(productID).child(unitName).child("inventory_quantity");

        inventoryQuantityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer currentInventoryQuantity = dataSnapshot.getValue(Integer.class);
                if (currentInventoryQuantity == null) {
                    currentInventoryQuantity = 0;
                }
                int newInventoryQuantity = currentInventoryQuantity + inQuantity;
                inventoryQuantityRef.setValue(newInventoryQuantity).addOnSuccessListener(aVoid -> {
                    Toast.makeText(AddStockInActivity.this, "Cập nhật tổng tồn kho thành công", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(AddStockInActivity.this, "Failed to update inventory quantity: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddStockInActivity.this, "Failed to read inventory quantity: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                date_stock_in.setText(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
}