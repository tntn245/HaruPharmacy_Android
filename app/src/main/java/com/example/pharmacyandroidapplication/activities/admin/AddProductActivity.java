package com.example.pharmacyandroidapplication.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.Unit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {
    private TextView add_txt_product_id;
    private ImageView add_img_product_img;
    private EditText add_txt_product_name, add_txt_product_price, add_txt_product_ingredient, add_txt_product_uses;
    private Spinner add_spn_product_type, add_spn_product_unit, add_spn_product_status;
    private Button btn_save_add_product, btn_cancel_add_product;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference productRef = database.getReference("product");
    private DatabaseReference categoryRef = database.getReference("category");
    private DatabaseReference unitRef = database.getReference("unit");
    private ArrayList typeList, unitList;
    private ArrayAdapter<String> adapter, unitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        add_txt_product_id = findViewById(R.id.add_txt_product_id);
        add_txt_product_name = findViewById(R.id.add_txt_product_name);
        add_img_product_img = findViewById(R.id.add_img_product_img);
        add_txt_product_price = findViewById(R.id.add_txt_product_price);
        add_txt_product_ingredient = findViewById(R.id.add_txt_product_ingredient);
        add_txt_product_uses = findViewById(R.id.add_txt_product_uses);
        add_spn_product_unit = findViewById(R.id.add_spn_product_unit);
        add_spn_product_status = findViewById(R.id.add_spn_product_status);
        add_spn_product_type = findViewById(R.id.add_spn_product_type);
        btn_save_add_product = findViewById(R.id.btn_save_add_product);
        btn_cancel_add_product = findViewById(R.id.btn_cancel_saved_add_product);

        typeList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        retrieveCategoryData("category_name");
        add_spn_product_type.setAdapter(adapter);

        ArrayList<String> statusList = new ArrayList<>();
        statusList.add("Đang kinh doanh");
        statusList.add("Ngừng kinh doanh");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        add_spn_product_status.setAdapter(statusAdapter);

        unitList = new ArrayList<>();
        unitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unitList);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        retrieveUnitData("unit_name");
        add_spn_product_unit.setAdapter(unitAdapter);
        btn_save_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewProduct();
                setDefaultView();
                Intent intent = new Intent(AddProductActivity.this, ProductManagementActivity.class);
                startActivity(intent);
            }
        });
    }

    public void saveNewProduct() {
        String id_cate, name, unit, ingredient, uses, img;
        int price, quantity;
        boolean flag_valid;
        id_cate = add_spn_product_type.getSelectedItem().toString();
        name = add_txt_product_name.getText().toString();
        img = "img_link";
        unit = add_spn_product_unit.getSelectedItem().toString();
        ingredient = add_txt_product_ingredient.getText().toString();
        uses = add_txt_product_uses.getText().toString();
        price = Integer.valueOf(add_txt_product_price.getText().toString());
        quantity = 0;
        flag_valid = (add_spn_product_status.getSelectedItem().toString() == "Đang kinh doanh");
        DatabaseReference newProductRef = productRef.push();
        String id = newProductRef.getKey().toString();
        Map<String, Object> units = new HashMap<>();
//        units.put(unit, new Unit(unit, price));
//        Product newProduct = new Product(id, id_cate, img, name, quantity, price, unit, uses, ingredient, flag_valid, false, units);
//        newProductRef.setValue(newProduct);
        // Tạo các đơn vị (units) cho sản phẩm

        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy dữ liệu từ snapshot và tạo đối tượng Product
                    String nameInFirebase = snapshot.child("name").getValue(String.class);
                    if (name.equals(nameInFirebase)) {
//                        Map<String, Object> units = new HashMap<>();
//                        units.put(unit, new Unit(unit, price));
//                        DatabaseReference unitRef = snapshot.getRef().child("units").getRef();
//                        unitRef.setValue(units);
//                        break;
                    } else {
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void setDefaultView() {
        add_txt_product_id.setText("");
        add_txt_product_name.setText("");
        add_img_product_img.setImageResource(R.mipmap.ic_launcher);
        add_txt_product_price.setText("");
        add_txt_product_ingredient.setText("");
        add_txt_product_uses.setText("");
        add_spn_product_unit.setSelection(0);
        add_spn_product_status.setSelection(0);
        add_spn_product_type.setSelection(0);
    }

    public void retrieveCategoryData(String attr) {
        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                typeList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    typeList.add(item.child(attr).getValue(String.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
            }
        });
    }

    public void retrieveUnitData(String attr) {
        unitRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                unitList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    unitList.add(item.child(attr).getValue(String.class));
                }
                unitAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
            }
        });
    }
}