package com.example.pharmacyandroidapplication.activities.admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.LoginActivity;
import com.example.pharmacyandroidapplication.models.CheckboxData;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.Unit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EditProductActivity extends AppCompatActivity {
    private EditText txt_product_name, txt_product_ingredient, txt_product_uses;
    Spinner spinner_category_name, spinner_status;
    private LinearLayout checkboxContainer;
    private ArrayAdapter<Product> productNameAdapter;
    private ArrayList<Product> productList;
    private ImageButton add_img_product_img;
    private ImageView img_product;
    String productID;
    Map<String, Object> unitArr;
    String categoryID;
    String categoryName;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Map<String, CheckBox> checkBoxMap = new HashMap<>();
    Map<String, EditText> editTextPriceMap = new HashMap<>();
    Map<String, EditText> editTextSellPriceMap = new HashMap<>();
    Product product;
    Integer percentProfit;
    StorageReference storageReference;
    Uri image;
    String imageStr;
    private ArrayList typeList;
    private ArrayAdapter<String> adapter;
    ArrayList<CheckboxData> checkboxData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        productID = getIntent().getExtras().getString("productID");
        loadPercentProfit();

        storageReference = FirebaseStorage.getInstance().getReference();
        txt_product_name = findViewById(R.id.spinner_product_name);
        spinner_category_name = findViewById(R.id.spinner_category_name);
        txt_product_ingredient = findViewById(R.id.txt_product_ingredient);
        txt_product_uses = findViewById(R.id.txt_product_uses);
        spinner_status = findViewById(R.id.spinner_status);
        checkboxContainer = findViewById(R.id.checkboxContainer);
        add_img_product_img = findViewById(R.id.add_img_product_img);
        img_product = findViewById(R.id.img);

        typeList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        retrieveCategoryData("name");
        spinner_category_name.setAdapter(adapter);

        retrieveUnitData("unit_name");

        ArrayList<String> statusList = new ArrayList<>();
        statusList.add("Đang kinh doanh");
        statusList.add("Ngừng kinh doanh");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_status.setAdapter(statusAdapter);

        productList = new ArrayList<>();
        Button btn_save_add_product = findViewById(R.id.btn_save_add_product);
        btn_save_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
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
                Toast.makeText(EditProductActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EditProductActivity.this, "Failed!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        // Đường dẫn của ảnh
                        image = downloadUri;
                        imageStr = image.toString();
                        Toast.makeText(EditProductActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void loadPercentProfit() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("attribute").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                percentProfit = dataSnapshot.child("percent_profit").getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu
            }
        });

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

    private String getCategoryName(String cateID) {
        // Tạo một DatabaseReference để tham chiếu đến node chứa dữ liệu sản phẩm trong Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoryRef = database.getReference("category");        // Thêm một ChildEventListener để lắng nghe sự thay đổi dữ liệu
        categoryRef.child(cateID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    categoryName = dataSnapshot.child("name").getValue(String.class);
                } else {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý sự kiện onCancelled
            }
        });
        return categoryName;
    }

    // Phương thức để đặt lại trạng thái của tất cả CheckBox và EditText
    private void resetCheckBoxStates() {
        int childCount = checkboxContainer.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View view = checkboxContainer.getChildAt(i);

            // Kiểm tra nếu view là LinearLayout chứa CheckBox và EditText
            if (view instanceof LinearLayout) {
                LinearLayout unitLayout = (LinearLayout) view;
                int layoutChildCount = unitLayout.getChildCount();

                for (int j = 0; j < layoutChildCount; j++) {
                    View childView = unitLayout.getChildAt(j);

                    // Đặt lại trạng thái của CheckBox
                    if (childView instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) childView;
                        checkBox.setChecked(false);
                    }

                    // Đặt lại trạng thái của EditText
                    if (childView instanceof EditText) {
                        EditText editText = (EditText) childView;
                        editText.setText(""); // Xóa văn bản
                        editText.setVisibility(View.GONE); // Ẩn EditText
                    }
                }
            }
        }
    }

    private void loadProductFromFirebase() {
        DatabaseReference productsRef = database.getReference("product");
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy dữ liệu từ snapshot và tạo đối tượng Product
                    String product_id = snapshot.child("id").getValue(String.class);
                    if (product_id.equals(productID)) {
                        String productName = snapshot.child("name").getValue(String.class);
                        int productPrice = snapshot.child("price").getValue(Integer.class);
                        imageStr = snapshot.child("img").getValue(String.class);
                        categoryID = snapshot.child("id_category").getValue(String.class);
                        String unit = snapshot.child("unit").getValue(String.class);
                        int inventory_quantity = snapshot.child("inventory_quantity").getValue(Integer.class);
                        String uses = snapshot.child("uses").getValue(String.class);
                        String ingredient = snapshot.child("ingredient").getValue(String.class);
                        Boolean flagValid = Boolean.TRUE.equals(snapshot.child("flag_valid").getValue(Boolean.class));
                        Boolean prescription = Boolean.TRUE.equals(snapshot.child("prescription").getValue(Boolean.class));

                        unitArr = new HashMap<>();
                        // Lấy dữ liệu từ unitarr
                        for (DataSnapshot unitSnapshot : snapshot.child("unitarrr").getChildren()) {
                            String unitName = unitSnapshot.getKey();
                            Integer price = (unitSnapshot.child("price").getValue(Integer.class) != null)
                                    ? unitSnapshot.child("price").getValue(Integer.class) : 0;
                            Integer quantity = (unitSnapshot.child("quantity").getValue(Integer.class) != null)
                                    ? unitSnapshot.child("quantity").getValue(Integer.class) : 0;

                            // Tạo một map để chứa giá và số lượng của đơn vị
                            Map<String, Object> unitData = new HashMap<>();
                            unitData.put("price", price);
                            unitData.put("quantity", quantity);

                            // Thêm đơn vị vào unitArr
                            unitArr.put(unitName, unitData);

                            for (CheckboxData item : checkboxData) {
                                CheckBox checkbox = item.getCheckBox();
                                EditText editTextPrice = item.getEditTextPrice();
                                EditText editTextSellPrice = item.getEditTextSellPrice();

                                if (unitName.equals(item.getCheckBox().getText().toString())) {
                                    checkbox.setChecked(true);
                                    checkbox.setEnabled(false);

                                    editTextPrice.setText(String.valueOf(price));

                                    int sellPrice = price + 50 * price / 100;
                                    editTextSellPrice.setText(String.valueOf(sellPrice));

                                    Log.d("aaaaa", String.valueOf(checkboxData.size()));


                                }
                                ViewGroup parent = (ViewGroup) item.getCheckBox().getParent();
                                if (parent != null) {
                                    parent.removeView(item.getCheckBox());
                                }
                                ViewGroup parentPrice = (ViewGroup) item.getEditTextPrice().getParent();
                                if (parentPrice != null) {
                                    parentPrice.removeView(item.getEditTextPrice());
                                }
                                ViewGroup parentSellPrice = (ViewGroup) item.getEditTextSellPrice().getParent();
                                if (parentSellPrice != null) {
                                    parentSellPrice.removeView(item.getEditTextSellPrice());
                                }
                                checkboxContainer.addView(checkbox);
                                checkboxContainer.addView(editTextPrice);
                                checkboxContainer.addView(editTextSellPrice);
                            }
                        }

                        product = new Product(productID, categoryID, imageStr, productName, productPrice, inventory_quantity, unit, uses, ingredient, flagValid, prescription, unitArr);

                        txt_product_name.setText(product.getName());
                        categoryName = getCategoryName(categoryID);
                        int position = typeList.indexOf(categoryName);
                        if (position >= 0) {
                            spinner_category_name.setSelection(position);
                        }
                        txt_product_ingredient.setText(product.getIngredient());
                        txt_product_uses.setText(product.getUses());
                        boolean productStatus = product.isFlag_valid();
                        if (productStatus) {
                            spinner_status.setSelection(0);
                        } else {
                            spinner_status.setSelection(1);
                        }
                        Glide.with(getApplicationContext()).load(image).into(img_product);

                        resetCheckBoxStates();
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void saveProduct() {
        Map<String, Object> arr = new HashMap<>();
        DatabaseReference productRef = database.getReference("product").child(productID);
        boolean flagCheckbox = false;
        boolean flagDone = false;

        String unitName = null;
        int unitPrice = 0;

        for (int i = 0; i < checkboxContainer.getChildCount(); i++) {
            View view = checkboxContainer.getChildAt(i);
            Log.d("aaaabbb", String.valueOf(i));

            if (view instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) view;
                if (checkBox.isChecked()) {
                    flagCheckbox = true;
                    flagDone = false;
                    unitName = checkBox.getText().toString();
                }
            }
            if (view instanceof EditText) {
                EditText editText = (EditText) view;
                if (flagCheckbox) {
                    flagCheckbox = false;
                    flagDone = true;
                    String editTextPriceText = editText.getText().toString().trim();
                    unitPrice = editTextPriceText.isEmpty() ? 0 : Integer.parseInt(editTextPriceText);
                } else {
                    flagDone = false;
                }
            }

            if (flagDone) {
                Log.d("aaaacccccc", unitName + String.valueOf(unitPrice));

                Map<String, Object> unitData = new HashMap<>();
                unitData.put("price", unitPrice);
                unitData.put("quantity", 0);

                arr.put(unitName, unitData);
            }
        }
        String img = imageStr;
        String name = txt_product_name.getText().toString();
        String ingredient = txt_product_ingredient.getText().toString();
        String uses = txt_product_uses.getText().toString();
        boolean flagValid = (spinner_status.getSelectedItem().toString() == "Đang kinh doanh");

        // Thực hiện cập nhật thông tin đơn vị vào cơ sở dữ liệu Firebase
        productRef.child("img").setValue(img);
        productRef.child("name").setValue(name);
        productRef.child("ingredient").setValue(ingredient);
        productRef.child("uses").setValue(uses);
        productRef.child("flag_valid").setValue(flagValid);
        productRef.child("unitarrr").setValue(arr);

        Intent intent = new Intent(EditProductActivity.this, ProductManagementActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveUnitToDatabase(String unitName, int unitPrice) {
        // Tạo một đối tượng Unit để lưu vào cơ sở dữ liệu Firebase
        Unit unit = new Unit(unitName, unitPrice, 0);

        // Thực hiện cập nhật thông tin đơn vị vào cơ sở dữ liệu Firebase
        DatabaseReference unitRef = database.getReference("product").child(productID).child("unitarrr");
        unitRef.child(unitName).setValue(unit);
    }

    public void retrieveCategoryData(String attr) {
        DatabaseReference categoryRef = database.getReference("category");
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
        DatabaseReference unitRef = database.getReference("unit");
        unitRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                checkboxContainer.removeAllViews();

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String unitID = item.getKey();
                    String unitName = item.child(attr).getValue(String.class);

                    CheckBox checkBox = new CheckBox(getApplicationContext());
                    checkBox.setText(unitName);

                    EditText editTextPrice = new EditText(getApplicationContext());
                    editTextPrice.setText(String.valueOf(0));
                    editTextPrice.setVisibility(View.GONE);

                    EditText editTextSellPrice = new EditText(getApplicationContext());
                    editTextSellPrice.setText(String.valueOf(0));
                    editTextSellPrice.setVisibility(View.GONE);
                    editTextSellPrice.setEnabled(false);

                    CheckboxData cbdata = new CheckboxData(checkBox, editTextPrice, editTextSellPrice);
                    checkboxData.add(cbdata);

                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                editTextPrice.setVisibility(View.VISIBLE);
                                editTextSellPrice.setVisibility(View.VISIBLE);
                                Toast.makeText(EditProductActivity.this, checkBox.getText().toString() + " checked", Toast.LENGTH_SHORT).show();
                            } else {
                                editTextPrice.setVisibility(View.GONE);
                                editTextSellPrice.setVisibility(View.GONE);
                                Toast.makeText(EditProductActivity.this, checkBox.getText().toString() + " unchecked", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

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
                                int price150Percent = (int) (price + price * 50 / 100);
                                editTextSellPrice.setText(String.valueOf(price150Percent));
                            } catch (NumberFormatException e) {
                                editTextSellPrice.setText("");
                            }
                        }
                    });
                }
                Log.d("aaaaa", "unit" + String.valueOf(checkboxData.size()));
                loadProductFromFirebase();

//                for(CheckboxData item : checkboxData){
//                    checkboxContainer.addView(item.getCheckBox());
//                    checkboxContainer.addView(item.getEditTextPrice());
//                    checkboxContainer.addView(item.getEditTextSellPrice());
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
            }
        });
    }
}