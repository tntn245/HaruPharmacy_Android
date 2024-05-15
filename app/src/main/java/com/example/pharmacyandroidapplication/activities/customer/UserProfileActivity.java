package com.example.pharmacyandroidapplication.activities.customer;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    String userID;
    Account user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userID = getIntent().getExtras().getString("userID");

        loadUserInformation();
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

                    // Hiển thị thông tin người dùng lên giao diện
                    displayUserInfo(username, img);
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

    private void displayUserInfo(String username, String img) {
        // Hiển thị thông tin người dùng lên giao diện
        // Ví dụ: gán giá trị cho TextViews
        TextView userName = findViewById(R.id.userName);
        ImageView userImg = findViewById(R.id.userImg);

        userName.setText(username);
        // Tải và hiển thị ảnh từ URL lên ImageView sử dụng Glide
        Glide.with(this)
                .load(img)
                .into(userImg);
    }
}
