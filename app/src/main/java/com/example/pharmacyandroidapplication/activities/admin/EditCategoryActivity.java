package com.example.pharmacyandroidapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.Category;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditCategoryActivity extends AppCompatActivity {
    private TextView edit_category_txt_id;
    private EditText edit_category_txt_name;
    private Spinner edit_category_spn_status;
    private Button btn_save_edit_category, btn_cancel_edit_category;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference categoryRef = database.getReference("category");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        edit_category_txt_id = findViewById(R.id.edit_category_txt_id);
        edit_category_txt_name = findViewById(R.id.edit_category_txt_name);
        edit_category_spn_status = findViewById(R.id.edit_category_spn_status);
        btn_cancel_edit_category = findViewById(R.id.btn_cancel_edit_category);
        btn_save_edit_category = findViewById(R.id.btn_save_edit_category);
        String id = getIntent().getExtras().getString("selectedCategoryId");
        String name = getIntent().getExtras().getString("selectedCategoryName");
        boolean flag_valid = getIntent().getExtras().getBoolean("selectedCategoryStatus");
        edit_category_txt_id.setText(id);
        edit_category_txt_name.setText(name);
        String[] arraySpinner = new String[]{"Đang kinh doanh", "Ngừng kinh doanh"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_category_spn_status.setAdapter(adapter);
        if (flag_valid) edit_category_spn_status.setSelection(0);
        else edit_category_spn_status.setSelection(1);
        //Button Save
        btn_save_edit_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editedName = edit_category_txt_name.getText().toString();
                if (!editedName.equals("")) {
                    boolean editedStatus = edit_category_spn_status.getSelectedItem().toString().equals("Đang kinh doanh");

                    if (editedName.equals(edit_category_txt_name.getText().toString()) && (editedStatus == getIntent().getExtras().getBoolean("selectedCategoryStatus")))
                        Toast.makeText(EditCategoryActivity.this, "Thông tin không có sự thay đổi", Toast.LENGTH_SHORT).show();
                    else {
                        Category editedCategory = new Category(id, editedName, editedStatus);
                        DatabaseReference categoryByIdRef = categoryRef.child(id);
                        categoryByIdRef.setValue(editedCategory);
                        Intent intent =  new Intent(EditCategoryActivity.this, CategoryManagementActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else
                    Toast.makeText(EditCategoryActivity.this, "Vui lòng nhập tên loại sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
        //Button Cancel
        btn_cancel_edit_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(EditCategoryActivity.this, CategoryManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}