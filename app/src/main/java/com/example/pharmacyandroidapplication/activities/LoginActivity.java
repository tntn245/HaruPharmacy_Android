package com.example.pharmacyandroidapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.admin.AdminHomepageActivity;
import com.example.pharmacyandroidapplication.activities.customer.CustomerHomepageActivity;
import com.example.pharmacyandroidapplication.activities.customer.ForgotPassActivity;
import com.example.pharmacyandroidapplication.activities.customer.LoginSMSActivity;
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

public class LoginActivity extends AppCompatActivity {
    TextView textViewRegister;
    TextView textViewForgotPass;
    EditText editTextName, editTextPassword;
    Button buttonLogin;
    Button buttonLoginSMS;
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
        buttonLoginSMS = findViewById(R.id.loginSMSButton);
        progressBar = findViewById(R.id.progressBar);

        buttonLoginSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginSMSActivity.class);
                startActivity(intent);
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        textViewForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(!validateUsername() | !validatePassword()){
//
//                } else {
//                    checkUser();
//                }

                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextName.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email) ){
                    Toast.makeText(LoginActivity.this,"Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password) ) {
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
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
                                                    Intent intent = new Intent(LoginActivity.this, AdminHomepageActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else if ("customer".equals(userType)) {
                                                    Intent intent = new Intent(LoginActivity.this, CustomerHomepageActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    // Trường hợp khác, có thể xử lý theo ý của bạn
                                                    Toast.makeText(LoginActivity.this, "Unknown user type", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                // Không có dữ liệu về người dùng
                                                Toast.makeText(LoginActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            // Xử lý lỗi nếu cần
                                            Toast.makeText(LoginActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
//                                    Toast.makeText(LoginActivity.this, "Login successful.",
//                                            Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(getApplicationContext(), CustomerHomepageActivity.class);
//                                    startActivity(intent);
//                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
//
//    public Boolean validateUsername() {
//        String val = editTextName.getText().toString();
//        if (val.isEmpty()) {
//            editTextName.setError("Username cannot be empty");
//            return false;
//        } else {
//            editTextName.setError(null);
//            return true;
//        }
//    }
//    public Boolean validatePassword(){
//        String val = editTextPassword.getText().toString();
//        if (val.isEmpty()){
//            editTextPassword.setError("Password cannot be empty");
//            return false;
//        } else {
//            editTextPassword.setError(null);
//            return true;
//        }
//    }
//    private void checkUser(){
//        String userUsername = editTextName.getText().toString().trim();
//        String userPassword = editTextPassword.getText().toString().trim();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("accounts");
//        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);
//
//        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    editTextName.setError(null);
//                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
//
//                    assert passwordFromDB != null;
//                    if (!passwordFromDB.equals(userPassword)) { //Có khi lỗi
//                        editTextName.setError(null);
//                        Intent intent = new Intent(LoginActivity.this, CustomerHomepageActivity.class);
//                        startActivity(intent);
//                    } else {
//                        editTextPassword.setError("Invalid Credentials");
//                        editTextPassword.requestFocus();
//                    }
//                } else {
//                    editTextName.setError("User does not exist");
//                    editTextName.requestFocus();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}