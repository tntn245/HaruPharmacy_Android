package com.example.pharmacyandroidapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.activities.LoginActivity;
import com.example.pharmacyandroidapplication.activities.admin.AdminHomepageActivity;
import com.example.pharmacyandroidapplication.activities.customer.CustomerHomepageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
//    FirebaseAuth auth;
//    FirebaseUser user;
//    DatabaseReference userRef;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super. onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
//
//        if (user == null) {
//            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//
//        }
//    }



    EditText editTextEmail, editTextPassword;
    Button buttonReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super. onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Button btnUser = findViewById(R.id.btnCustomer);
//        btnUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, CustomerHomepageActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button btnAdmin = findViewById(R.id.btnAdmin);
//        btnAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AdminHomepageActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button btnLogin = findViewById(R.id.btnLogin);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}