package com.example.pharmacyandroidapplication.activities.admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.customer.UserEditProfileActivity;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.Unit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddNewProductActivity extends AppCompatActivity {
    private ImageButton add_img_product_img;
    private ImageView img_product;
    private EditText add_txt_product_name, add_txt_product_ingredient, add_txt_product_uses;
    private Spinner add_spn_product_type, add_spn_product_status;
    private LinearLayout checkboxContainer;
    private Button btn_save_add_product, btn_cancel_add_product;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference productRef = database.getReference("product");
    private DatabaseReference categoryRef = database.getReference("category");
    private DatabaseReference unitRef = database.getReference("unit");
    private ArrayList typeList;
    private ArrayAdapter<String> adapter;
    String categoryID;
    boolean flag_exist;
    StorageReference storageReference;
    Uri image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        storageReference = FirebaseStorage.getInstance().getReference();
        add_txt_product_name = findViewById(R.id.add_txt_product_name);
        add_img_product_img = findViewById(R.id.add_img_product_img);
        add_txt_product_ingredient = findViewById(R.id.add_txt_product_ingredient);
        add_txt_product_uses = findViewById(R.id.add_txt_product_uses);
        checkboxContainer = findViewById(R.id.checkboxContainer);
        add_spn_product_status = findViewById(R.id.add_spn_product_status);
        add_spn_product_type = findViewById(R.id.add_spn_product_type);
        btn_save_add_product = findViewById(R.id.btn_save_add_product);
        btn_cancel_add_product = findViewById(R.id.btn_cancel_saved_add_product);
        img_product = findViewById(R.id.img);

        typeList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        retrieveCategoryData("name");
        add_spn_product_type.setAdapter(adapter);

        ArrayList<String> statusList = new ArrayList<>();
        statusList.add("Đang kinh doanh");
        statusList.add("Ngừng kinh doanh");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        add_spn_product_status.setAdapter(statusAdapter);

        retrieveUnitData("unit_name");
        btn_save_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewProduct();
//                setDefaultView();
            }
        });
        add_img_product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });
    }

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    image = result.getData().getData();
                    Glide.with(getApplicationContext()).load(image).into(img_product);
                    uploadImage(image);
                }
            } else {
                Toast.makeText(AddNewProductActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddNewProductActivity.this, "Failed!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        // Đường dẫn của ảnh
                        image = downloadUri;
                        Toast.makeText(AddNewProductActivity.this, "Tải ảnh thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void saveNewProduct() {
        if(image==null){
            Toast.makeText(AddNewProductActivity.this, "Vui lòng chọn hình ảnh sản phẩm", Toast.LENGTH_SHORT).show();
        }
        else {
            String cate_name = add_spn_product_type.getSelectedItem().toString();
            String cate_id = getCategoryID(cate_name);
            String name = add_txt_product_name.getText().toString();
            String img = image.toString();
            String ingredient = add_txt_product_ingredient.getText().toString();
            String uses = add_txt_product_uses.getText().toString();
            boolean flagValid = (add_spn_product_status.getSelectedItem().toString() == "Đang kinh doanh");
            boolean isAnyCheckboxChecked = false;
            for (int i = 0; i < checkboxContainer.getChildCount(); i++) {
                LinearLayout unitLayout = (LinearLayout) checkboxContainer.getChildAt(i);
                CheckBox checkBox = (CheckBox) unitLayout.getChildAt(0);
                if (checkBox.isChecked()) {
                    isAnyCheckboxChecked = true;
                    break;
                }
            }
            if (name.isEmpty() || img.isEmpty() ||
                    ingredient.isEmpty() || uses.isEmpty() ||
                    !isAnyCheckboxChecked) {
                Toast.makeText(AddNewProductActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                productRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String nameInFirebase = snapshot.child("name").getValue(String.class);
                            if (name.equals(nameInFirebase)) {
//                            Toast.makeText(AddNewProductActivity.this, "Sản phẩm đã tồn tại!", Toast.LENGTH_SHORT).show();
                                flag_exist = true;
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if (!flag_exist) {
                    // Tạo một product ID duy nhất
                    String productID = UUID.randomUUID().toString();
                    // Tạo một map để chứa các đơn vị và giá cả
                    Map<String, Object> unitArr = new HashMap<>();
                    int firstSellPrice = 0;
                    int count = 0;

                    // Duyệt qua tất cả các LinearLayout trong checkboxContainer
                    for (int i = 0; i < checkboxContainer.getChildCount(); i++) {
                        LinearLayout unitLayout = (LinearLayout) checkboxContainer.getChildAt(i);
                        CheckBox checkBox = (CheckBox) unitLayout.getChildAt(0);
                        EditText editTextUnitPrice = (EditText) unitLayout.getChildAt(1);
                        EditText editTextPercent = (EditText) unitLayout.getChildAt(2);
                        EditText editTextSellPrice = (EditText) unitLayout.getChildAt(3);

                        if (checkBox.isChecked()) {
                            count++;
                            // Thêm đơn vị được checked vào map
                            String unitName = checkBox.getText().toString();
                            String unitPrice = editTextUnitPrice.getText().toString();
                            String unitPercent = editTextPercent.getText().toString();
                            String sellPrice = editTextSellPrice.getText().toString();

                            if (unitPrice.isEmpty() || unitPercent.isEmpty()) {
                                Toast.makeText(AddNewProductActivity.this, "Vui lòng nhập giá và lợi nhuận sản phẩm", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Tạo một map để chứa giá và số lượng của đơn vị
                            Map<String, Object> unitData = new HashMap<>();
                            unitData.put("price", Integer.parseInt(unitPrice));
                            unitData.put("percent", Integer.parseInt(unitPercent));

                            // Thêm đơn vị vào unitArr
                            unitArr.put(unitName, unitData);

                            if (count == 1) {
                                firstSellPrice = Integer.parseInt(sellPrice);
                            }
                        }
                    }

                    // Tạo đối tượng Product
                    Product product = new Product(productID, cate_id, img, name, firstSellPrice, 0, "", uses, ingredient, flagValid, true, unitArr);

                    // Thêm product vào Firebase
                    productRef.child(productID).setValue(product).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddNewProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddNewProductActivity.this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                Intent intent = new Intent(AddNewProductActivity.this, ProductManagementActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private String getCategoryID(String cateName) {
        // Tạo một DatabaseReference để tham chiếu đến node chứa dữ liệu sản phẩm trong Firebase
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference("product");
        // Thêm một ChildEventListener để lắng nghe sự thay đổi dữ liệu
        productsRef.orderByChild("name").equalTo(cateName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                categoryID = dataSnapshot.getKey();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // Xử lý sự thay đổi dữ liệu nếu cần
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // Xử lý sự xóa dữ liệu nếu cần
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // Xử lý sự di chuyển dữ liệu nếu cần
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
        return categoryID;
    }

    public void retrieveCategoryData(String attr) {
        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                typeList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    typeList.add(item.child(attr).getValue(String.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
            }
        });
    }

    public void retrieveUnitData(String attr) {
        unitRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                checkboxContainer.removeAllViews();

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String unitID = item.getKey();
                    String unitName = item.child(attr).getValue(String.class);

                    // Tạo một LinearLayout để chứa CheckBox và EditText
                    LinearLayout unitLayout = new LinearLayout(AddNewProductActivity.this);
                    unitLayout.setOrientation(LinearLayout.VERTICAL);

                    // Tạo một CheckBox mới và thiết lập giá trị của nó
                    CheckBox checkBox = new CheckBox(AddNewProductActivity.this);
                    checkBox.setText(unitName);
                    checkBox.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));

                    // Tạo một EditText mới để nhập giá và thiết lập ẩn ban đầu
                    EditText editTextPrice = new EditText(AddNewProductActivity.this);
                    editTextPrice.setHint("Nhập đơn giá nhập kho");
                    editTextPrice.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editTextPrice.setVisibility(EditText.GONE);
                    editTextPrice.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));

                    // Tạo một EditText mới để nhập % và thiết lập ẩn ban đầu
                    EditText editTextPercent = new EditText(AddNewProductActivity.this);
                    editTextPercent.setHint("Nhập phần trăm lợi nhuận");
                    editTextPercent.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editTextPercent.setVisibility(EditText.GONE);
                    editTextPercent.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));

                    // Tạo một EditText mới để hiện giá bán = % giá nhập
                    EditText editTextSellPrice = new EditText(AddNewProductActivity.this);
                    editTextSellPrice.setEnabled(false);
                    editTextSellPrice.setHint("Giá bán");
                    editTextSellPrice.setVisibility(EditText.GONE);
                    editTextSellPrice.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));

                    // Thêm CheckBox và EditText vào LinearLayout
                    unitLayout.addView(checkBox);
                    unitLayout.addView(editTextPrice);
                    unitLayout.addView(editTextPercent);
                    unitLayout.addView(editTextSellPrice);

                    // Thêm LinearLayout vào checkboxContainer
                    checkboxContainer.addView(unitLayout);

                    // Thiết lập sự kiện kiểm tra trạng thái cho CheckBox
                    checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            editTextPrice.setVisibility(EditText.VISIBLE);
                            editTextPercent.setVisibility(EditText.VISIBLE);
                            editTextSellPrice.setVisibility(EditText.VISIBLE);
                        } else {
                            editTextPrice.setVisibility(EditText.GONE);
                            editTextPercent.setVisibility(EditText.GONE);
                            editTextSellPrice.setVisibility(EditText.GONE);
                        }
                    });

                    // Lắng nghe thay đổi trong editTextPrice để cập nhật editText150Percent
                    editTextPrice.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            try {
                                int price = Integer.parseInt(s.toString());
                                int percent = Integer.parseInt(editTextPercent.getText().toString());
                                int pricePercent = (int) (price + price * percent / 100);
                                editTextSellPrice.setText(String.valueOf(pricePercent));
                            } catch (NumberFormatException e) {
                                editTextSellPrice.setText("");
                            }
                        }
                    });
                    editTextPercent.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            try {
                                int percent = Integer.parseInt(s.toString());
                                int price = Integer.parseInt(editTextPrice.getText().toString());
                                int pricePercent = (int) (price + price * percent / 100);
                                editTextSellPrice.setText(String.valueOf(pricePercent));
                            } catch (NumberFormatException e) {
                                editTextSellPrice.setText("");
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
            }
        });
    }
}