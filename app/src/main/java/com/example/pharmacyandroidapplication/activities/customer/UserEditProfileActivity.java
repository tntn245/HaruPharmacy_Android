package com.example.pharmacyandroidapplication.activities.customer;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
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
    private EditText edt_birth_day;
    RadioGroup radioGroup;
    String sexOption;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        edt_birth_day = findViewById(R.id.edtTxt_edit_acc_birth_day);
        radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Lấy thông tin từ RadioButton đã chọn
                RadioButton radioButton = findViewById(checkedId);
                sexOption = radioButton.getText().toString();

                // Sử dụng thông tin đã chọn
                Toast.makeText(getApplicationContext(), "Selected option: " + sexOption, Toast.LENGTH_SHORT).show();
            }
        });

        Button saveBtn = findViewById(R.id.btn_saved_edit_acc_info);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
      
        ImageView ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserEditProfileActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void saveProfile(){
        String birth_day = edt_birth_day.getText().toString();
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
                        String userImg = dataSnapshot.child("img").getValue(String.class);

                        Account account = new Account(userId,userImg,userType, "unknown user name", sexOption, birth_day);
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
        intent.putExtra("sex", sexOption);
        intent.putExtra("birthDay", birth_day);
        // Đặt resultCode và Intent và kết thúc hoạt động
        setResult(RESULT_OK, intent);
        finish();
    }

}

