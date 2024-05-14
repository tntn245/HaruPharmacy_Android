package com.example.pharmacyandroidapplication.activities.customer;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.LoginActivity;
import com.example.pharmacyandroidapplication.utils.AndroidUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class LoginSMSActivity extends AppCompatActivity {
    String phoneNumber;
    Long timeoutSeconds = 60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    Button sendOTPButton;
    EditText phoneEditText;
    ProgressBar progressBar;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sms);

        progressBar = findViewById(R.id.progressBar);
        phoneEditText = findViewById(R.id.phoneEditText);
        sendOTPButton = findViewById(R.id.sendOTPButton);

        progressBar.setVisibility(View.GONE);

        sendOTPButton.setOnClickListener((v)->{
            String phoneNumber = "+84" + phoneEditText.getText();
            AndroidUtil.showToast(getApplicationContext(),phoneNumber);
            Intent intent = new Intent(LoginSMSActivity.this, SendOTPActivity.class);
            intent.putExtra("phone", phoneNumber);
            startActivity(intent);
        });
    }
}