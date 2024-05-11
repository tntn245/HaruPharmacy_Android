package com.example.pharmacyandroidapplication.activities.customer;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;

public class UserEditProfileActivity extends AppCompatActivity {
    private EditText edtTxt_edit_acc_birth_day, edtTxt_edit_acc_sex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        edtTxt_edit_acc_birth_day = findViewById(R.id.edtTxt_edit_acc_birth_day);
        edtTxt_edit_acc_sex = findViewById(R.id.edtTxt_edit_acc_sex);
        Button saveBtn = findViewById(R.id.btn_saved_edit_acc_info);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }
    public void saveProfile(){
        String sex= edtTxt_edit_acc_sex.getText().toString();
        String birth_day = edtTxt_edit_acc_birth_day.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("sex", sex);
        intent.putExtra("birthDay", birth_day);
        // Đặt resultCode và Intent và kết thúc hoạt động
        setResult(RESULT_OK, intent);
        finish();
    }

}
