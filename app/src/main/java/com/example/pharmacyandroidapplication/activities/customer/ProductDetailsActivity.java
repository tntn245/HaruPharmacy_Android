package com.example.pharmacyandroidapplication.activities.customer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.Unit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {
    String userId;
    int quantity = 1;
    ArrayList<Unit> UnitArr = new ArrayList<>();
    String unit;
    // Ánh xạ các phần tử trong dialog
    private ImageView dialogProductImg;
    private TextView dialogProductName;
    private TextView dialogProductPrice;
    private Button btnCloseDialog;
    private Button btnChooseToBuy;
    private RadioGroup radiogroup_unit;
    private RadioButton option1RadioButton;
    private RadioButton option2RadioButton;
    private TextView itemCartQuantity;
    private Button btnDecreaseQuantity;
    private Button btnIncreaseQuantity;
    private TextView productPriceBuy;
    private Button btnBuyNow;
    private Button btnAddToCart;
    private Product product;
    private String product_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userId = currentUser.getUid();
        // Nhận giá trị của item từ Intent
        product_id = getIntent().getStringExtra("product_id");
        Log.d("Product ID", product_id);
        // Hiển thị giá trị của item trong layout
        ImageView ProductImg = findViewById(R.id.product_img);
        TextView ProductName = findViewById(R.id.product_name);
        TextView ProductPrice = findViewById(R.id.product_price);
        TextView Valid = findViewById(R.id.danhmuc);
        TextView Ingredient = findViewById(R.id.ingredient);
        TextView Info = findViewById(R.id.info);
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("product").child(product_id);
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Kiểm tra xem dataSnapshot có dữ liệu hay không
                if (dataSnapshot.exists()) {
                    // Lấy dữ liệu từ dataSnapshot và thực hiện các thao tác cần thiết
                    product = dataSnapshot.getValue(Product.class);

                    // bind vao
                    ProductPrice.setText(String.valueOf(product.getPrice()));
                    ProductName.setText(product.getName());
                    if (product.isPrescription()) {
                        Valid.setText("Thuốc kê đơn");
                        btnChooseToBuy.setVisibility(View.INVISIBLE);
                    } else {
                        Valid.setText("Thuốc không kê đơn");
                    }
                    Ingredient.setText(product.getIngredient());
                    Info.setText(product.getUses());
                    Glide.with(ProductDetailsActivity.this)
                            .load(product.getImg())
                            .into(ProductImg);
                    // Ví dụ: Hiển thị thông tin sản phẩm
                    Log.d("Product Info", "Name: " + product.getName() + ", Price: " + product.getPrice());
                } else {
                    Log.d("Product Info", "Product with key '2' does not exist.");
                }
                DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("product").child(product_id).child("unitarrr");
                data.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Lấy dữ liệu từ DataSnapshot và thực hiện các hành động cần thiết
                        for (DataSnapshot unitSnapshot : dataSnapshot.getChildren()) {
                            String name = unitSnapshot.child("name").getValue(String.class);
                            int price = unitSnapshot.child("price").getValue(Integer.class);
                            int quantity = unitSnapshot.child("quantity").getValue(Integer.class);
                            Unit unittt = new Unit(name, price, quantity);
                            // Thêm đối tượng Unit vào ArrayList
                            // Thực hiện các hành động với dữ liệu đã lấy được, ví dụ: hiển thị dữ liệu lên giao diện người dùng
                            Log.i("Unit ADD", "Name: " + unittt.getName() + ", Price: " + unittt.getPrice());
                            UnitArr.add(unittt);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý khi có lỗi xảy ra trong quá trình truy cập dữ liệu
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }

        });

        //ProductImg.setImageResource(R.drawable.pro1);
        ImageButton btn_back = findViewById(R.id.ic_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this, CustomerHomepageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Bên trong activity hoặc fragment
        @SuppressLint("ResourceType") Animation slideUp = AnimationUtils.loadAnimation(this, R.drawable.animation_slide_up);
        btnChooseToBuy = findViewById(R.id.btn_Chonmua);
        btnChooseToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị dialog
                showProductDetailDialog(product);
            }
        });
    }
    private void showProductDetailDialog(Product product) {
        quantity=1;
        Dialog dialog = new Dialog(ProductDetailsActivity.this, R.style.FullScreenDialog);
        dialog.setContentView(R.layout.drawer_product_detail);
        // Lấy kích thước của màn hình
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        // Thiết lập kích thước dialog
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT; // 70% chiều cao của màn hình
        layoutParams.gravity = Gravity.BOTTOM; // Hiển thị ở dưới cùng
        layoutParams.horizontalMargin = 0; // Thiết lập margin ngang
        layoutParams.verticalMargin = 0; // Thiết lập margin dọc
        // Thiết lập cho dialog
        dialog.getWindow().setAttributes(layoutParams);
        // Xử lý sự kiện khi nhấn các nút trong dialog
        // Ánh xạ các phần tử trong dialog
        dialogProductImg = dialog.findViewById(R.id.product_img);
        dialogProductName = dialog.findViewById(R.id.product_name);
        dialogProductPrice = dialog.findViewById(R.id.product_price);
        btnCloseDialog = dialog.findViewById(R.id.close_button);
        radiogroup_unit = dialog.findViewById(R.id.group_unit);
            /*option1RadioButton = dialog.findViewById(R.id.option1RadioButton);
            option2RadioButton = dialog.findViewById(R.id.option2RadioButton);*/
        itemCartQuantity = dialog.findViewById(R.id.item_cart_quanlity);
        btnDecreaseQuantity = dialog.findViewById(R.id.btnDecreaseQuantity);
        btnIncreaseQuantity = dialog.findViewById(R.id.btnIncreaseQuantity);
        productPriceBuy = dialog.findViewById(R.id.product_price_buy);
        btnBuyNow = dialog.findViewById(R.id.btn_buy_now);
        btnAddToCart = dialog.findViewById(R.id.btn_add_to_cart);
        quantity = Integer.parseInt((String) itemCartQuantity.getText());
        Glide.with(ProductDetailsActivity.this)
                .load(product.getImg())
                .into(dialogProductImg);
        dialogProductName.setText(product.getName());
        dialogProductPrice.setText(String.valueOf(product.getPrice()));
        productPriceBuy.setText(String.valueOf(product.getPrice()));

        // Gán vào RadioButton
        for (int i = 0; i < UnitArr.size(); i++) {
            Unit unit = UnitArr.get(i);
            RadioButton radioButton = new RadioButton(this);
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen._70sdp),
                    getResources().getDimensionPixelSize(R.dimen._30sdp)
            ));
            radioButton.setPadding(
                    getResources().getDimensionPixelSize(R.dimen._2sdp),
                    getResources().getDimensionPixelSize(R.dimen._2sdp),
                    getResources().getDimensionPixelSize(R.dimen._2sdp),
                    getResources().getDimensionPixelSize(R.dimen._2sdp)
            );
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_size_medium));
            radioButton.setTypeface(Typeface.create("sans-serif-black", Typeface.BOLD));
            radioButton.setText(unit.getName());
            radioButton.setId(i); // Gán id cho từng RadioButton
            radioButton.setButtonDrawable(null);
            Drawable background = getResources().getDrawable(R.drawable.custom_radio_button);
            radioButton.setBackground(background);
            if (i == 0) {
                radioButton.setChecked(true); // Đặt RadioButton đầu tiên được chọn mặc định
            }
            radiogroup_unit.addView(radioButton);
        }

        // Set thông tin cho các phần tử trong dialog
        // (Bạn có thể lấy thông tin từ activity hoặc Intent)
        // Gắn sự kiện cho nút đóng dialog
        btnCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Đóng dialog khi nhấn nút đóng
            }
        });

        // Gắn sự kiện cho các nút cộng/trừ số lượng
        btnDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) { // Đảm bảo số lượng không nhỏ hơn 1
                    quantity--;
                    Integer price = Integer.parseInt(dialogProductPrice.getText().toString());
                    itemCartQuantity.setText(String.valueOf(quantity));
                    int sumprice = price * quantity;
                    productPriceBuy.setText(String.valueOf(sumprice));
                }
            }
        });

        btnIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                Integer price = Integer.parseInt(dialogProductPrice.getText().toString());
                itemCartQuantity.setText(String.valueOf(quantity));
                int sumprice = price * quantity;
                productPriceBuy.setText(String.valueOf(sumprice));
            }
        });
        // Gắn sự kiện cho các nút "Mua ngay" và "Thêm vào giỏ"
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi nhấn nút "Mua ngay"
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi nhấn nút "Thêm vào giỏ"

                int prd_quantity = Integer.parseInt(itemCartQuantity.getText().toString());
                int price = Integer.parseInt(productPriceBuy.getText().toString());
                int checkedRadioButtonId = radiogroup_unit.getCheckedRadioButtonId();

                if (checkedRadioButtonId != -1) {
                    RadioButton checkedRadioButton = dialog.findViewById(checkedRadioButtonId);
                    // Bạn có thể thực hiện các thao tác khác với RadioButton đang được chọn ở đây
                    String checkedRadioButtonText = checkedRadioButton.getText().toString();
                    // Ví dụ: Lấy văn bản của RadioButton đang được chọn
                   unit = checkedRadioButtonText;
                } else {
                    // Không có RadioButton nào được chọn
                    Log.d("Checked RadioButton", "None selected");
                }
                add_to_cart(userId, product_id, prd_quantity, unit, price, dialog);
            }
        });

        // Lắng nghe sự kiện khi RadioButton được chọn trong RadioGroup
        radiogroup_unit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Kiểm tra xem RadioButton nào được chọn
                if (checkedId != -1) {
                    // RadioButton được chọn tồn tại
                    RadioButton selectedRadioButton = dialog.findViewById(checkedId);
                    // Lấy nội dung của RadioButton được chọn
                    String selectedOption = selectedRadioButton.getText().toString();
                    // Ví dụ: Hiển thị nội dung trong Log
                    Log.d("Selected Option", selectedOption);
                    unit = selectedOption;
                    DatabaseReference dataS = FirebaseDatabase.getInstance().getReference().child("product").child(product_id).child("unitarrr").child(unit);
                    dataS.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Lấy dữ liệu từ DataSnapshot và thực hiện các hành động cần thiết
                            if (dataSnapshot.exists()) {
                                // Lấy dữ liệu từ dataSnapshot và thực hiện các thao tác cần thiết
                                Unit un = dataSnapshot.getValue(Unit.class);
                                dialogProductPrice.setText(String.valueOf(un.getPrice()));
                            } else {
                                Log.d("Product Info", "Product with key '2' does not exist.");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Xử lý khi có lỗi xảy ra trong quá trình truy cập dữ liệu
                        }
                    });

                } else {
                    // Không có RadioButton nào được chọn
                    Log.d("Selected Option", "None selected");
                }
            }
        });
                    // Hiển thị dialog
                    dialog.show();
                }
    private void add_to_cart(String uid, String prd_id, int prd_quantity, String unit,int price, Dialog dialog) {
        HashMap<String, Object> user_cart = new HashMap<>();
        user_cart.put("quantity", prd_quantity);
        user_cart.put("unit", unit);
        user_cart.put("price",price);
        String cartKey = prd_id + "@" + unit;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("cart");
        mDatabase.child(uid).child(cartKey).setValue(user_cart)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Xử lý khi insert thành công
                        Toast.makeText(getApplicationContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        // Ẩn dialog hoặc thực hiện các hành động khác ở đây
                        dialog.dismiss(); // Ví dụ: Ẩn dialog sau khi insert thành công
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi insert thất bại
                        Toast.makeText(getApplicationContext(), "Insert thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}