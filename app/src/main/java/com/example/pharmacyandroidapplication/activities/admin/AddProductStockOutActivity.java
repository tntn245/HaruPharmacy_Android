package com.example.pharmacyandroidapplication.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pharmacyandroidapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AddProductStockOutActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference inventoryRef = database.getReference("inventory");
    DatabaseReference productRef = database.getReference("product");
    ArrayList<String> productList;
    ArrayList<String> unitList;
    ArrayList<String> lotNumList;
    Spinner spinnerProductName, spinnerUnit, spinnerLotNumber;
    EditText stockout_quantity, lot_stock_quantity, inventory_quantity;
    String selectedProductID;
    String selectedProductName;
    String selectedUnitName;
    String selectedLotNum;
    String outQuantity;
    Button btn_add, btn_cancel;
    int stockQuantity, minInventory, inventoryQuantity;
    HashMap<String, String> productMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_stockout);

        spinnerProductName = findViewById(R.id.spinner_product_name);
        spinnerUnit = findViewById(R.id.spinner_unit);
        spinnerLotNumber = findViewById(R.id.spinner_lot_number);
        stockout_quantity = findViewById(R.id.stockout_quantity);
        lot_stock_quantity = findViewById(R.id.lot_stock_quantity);
        inventory_quantity = findViewById(R.id.inventory_quantity);

        productMap = new HashMap<>();

        btn_add = findViewById(R.id.btn_add);
        btn_cancel = findViewById(R.id.btn_cancel);

        productList = new ArrayList<>();
        loadProductFromFirebase();

        stockout_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý gì trước khi thay đổi
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Không cần xử lý gì trong khi thay đổi
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (!text.isEmpty()) {
                    try {
                        int value = Integer.parseInt(text);
                        if (value < 0) {
                            stockout_quantity.setText("0");
                            stockout_quantity.setSelection(stockout_quantity.getText().length()); // Đặt con trỏ vào cuối
                            Toast.makeText(AddProductStockOutActivity.this, "Value cannot be less than 0", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        // Xử lý ngoại lệ nếu có
                    }
                }
            }
        });
        // Xử lý khi chọn một sản phẩm từ Spinner Product
        spinnerProductName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProductName = spinnerProductName.getSelectedItem().toString();
                selectedProductID = productMap.get(selectedProductName);

                // Truy vấn cơ sở dữ liệu để lấy danh sách đơn vị
                DatabaseReference unitRef = inventoryRef.child(selectedProductID);
                unitRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        unitList = new ArrayList<>();
                        for (DataSnapshot unitSnapshot : dataSnapshot.getChildren()) {
                            unitList.add(unitSnapshot.getKey());
                        }

                        // Cập nhật danh sách đơn vị vào Spinner Unit
                        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(AddProductStockOutActivity.this, android.R.layout.simple_spinner_item, unitList);
                        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerUnit.setAdapter(unitAdapter);

                        if(unitList.size()==0){
                            lotNumList = new ArrayList<>();
                            ArrayAdapter<String> lotnumberAdapter = new ArrayAdapter<>(AddProductStockOutActivity.this, android.R.layout.simple_spinner_item, lotNumList);
                            lotnumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerLotNumber.setAdapter(lotnumberAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi nếu có
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có gì được chọn
            }
        });

        // Xử lý khi chọn một đơn vị từ Spinner Unit
        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProductName = spinnerProductName.getSelectedItem().toString();
                selectedProductID = productMap.get(selectedProductName);
                selectedUnitName = spinnerUnit.getSelectedItem().toString();

                // Truy vấn cơ sở dữ liệu để lấy danh sách lotnumber
                DatabaseReference lotnumberRef = inventoryRef.child(selectedProductID).child(selectedUnitName);
                lotnumberRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        lotNumList = new ArrayList<>();
                        for (DataSnapshot lotnumberSnapshot : dataSnapshot.getChildren()) {
                            String key = lotnumberSnapshot.getKey();
                            Integer lotQuantity = lotnumberSnapshot.child("stock_quantity").getValue(Integer.class);
                            if(key.equals("inventory_quantity")){
                                inventoryQuantity = lotnumberSnapshot.getValue(Integer.class);
                                inventory_quantity.setText(String.valueOf(inventoryQuantity));
                            }
                            else {
                                if (lotQuantity != 0) {
                                    lotNumList.add(key);
                                }
                            }
                        }
                        // Cập nhật danh sách lotnumber vào Spinner Lotnumber
                        ArrayAdapter<String> lotnumberAdapter = new ArrayAdapter<>(AddProductStockOutActivity.this, android.R.layout.simple_spinner_item, lotNumList);
                        lotnumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerLotNumber.setAdapter(lotnumberAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi nếu có
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có gì được chọn
            }
        });
        database.getReference().child("attribute").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    minInventory = dataSnapshot.child("min_inventory").getValue(Integer.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi
            }
        });

        // Xử lý khi chọn một lotnumber từ Spinner Lotnumber
        spinnerLotNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProductName = spinnerProductName.getSelectedItem().toString();
                selectedProductID = productMap.get(selectedProductName);
                selectedUnitName = spinnerUnit.getSelectedItem().toString();
                selectedLotNum = spinnerLotNumber.getSelectedItem().toString();

                // Truy vấn cơ sở dữ liệu để lấy số lượng
                DatabaseReference quantityRef = inventoryRef.child(selectedProductID)
                        .child(selectedUnitName)
                        .child(selectedLotNum)
                        .child("stock_quantity");
                quantityRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        stockQuantity = dataSnapshot.getValue(Integer.class);
                        lot_stock_quantity.setText(String.valueOf(stockQuantity));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi nếu có
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có gì được chọn
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stockout_quantity.getText().toString().isEmpty()){
                    Toast.makeText(AddProductStockOutActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
                }
                else {
                    int outQuantity = Integer.parseInt(stockout_quantity.getText().toString());
                    if(stockQuantity < outQuantity){
                        Toast.makeText(AddProductStockOutActivity.this, "Số lượng vượt quá tồn kho", Toast.LENGTH_LONG).show();
                        Log.d("thongbaone", "Số lượng vượt quá tồn kho");
                    }
                    else if((inventoryQuantity - outQuantity) < minInventory){
                        Toast.makeText(AddProductStockOutActivity.this, "Phải đảm bảo tồn kho tối thiểu là: "+ minInventory, Toast.LENGTH_LONG).show();
                        Log.d("thongbaone", "Phải đảm bảo tồn kho tối thiểu là: "+ minInventory);
                    }
                    else {
                        Intent intent = new Intent(AddProductStockOutActivity.this, AddStockOutActivity.class);
                        intent.putExtra("productID", selectedProductID);
                        intent.putExtra("productName", selectedProductName);
                        intent.putExtra("lotNumber", selectedLotNum);
                        intent.putExtra("unitName", selectedUnitName);
                        intent.putExtra("outQuantity", outQuantity);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    private void loadProductFromFirebase() {
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    // Lấy product ID từ mỗi sản phẩm và thêm vào ArrayList
                    String productID = productSnapshot.getKey();
                    String productName = productSnapshot.child("name").getValue(String.class);
                    productMap.put(productName, productID);
                    productList.add(productName);
                }
                // Sau khi đã có danh sách product ID, cập nhật vào Spinner Product
                ArrayAdapter<String> productAdapter = new ArrayAdapter<>(AddProductStockOutActivity.this, android.R.layout.simple_spinner_item, productList);
                productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProductName.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }

}