package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;

public class UserProfileActivity extends AppCompatActivity {
    private static final int EDIT_PROFILE_REQUEST_CODE = 1001;
    private TextView txt_acc_birth_day, txt_acc_sex;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Button btnEditProfile = findViewById(R.id.btn_edit_acc_info);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, UserEditProfileActivity.class);
                startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                txt_acc_birth_day = findViewById(R.id.txt_acc_birth_day);
                txt_acc_sex = findViewById(R.id.txt_acc_sex);
                String sex = data.getStringExtra("sex");
                String birthDay = data.getStringExtra("birthDay");
                txt_acc_sex.setText(sex);
                txt_acc_birth_day.setText(birthDay);
            }
        }
    }
}
