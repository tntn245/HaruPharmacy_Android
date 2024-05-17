package com.example.pharmacyandroidapplication.activities.customer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {
    int quantity = 1;
    String unit;
    // Ánh xạ các phần tử trong dialog
    private ImageView dialogProductImg;
    private TextView dialogProductName;
    private TextView dialogProductPrice;
    private Button btnCloseDialog;
    private  RadioGroup radiogroup_unit;
    private RadioButton option1RadioButton;
    private RadioButton option2RadioButton;
    private TextView itemCartQuantity;
    private Button btnDecreaseQuantity;
    private Button btnIncreaseQuantity;
    private TextView productPriceBuy;
    private Button btnBuyNow;
    private Button btnAddToCart;
    private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Nhận giá trị của item từ Intent
        String product_id = getIntent().getStringExtra("product_id");

        // Hiển thị giá trị của item trong layout
        ImageView ProductImg = findViewById(R.id.product_img);
        TextView ProductName = findViewById(R.id.product_name);
        TextView ProductPrice = findViewById(R.id.product_price);
        TextView Valid = findViewById(R.id.danhmuc);
        TextView Ingredient = findViewById(R.id.ingredient);
        TextView Info  = findViewById(R.id.info);
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
                    if(product.isFlag_valid())
                    {
                        Valid.setText("Thuốc không kê đơn");
                    }else{
                        Valid.setText("Thuốc kê đơn");
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
        Button btnChooseToBuy = findViewById(R.id.btn_Chonmua);
        btnChooseToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị dialog
                showProductDetailDialog(product);
            }
        });
    }
    private void showProductDetailDialog(Product product) {
        Dialog dialog = new Dialog(ProductDetailsActivity.this,R.style.FullScreenDialog);
        dialog.setContentView(R.layout.drawer_product_detail);
        // Lấy kích thước của màn hình
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        // Thiết lập kích thước dialog
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height =  LayoutParams.WRAP_CONTENT; // 70% chiều cao của màn hình
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
        option1RadioButton = dialog.findViewById(R.id.option1RadioButton);
        option2RadioButton = dialog.findViewById(R.id.option2RadioButton);
        itemCartQuantity = dialog.findViewById(R.id.item_cart_quanlity);
        btnDecreaseQuantity = dialog.findViewById(R.id.btnDecreaseQuantity);
        btnIncreaseQuantity = dialog.findViewById(R.id.btnIncreaseQuantity);
        productPriceBuy = dialog.findViewById(R.id.product_price_buy);
        btnBuyNow = dialog.findViewById(R.id.btn_buy_now);
        btnAddToCart = dialog.findViewById(R.id.btn_add_to_cart);

        Glide.with(ProductDetailsActivity.this)
                .load(product.getImg())
                .into(dialogProductImg);
        dialogProductName.setText(product.getName());
        dialogProductPrice.setText(String.valueOf(product.getPrice()));
        float price = Integer.parseInt(dialogProductPrice.getText().toString());
        productPriceBuy.setText(String.valueOf(price));

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
                    itemCartQuantity.setText(String.valueOf(quantity));
                    float sumprice = price*quantity;
                    productPriceBuy.setText(String.valueOf(sumprice));
                }
            }
        });

        btnIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                itemCartQuantity.setText(String.valueOf(quantity));
                float sumprice = price*quantity;
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
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                String userId = currentUser.getUid();
                String prd_name =  dialogProductName.getText().toString();
                int prd_quantity= Integer.parseInt(itemCartQuantity.getText().toString());
                add_to_cart(userId,prd_name,prd_quantity,unit,dialog);
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

                    // Sử dụng nội dung của RadioButton được chọn
                    // Ví dụ: Hiển thị nội dung trong Log
                    Log.d("Selected Option", selectedOption);
                    unit = selectedOption;
                } else {
                    // Không có RadioButton nào được chọn
                    Log.d("Selected Option", "None selected");
                }
            }
        });

        // Hiển thị dialog
        dialog.show();
    }
    // Phương thức formatPrice để định dạng số:
    /*private String formatPrice(float price) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        float x = price;
        return numberFormat.format(x);
    }*/
    private void add_to_cart(String uid, String prd_name, int prd_quantity, String unit, Dialog dialog) {
        HashMap<String, Object> user_cart = new HashMap<>();
        user_cart.put("quantity", prd_quantity);
        user_cart.put("unit", unit);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("cart");
        mDatabase.child(uid).child(prd_name).setValue(user_cart)
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
