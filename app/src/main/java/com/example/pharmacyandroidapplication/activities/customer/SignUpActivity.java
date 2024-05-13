package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.LoginActivity;
import com.example.pharmacyandroidapplication.models.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextName, editTextPassword;
    TextView textViewLogin;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference reference;


//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        textViewLogin = findViewById(R.id.textViewLogin);
        editTextName = findViewById(R.id.usernameEditText);
        editTextPassword = findViewById(R.id.passwordEditText);
        buttonReg = findViewById(R.id.SignUpButton);
        progressBar = findViewById(R.id.progressBar);


        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(editTextName.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email) ){
                    Toast.makeText(SignUpActivity.this,"Enter email", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    buttonReg.setVisibility(View.VISIBLE);
                    return;
                }
                if (TextUtils.isEmpty(password) ) {
                    Toast.makeText(SignUpActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    buttonReg.setVisibility(View.VISIBLE);
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                buttonReg.setVisibility(View.GONE);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

                                    database = FirebaseDatabase.getInstance();
                                    reference = database.getReference("accounts");

                                    Account acc = new Account(userID);
                                    reference.child(userID).setValue(acc);

                                    Toast.makeText(SignUpActivity.this, "Sign up successfully.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
