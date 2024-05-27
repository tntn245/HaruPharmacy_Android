package com.example.pharmacyandroidapplication.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.LoginActivity;
import com.example.pharmacyandroidapplication.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditProductActivity extends AppCompatActivity {
    private EditText txt_product_name, txt_category_name, txt_product_ingredient, txt_product_uses, txt_product_status;
    private LinearLayout checkboxContainer;
    private ArrayAdapter<Product> productNameAdapter;
    private ArrayList<Product> productList;
    String productID;
    String categoryID;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Map<String, CheckBox> checkBoxMap = new HashMap<>();
    Map<String, EditText> editTextPriceMap = new HashMap<>();
    Map<String, EditText> editTextSellPriceMap = new HashMap<>();
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        productID = getIntent().getExtras().getString("productID");
        Toast.makeText(this, productID, Toast.LENGTH_SHORT).show();


        txt_product_name = findViewById(R.id.spinner_product_name);
        txt_category_name = findViewById(R.id.txt_category_name);
        txt_product_ingredient = findViewById(R.id.txt_product_ingredient);
        txt_product_uses = findViewById(R.id.txt_product_uses);
        txt_product_status = findViewById(R.id.txt_product_status);
        checkboxContainer = findViewById(R.id.checkboxContainer);

        retrieveUnitData("unit_name");

        productList = new ArrayList<>();
        loadProductFromFirebase();
    }

    // Phương thức để đặt lại trạng thái của tất cả CheckBox và EditText
    private void resetCheckBoxStates() {
        int childCount = checkboxContainer.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View view = checkboxContainer.getChildAt(i);

            // Kiểm tra nếu view là LinearLayout chứa CheckBox và EditText
            if (view instanceof LinearLayout) {
                LinearLayout unitLayout = (LinearLayout) view;
                int layoutChildCount = unitLayout.getChildCount();

                for (int j = 0; j < layoutChildCount; j++) {
                    View childView = unitLayout.getChildAt(j);

                    // Đặt lại trạng thái của CheckBox
                    if (childView instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) childView;
                        checkBox.setChecked(false);
                    }

                    // Đặt lại trạng thái của EditText
                    if (childView instanceof EditText) {
                        EditText editText = (EditText) childView;
                        editText.setText(""); // Xóa văn bản
                        editText.setVisibility(View.GONE); // Ẩn EditText
                    }
                }
            }
        }
    }

    private void checkCheckBoxStates(String unitNameToCheck, int unitPrice, int sellPrice) {
        int childCount = checkboxContainer.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View view = checkboxContainer.getChildAt(i);

            // Kiểm tra nếu view là LinearLayout chứa CheckBox và EditText
            if (view instanceof LinearLayout) {
                LinearLayout unitLayout = (LinearLayout) view;
                int layoutChildCount = unitLayout.getChildCount();

                for (int j = 0; j < layoutChildCount; j++) {
                    View childView = unitLayout.getChildAt(j);

                    // Đặt lại trạng thái của CheckBox
                    if (childView instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) childView;
                        String unitName = checkBox.getText().toString();

                        if (unitName.equals(unitNameToCheck)) {
                            checkBox.setChecked(true);
                            checkBox.setEnabled(false);
                        }
                    }
                    // Kiểm tra và lấy giá trị của EditText giá
                    if (childView instanceof EditText && j == 1) {
                        EditText editTextPrice = (EditText) childView;
                        editTextPrice.setText(String.valueOf(unitPrice));
                    }

                    // Kiểm tra và lấy giá trị của EditText giá bán
                    if (childView instanceof EditText && j == 2) {
                        EditText editTextSellPrice = (EditText) childView;
                        editTextSellPrice.setText(String.valueOf(sellPrice));
                    }
                }
            }
        }
    }

    private void loadProductFromFirebase() {
        DatabaseReference productsRef = database.getReference("product");
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy dữ liệu từ snapshot và tạo đối tượng Product
                    String product_id = snapshot.child("id").getValue(String.class);
                    if (product_id.equals(productID)) {
                        String productName = snapshot.child("name").getValue(String.class);
                        int productPrice = snapshot.child("price").getValue(Integer.class);
                        String productImg = snapshot.child("img").getValue(String.class);
                        String id_category = snapshot.child("id_category").getValue(String.class);
                        String unit = snapshot.child("unit").getValue(String.class);
                        int inventory_quantity = snapshot.child("inventory_quantity").getValue(Integer.class);
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

                        product = new Product(productID, id_category, productImg, productName, productPrice,inventory_quantity, unit, uses, ingredient, flagValid, prescription, unitArr);

                        txt_product_name.setText(product.getName());
                        txt_category_name.setText(product.getId_category());
                        txt_product_ingredient.setText(product.getIngredient());
                        txt_product_uses.setText(product.getUses());
                        boolean productStatus = product.isFlag_valid();
                        txt_product_status.setText(productStatus ? "Đang kinh doanh" : "Ngừng kinh doanh");

                        resetCheckBoxStates();
                        for (String unitName : unitArr.keySet()) {
                            Map<String, Object> unitData = (Map<String, Object>) unitArr.get(unitName);

                            int price = (int) unitData.get("price");
                            int sellPrice = (int) unitData.get("sell_price");
                            int quantity = (int) unitData.get("quantity");

                            // Checked checkbox
                            checkCheckBoxStates(unitName, price, sellPrice);
                        }

                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void retrieveUnitData(String attr) {
        DatabaseReference unitRef = database.getReference("unit");
        unitRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                checkboxContainer.removeAllViews();

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String unitID = item.getKey();
                    String unitName = item.child(attr).getValue(String.class);

                    // Tạo một LinearLayout để chứa CheckBox và EditText
                    LinearLayout unitLayout = new LinearLayout(EditProductActivity.this);
                    unitLayout.setOrientation(LinearLayout.VERTICAL);

                    // Tạo một CheckBox mới và thiết lập giá trị của nó
                    CheckBox checkBox = new CheckBox(EditProductActivity.this);
                    checkBox.setText(unitName);
                    checkBox.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
                    checkBox.setTag(unitID); // Lưu trữ unitId vào tag của CheckBox

                    // Tạo một EditText mới để nhập giá và thiết lập ẩn ban đầu
                    EditText editTextPrice = new EditText(EditProductActivity.this);
                    editTextPrice.setHint("Enter price");
                    editTextPrice.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editTextPrice.setVisibility(EditText.GONE);
                    editTextPrice.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));

                    // Tạo một EditText mới để hiện giá bán = % giá nhập
                    EditText editTextSellPrice = new EditText(EditProductActivity.this);
                    editTextSellPrice.setEnabled(false);
                    editTextSellPrice.setVisibility(EditText.GONE);
                    editTextSellPrice.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));

                    // Thêm CheckBox và EditText vào LinearLayout
                    unitLayout.addView(checkBox);
                    unitLayout.addView(editTextPrice);
                    unitLayout.addView(editTextSellPrice);

                    // Thêm LinearLayout vào checkboxContainer
                    checkboxContainer.addView(unitLayout);

                    // Lưu trữ CheckBox và EditText vào Map
                    checkBoxMap.put(unitName, checkBox);
                    editTextPriceMap.put(unitName, editTextPrice);
                    editTextSellPriceMap.put(unitName, editTextSellPrice);

                    // Thiết lập sự kiện kiểm tra trạng thái cho CheckBox
                    checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            editTextPrice.setVisibility(EditText.VISIBLE);
                            editTextSellPrice.setVisibility(EditText.VISIBLE);
                            Toast.makeText(EditProductActivity.this, unitName + " checked", Toast.LENGTH_SHORT).show();
                        } else {
                            editTextPrice.setVisibility(EditText.GONE);
                            editTextSellPrice.setVisibility(EditText.GONE);
                            Toast.makeText(EditProductActivity.this, unitName + " unchecked", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Lắng nghe thay đổi trong editTextPrice để cập nhật editText150Percent
                    editTextPrice.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            try {
                                double price = Double.parseDouble(s.toString());
                                double price150Percent = price * 1.5;
                                editTextSellPrice.setText(String.valueOf(price150Percent));
                            } catch (NumberFormatException e) {
                                editTextSellPrice.setText("");
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
            }
        });
    }
}