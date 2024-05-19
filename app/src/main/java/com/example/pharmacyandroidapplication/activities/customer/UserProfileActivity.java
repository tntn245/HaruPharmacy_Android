package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.HomeProductAdapter;
import com.example.pharmacyandroidapplication.models.Account;
import com.example.pharmacyandroidapplication.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {
    private static final int EDIT_PROFILE_REQUEST_CODE = 1001;
    private TextView txt_acc_birth_day, txt_acc_sex, txt_username;
    ImageView user_img;
    String userID;
    Account user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userID = getIntent().getExtras().getString("userID");

        loadUserInformation();

        Button btnEditProfile = findViewById(R.id.btn_edit_acc_info);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, UserEditProfileActivity.class);
                startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE);
            }
        });

        ImageView ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
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
    private void loadUserInformation() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("accounts");
        usersRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Kiểm tra xem người dùng có tồn tại trong cơ sở dữ liệu hay không
                if (snapshot.exists()) {
                    String username = snapshot.child("username").getValue(String.class);
                    String img = snapshot.child("img").getValue(String.class);
                    String sex = snapshot.child("sex").getValue(String.class);
                    String birthday = snapshot.child("birth_day").getValue(String.class);

                    // Hiển thị thông tin người dùng lên giao diện
                    displayUserInfo(username, img, sex, birthday);
                } else {
                    // Người dùng không tồn tại trong cơ sở dữ liệu
                    Toast.makeText(UserProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi xảy ra trong quá trình truy vấn
                Toast.makeText(UserProfileActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayUserInfo(String username, String img, String sex, String birthday) {
        // Hiển thị thông tin người dùng lên giao diện
        // Ví dụ: gán giá trị cho TextViews
        txt_username = findViewById(R.id.txt_username);
        txt_acc_birth_day = findViewById(R.id.txt_acc_birth_day);
        txt_acc_sex = findViewById(R.id.txt_acc_sex);
        user_img = findViewById(R.id.userImg);

        txt_username.setText(username);
        txt_acc_sex.setText(sex);
        txt_acc_birth_day.setText(birthday);
        // Tải và hiển thị ảnh từ URL lên ImageView sử dụng Glide
        Glide.with(this)
                .load(img)
                .into(user_img);
    }
}
