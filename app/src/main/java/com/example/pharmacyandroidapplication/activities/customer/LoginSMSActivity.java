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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

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
        sendOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOTPButton.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                phoneEditText = findViewById(R.id.phoneEditText);
                phoneNumber = String.valueOf(phoneEditText.getText());
                if (phoneEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LoginSMSActivity.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    sendOTPButton.setVisibility(View.VISIBLE);
                    return;
                }
//                send0tp(phoneNumber, false);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+84" + phoneEditText.getText().toString(),
                        60L,
                        TimeUnit.SECONDS,
                        LoginSMSActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                // Khong biet de lam gi
                                Intent intent = new Intent(getApplicationContext(), SendOTPActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText( LoginSMSActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Intent intent = new Intent(getApplicationContext(), SendOTPActivity.class);
                                intent.putExtra( "phone", phoneEditText.getText().toString());
                                intent.putExtra( "verificationID", verificationID);
                                startActivity(intent);
                            }

                        }
                );
                Intent intent = new Intent(getApplicationContext(), SendOTPActivity.class);
                intent.putExtra("phone", phoneEditText.getText().toString());
                startActivity(intent);
            }
        });
    }

    void send0tp(String phoneNumber, boolean isResend) {
//        setInProgress(true);
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                signIn(phoneAuthCredential);
                                Toast.makeText(LoginSMSActivity.this, "AAAAAAAAAA", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                AndroidUtil.showToast(getApplicationContext(), "OTP verification failed");
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                AndroidUtil.showToast(getApplicationContext(), "OTP sent successfully");
                            }
                        });
        if (isResend) {
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        } else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());

        }
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
    }

    void setInProgress(boolean inProgress) {
//        if(inProgress){
//            progressBar.setVisibility(View.VISIBLE);
//            btnNext.setVisibility(View.GONE);
//        }else{
//            progressBar.setVisibility(View.GONE);
//            btnNext.setVisibility(View.VISIBLE);
//
//        }
    }
}