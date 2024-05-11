package com.example.pharmacyandroidapplication.activities.customer;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;

public class UserEditProfileActivity extends AppCompatActivity {
    private TextView txt_acc_birth_day, txt_acc_sex;
    private EditText edtTxt_edit_acc_birth_day, edtTxt_edit_acc_sex;
    Button saveBtn = findViewById(R.id.btn_edit_acc_info);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        txt_acc_birth_day = findViewById(R.id.txt_acc_birth_day);

    }

}
