package com.example.pharmacyandroidapplication;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.activities.admin.AdminHomepageActivity;
import com.example.pharmacyandroidapplication.activities.customer.CustomerHomepageActivity;
import com.example.pharmacyandroidapplication.activities.customer.ForgotPassActivity;
import com.example.pharmacyandroidapplication.activities.customer.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    TextView textViewRegister;
    TextView textViewForgotPass;
    EditText editTextName, editTextPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userID = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("account").child(userID);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String userType = dataSnapshot.child("role").getValue(String.class);
                        if ("admin".equals(userType)) {
                            Intent intent = new Intent(getApplicationContext(), AdminHomepageActivity.class);
                            startActivity(intent);
                            finish();
                        } else if ("customer".equals(userType)) {
                            Intent intent = new Intent(getApplicationContext(), CustomerHomepageActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Trường hợp khác, có thể xử lý theo ý của bạn
                        }
                    } else {
                        // Không có dữ liệu về người dùng
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý lỗi nếu cần
                }
            });
        } else {
            // Nếu không có người dùng đăng nhập, kiểm tra xem có redirect từ SignUpActivity không
            // Nếu có, chuyển đến trang Login
            Intent intent = getIntent();
            if (intent != null && intent.getBooleanExtra("redirectFromSignUp", false)) {
                // Redirect từ SignUpActivity
                // Hiển thị trang đăng nhập
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        textViewRegister = findViewById(R.id.textViewRegister);
        textViewForgotPass = findViewById(R.id.textViewForgotPass);
        editTextName = findViewById(R.id.usernameEditText);
        editTextPassword = findViewById(R.id.passwordEditText);
        buttonLogin = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
      
        textViewForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });
      
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextName.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("accounts").child(userID);
                                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                String userType = dataSnapshot.child("role").getValue(String.class);
                                                // Kiểm tra và điều hướng người dùng tới trang tương ứng
                                                if ("admin".equals(userType)) {
                                                    Intent intent = new Intent(MainActivity.this, AdminHomepageActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else if ("customer".equals(userType)) {
                                                    Intent intent = new Intent(MainActivity.this, CustomerHomepageActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    // Trường hợp khác, có thể xử lý theo ý của bạn
                                                    Toast.makeText(MainActivity.this, "Unknown user type", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                // Không có dữ liệu về người dùng
                                                Toast.makeText(MainActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            // Xử lý lỗi nếu cần
                                            Toast.makeText(MainActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}