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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCategoryActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference categoryRef = database.getReference("category");
    private EditText add_txt_category_id, add_txt_category_name;
    private Spinner add_spn_category_status;
    private Button btn_save_add_category, btn_cancel_saved_add_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        add_txt_category_name = findViewById(R.id.add_txt_category_name);
        add_txt_category_id = findViewById(R.id.add_txt_category_id);
        add_spn_category_status = findViewById(R.id.add_spn_category_status);
        btn_save_add_category = findViewById(R.id.btn_save_add_category);
        btn_cancel_saved_add_category = findViewById(R.id.btn_cancel_saved_add_category);
        String[] arraySpinner = new String[]{"Chọn tình trạng kinh doanh", "Đang kinh doanh", "Ngừng kinh doanh"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        add_spn_category_status.setAdapter(adapter);

        btn_save_add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add_spn_category_status.getSelectedItem().toString().equals("Chọn tình trạng kinh doanh")) {
                    Toast.makeText(AddCategoryActivity.this, "Vui lòng chọn tình trạng kinh doanh", Toast.LENGTH_SHORT).show();
                } else if (add_txt_category_id.getText().toString().equals("") || add_txt_category_name.getText().toString().equals("")) {
                    Toast.makeText(AddCategoryActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        boolean flag = true;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (add_txt_category_id.getText().toString().equals(dataSnapshot.child("id").getValue().toString())) {
                                    flag = false;
                                }
                            }
                            if (flag == true) {
                                saveCategory();
                                Intent intent = new Intent(AddCategoryActivity.this, CategoryManagementActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else if (!flag){
                                Toast.makeText(AddCategoryActivity.this, "Mã sản phẩm này đã tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        btn_cancel_saved_add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCategoryActivity.this, CategoryManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveCategory() {
        String id, name;
        boolean flag_valid;
        id = add_txt_category_id.getText().toString();
        name = add_txt_category_name.getText().toString();
        if (add_spn_category_status.getSelectedItem().toString().equals("Đang kinh doanh")) {
            flag_valid = true;
        } else flag_valid = false;
        Category newCategory = new Category(id, name, flag_valid);
        DatabaseReference newCategoryRef = categoryRef.child(id);
        newCategoryRef.setValue(newCategory);
    }
}