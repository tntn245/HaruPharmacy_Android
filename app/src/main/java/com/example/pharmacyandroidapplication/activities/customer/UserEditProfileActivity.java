package com.example.pharmacyandroidapplication.activities.customer;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.admin.AdminHomepageActivity;
import com.example.pharmacyandroidapplication.models.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserEditProfileActivity extends AppCompatActivity {
    private EditText edtTxt_edit_acc_birth_day, edtTxt_edit_acc_sex;
private FirebaseAuth mAuth;
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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            String userID = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("accounts");
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String userType = dataSnapshot.child("role").getValue(String.class);
                        String userId = dataSnapshot.child("id").getValue(String.class);

                        Account account = new Account(userId, "unknown user name",userType, sex, birth_day);
                        userRef.child(userID).setValue(account)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Ghi dữ liệu thành công
                                            Log.d("TAG", "Data added successfully");
                                        } else {
                                            // Ghi dữ liệu thất bại
                                            Log.e("TAG", "Error adding data: " + task.getException().getMessage());
                                        }
                                    }
                                });
                    } else {
                        // Không có dữ liệu về người dùng
                        Log.w("firebase", "khong co du lieu nguoi dung trong database");
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }
        Intent intent = new Intent();
        intent.putExtra("sex", sex);
        intent.putExtra("birthDay", birth_day);
        // Đặt resultCode và Intent và kết thúc hoạt động
        setResult(RESULT_OK, intent);
        finish();
    }

}
