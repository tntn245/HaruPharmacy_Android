package com.example.pharmacyandroidapplication.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class AddProductStockInActivity extends AppCompatActivity {
    Button btn_add_product_stockin, btn_cancel;
    Spinner spinnerProductName, spinnerUnit;
    EditText lot_number, in_quantity, unit_price, production_date, expiration_date;
    TextView quantity_in_stock;
    ArrayAdapter<Product> productNameAdapter;
    ArrayAdapter<String> unitAdapter;
    ArrayList<Product> productList;
    ArrayList<String> unitList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String selectedProductID;
    String selectedUnitName;
    String lotNum;
    String inQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_stockin);

        spinnerProductName = findViewById(R.id.spinnerProductName);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        lot_number = findViewById(R.id.lot_number);
        in_quantity = findViewById(R.id.in_quantity);
        unit_price = findViewById(R.id.unit_price);
        production_date = findViewById(R.id.production_date);
        expiration_date = findViewById(R.id.expiration_date);
        quantity_in_stock = findViewById(R.id.quantity_in_stock);
        btn_add_product_stockin = findViewById(R.id.btn_add_product_stockin);
        btn_cancel = findViewById(R.id.btn_cancel);

        productList = new ArrayList<>();
        unitList = new ArrayList<>();

        UUID uniqueID = UUID.randomUUID();
        lotNum = uniqueID.toString();
        lot_number.setText(lotNum);
        loadProductFromFirebase();

        spinnerProductName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = productList.get(position);
                selectedProductID = selectedProduct.getId();
                Toast.makeText(AddProductStockInActivity.this,selectedProductID , Toast.LENGTH_SHORT).show();

                loadUnits(selectedProductID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có sản phẩm nào được chọn
            }
        });
        // Lắng nghe sự kiện khi chọn đơn vị từ spinner unit
        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUnitName = (String) parent.getItemAtPosition(position);
                loadUnitInfo(selectedProductID, selectedUnitName); // Load thông tin của đơn vị được chọn
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có đơn vị nào được chọn
            }
        });

        production_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v, production_date);
            }
        });

        expiration_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v, expiration_date);
            }
        });
        btn_add_product_stockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(in_quantity.getText().toString().isEmpty() ||
                        production_date.getText().toString().isEmpty()||
                        expiration_date.getText().toString().isEmpty()||
                        unit_price.getText().toString().isEmpty()){
                    Toast.makeText(AddProductStockInActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(AddProductStockInActivity.this, AddStockInActivity.class);
                    intent.putExtra("productID", selectedProductID);
                    intent.putExtra("productName", spinnerProductName.getSelectedItem().toString());
                    intent.putExtra("lotNumber", lotNum);
                    intent.putExtra("unitName", selectedUnitName);
                    intent.putExtra("inQuantity", Integer.parseInt(in_quantity.getText().toString()));
                    intent.putExtra("productionDate", production_date.getText().toString());
                    intent.putExtra("expirationDate", expiration_date.getText().toString());
                    intent.putExtra("unitPrice", Integer.parseInt(unit_price.getText().toString()));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void loadUnits(String productID) {
        DatabaseReference unitsRef = FirebaseDatabase.getInstance().getReference().child("product").child(productID).child("unitarrr");
        unitsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> unitNames = new ArrayList<>();
                for (DataSnapshot unitSnapshot : dataSnapshot.getChildren()) {
                    String unitName = unitSnapshot.getKey();
                    unitNames.add(unitName);
                }
                // Hiển thị các tên đơn vị trong spinner unit
                ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(AddProductStockInActivity.this, android.R.layout.simple_spinner_item, unitNames);
                unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUnit.setAdapter(unitAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình truy vấn Firebase
            }
        });
    }

    // Hàm để load thông tin của đơn vị được chọn từ Firebase
    private void loadUnitInfo(String productID, String unitName) {
        DatabaseReference unitRef = FirebaseDatabase.getInstance().getReference().child("product").child(productID).child("unitarrr").child(unitName);
        unitRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Lấy thông tin về quantity, price, và sell_price của đơn vị
                    int quantity = dataSnapshot.child("quantity").getValue(Integer.class);
                    int price = dataSnapshot.child("price").getValue(Integer.class);
//                    int sellPrice = dataSnapshot.child("sell_price").getValue(Integer.class);
                    quantity_in_stock.setText(String.valueOf(quantity));
                    unit_price.setText(String.valueOf(price));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình truy vấn Firebase
            }
        });
    }

    private void loadProductFromFirebase() {
        DatabaseReference productsRef = database.getReference("product");
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy dữ liệu từ snapshot và tạo đối tượng Product
                    String productName = snapshot.child("name").getValue(String.class);
                    int productPrice = snapshot.child("price").getValue(Integer.class);
                    String productImg = snapshot.child("img").getValue(String.class);
                    String id = snapshot.child("id").getValue(String.class);
                    String id_category = snapshot.child("id_category").getValue(String.class);
                    String unit = snapshot.child("unit").getValue(String.class);
//                    int inventory_quantity = snapshot.child("inventory_quantity").getValue(Integer.class);
                    String uses = snapshot.child("uses").getValue(String.class);
                    String ingredient = snapshot.child("ingredient").getValue(String.class);
                    Boolean flagValid = Boolean.TRUE.equals(snapshot.child("flag_valid").getValue(Boolean.class));
                    Boolean prescription = Boolean.TRUE.equals(snapshot.child("prescription").getValue(Boolean.class));

                    Map<String, Object> unitArr = new HashMap<>();
                    // Lấy dữ liệu từ unitarr
                    for (DataSnapshot unitSnapshot : snapshot.child("unitarrr").getChildren()) {
                        String unitName = unitSnapshot.getKey();
                        Integer price = (unitSnapshot.child("price").getValue(Integer.class) != null)
                                ? unitSnapshot.child("price").getValue(Integer.class) : 0;
                        Integer sell_price = (unitSnapshot.child("sell_price").getValue(Integer.class) != null)
                                ? unitSnapshot.child("sell_price").getValue(Integer.class) : 0;
                        Integer quantity = (unitSnapshot.child("quantity").getValue(Integer.class) != null)
                                ? unitSnapshot.child("quantity").getValue(Integer.class) : 0;

                        // Tạo một map để chứa giá và số lượng của đơn vị
                        Map<String, Object> unitData = new HashMap<>();
                        unitData.put("price", price);
                        unitData.put("sell_price", sell_price);
                        unitData.put("quantity", quantity);

                        // Thêm đơn vị vào unitArr
                        unitArr.put(unitName, unitData);
                    }
                    Product product = new Product(id, id_category, productImg, productName, 0, productPrice, unit, uses, ingredient, flagValid, prescription, unitArr);
                    // Sau đó, thêm sản phẩm vào danh sách productList
                    productList.add(product);
                }

                productNameAdapter = new ArrayAdapter<Product>(AddProductStockInActivity.this, android.R.layout.simple_spinner_item, productList);
                productNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProductName.setAdapter(productNameAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public void showDatePickerDialog(View v, EditText txt_date) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year, month, dayOfMonth);

                if (txt_date.getId() == R.id.production_date) {

                    // Ngày sản xuất
                    String selectedDate = dateFormat.format(selectedCalendar.getTime());

                    // Kiểm tra ngày đã chọn có lớn hơn ngày hiện tại không
                    if (selectedCalendar.compareTo(Calendar.getInstance()) > 0) {
                        Toast.makeText(getApplicationContext(), "Không thể chọn ngày trong tương lai", Toast.LENGTH_SHORT).show();
                    } else {
                        // Kiểm tra nếu đã có ngày hết hạn
                        String expirationDateStr = expiration_date.getText().toString();
                        if (!expirationDateStr.isEmpty()) {
                            try {
                                Date expirationDate = dateFormat.parse(expirationDateStr);
                                Calendar expirationCalendar = Calendar.getInstance();
                                expirationCalendar.setTime(expirationDate);

                                if (selectedCalendar.compareTo(expirationCalendar) >= 0) {
                                    Toast.makeText(getApplicationContext(), "Ngày sản xuất phải nhỏ hơn ngày hết hạn", Toast.LENGTH_SHORT).show();
                                } else {
                                    txt_date.setText(selectedDate);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            txt_date.setText(selectedDate);
                        }
                    }
                } else if (txt_date.getId() == R.id.expiration_date) {
                    // Ngày hết hạn
                    String selectedDate = dateFormat.format(selectedCalendar.getTime());

                    // Kiểm tra nếu đã có ngày sản xuất
                    String productionDateStr = production_date.getText().toString();
                    if (!productionDateStr.isEmpty()) {
                        try {
                            Date productionDate = dateFormat.parse(productionDateStr);
                            Calendar productionCalendar = Calendar.getInstance();
                            productionCalendar.setTime(productionDate);

                            if (selectedCalendar.compareTo(productionCalendar) <= 0) {
                                Toast.makeText(getApplicationContext(), "Ngày hết hạn phải lớn hơn ngày sản xuất", Toast.LENGTH_SHORT).show();
                            } else {
                                txt_date.setText(selectedDate);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        txt_date.setText(selectedDate);
                    }
                }
            }
        }, year, month, dayOfMonth);

        if (txt_date.getId() == R.id.production_date) {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        }
        datePickerDialog.show();
    }

}