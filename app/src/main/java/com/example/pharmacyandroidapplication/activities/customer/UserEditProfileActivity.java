package com.example.pharmacyandroidapplication.activities.customer;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserEditProfileActivity extends AppCompatActivity {
    private EditText edt_txt_birth_day_account,edt_txt_username_account ;
    private RadioGroup edt_radioGroup_sex_account;

    String sexOption;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        edt_txt_birth_day_account = findViewById(R.id.edt_txt_birth_day_account);
        edt_radioGroup_sex_account = findViewById(R.id.edt_radioGroup_sex_account);
        Button saveBtn = findViewById(R.id.btn_saved_edit_acc_info);
        ImageView ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserEditProfileActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        edt_radioGroup_sex_account.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Lấy thông tin từ RadioButton đã chọn
                RadioButton radioButton = findViewById(checkedId);
                sexOption = radioButton.getText().toString();

                // Sử dụng thông tin đã chọn
                Toast.makeText(getApplicationContext(), "Selected option: " + sexOption, Toast.LENGTH_SHORT).show();
            }
        });

        edt_txt_birth_day_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    public void saveProfile(){
        String birth_day = edt_txt_birth_day_account.getText().toString();
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
    public void showDatePickerDialog(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Khi người dùng chọn ngày, cập nhật TextView với ngày đã chọn
                Calendar selectedCalendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                selectedCalendar.set(year, month, dayOfMonth);
                String selectedDate = dateFormat.format(selectedCalendar.getTime());
                edt_txt_birth_day_account.setText(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
}

