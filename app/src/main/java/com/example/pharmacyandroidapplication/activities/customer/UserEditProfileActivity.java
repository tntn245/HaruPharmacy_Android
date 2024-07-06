package com.example.pharmacyandroidapplication.activities.customer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pharmacyandroidapplication.MainActivity;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.ChatActivity;
import com.example.pharmacyandroidapplication.models.Account;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class UserEditProfileActivity extends AppCompatActivity {
    EditText edt_txt_birth_day_account, edt_txt_username_account;
    TextView edt_txt_change_img_account;
    Uri image;
    String imageStr;
    ImageView edt_img_account;
    Boolean flag_select_birthday = false;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    StorageReference storageReference;
    String sexOption;
    String userID;
    Button saveBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        storageReference = FirebaseStorage.getInstance().getReference();

        loadUserInf();

        edt_txt_change_img_account = findViewById(R.id.edt_txt_change_img_account);
        edt_img_account = findViewById(R.id.edt_img_account);
        edt_txt_username_account = findViewById(R.id.edt_txt_username_account);
        edt_txt_birth_day_account = findViewById(R.id.edt_txt_birth_day_account);
        radioGroupGender = findViewById(R.id.edt_radioGroup_sex_account);
        radioButtonMale = findViewById(R.id.maleRadioButton);
        radioButtonFemale = findViewById(R.id.femaleRadioButton);

        saveBtn = findViewById(R.id.btn_saved_edit_acc_info);
//        ImageView ic_back = findViewById(R.id.ic_back);
//
//        ic_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserEditProfileActivity.this, UserProfileActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Lấy thông tin từ RadioButton đã chọn
                RadioButton radioButton = findViewById(checkedId);
                sexOption = radioButton.getText().toString();
            }
        });

        edt_txt_birth_day_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
                flag_select_birthday = true;
            }
        });
        edt_txt_change_img_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    private void loadUserInf() {
        userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("accounts").child(userID);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userName = dataSnapshot.child("username").getValue(String.class);
                    String userImg = dataSnapshot.child("img").getValue(String.class);
                    String userSex = dataSnapshot.child("sex").getValue(String.class);
                    String userBirthDay = dataSnapshot.child("birth_day").getValue(String.class);

                    Glide.with(UserEditProfileActivity.this)
                            .load(userImg)
                            .into(edt_img_account);
                    edt_txt_username_account.setText(userName);
                    edt_txt_birth_day_account.setText(userBirthDay);

                    // Kiểm tra giá trị của biến userSex và tick vào RadioButton tương ứng
                    if ("Nam".equals(userSex)) {
                        radioButtonMale.setChecked(true);
                    } else if ("Nữ".equals(userSex)) {
                        radioButtonFemale.setChecked(true);
                    }

                    image = Uri.parse(userImg);
                } else {
                    // Không có dữ liệu về người dùng
                    Toast.makeText(UserEditProfileActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
                Toast.makeText(UserEditProfileActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    image = result.getData().getData();
                    Glide.with(getApplicationContext()).load(image).into(edt_img_account);
                    uploadImage(image);
                }
            } else {
                Toast.makeText(UserEditProfileActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
        }
    });

    private void uploadImage(Uri file) {
        StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
        ref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserEditProfileActivity.this, "Failed!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        // Đường dẫn của ảnh
                        image = downloadUri;
                        saveBtn.setEnabled(true);
                        Toast.makeText(UserEditProfileActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void saveProfile() {
            HashMap<String, Object> userInf = new HashMap<>();
            userInf.put("id", userID);
            userInf.put("username", edt_txt_username_account.getText().toString());
            userInf.put("img", image.toString());
            userInf.put("role", "customer");
            userInf.put("sex", sexOption);
            userInf.put("birth_day", edt_txt_birth_day_account.getText().toString());

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("accounts");
            userRef.child(userID).setValue(userInf, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                    Intent intent = new Intent(UserEditProfileActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                    finish();
                    } else {
                        Toast.makeText(UserEditProfileActivity.this, "Lỗi khi thêm dữ liệu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
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

