package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.CartAdapter;
import com.example.pharmacyandroidapplication.models.Cart;
import com.example.pharmacyandroidapplication.models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.reactivex.rxjava3.annotations.NonNull;

public class CartActivity extends AppCompatActivity {
    private ArrayList<Cart> carts;
    private RecyclerView mrecycleview;
    private CartAdapter mcartadapter;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mrecycleview = findViewById(R.id.recyclercart);
        carts = new ArrayList<>();
        loadFireBase();
        mcartadapter = new CartAdapter(this, carts);
        mrecycleview.setAdapter(mcartadapter);
        mrecycleview.setLayoutManager(new LinearLayoutManager(this));

        Button btnBuy = findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
        CheckBox checkAllCheckBox = findViewById(R.id.check_all);
        checkAllCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Xử lý sự kiện khi trạng thái của nút "check_all" thay đổi
                if (isChecked) {
                    // Đánh dấu tất cả các mục trong RecyclerView là đã chọn
                    // Duyệt qua danh sách các mục trong RecyclerView và gọi phương thức setChecked(true)
                    for (int i = 0; i < mcartadapter.getItemCount(); i++) {
                        mcartadapter.setChecked(i, true);
                    }
                } else {
                    // Bỏ đánh dấu tất cả các mục trong RecyclerView
                    // Duyệt qua danh sách các mục trong RecyclerView và gọi phương thức setChecked(false)
                    for (int i = 0; i < mcartadapter.getItemCount(); i++) {
                        mcartadapter.setChecked(i, false);
                    }
                }
            }
        });
    }
    private void createCartList() {
        carts.add(new Cart( "101","101","Thực phẩm chữa lành",100000,1,false,"Hộp","https://firebasestorage.googleapis.com/v0/b/harupharmacy-f4929.appspot.com/o/images%2Fpro1.png?alt=media&token=2d6153e4-a7a7-4fdc-88f2-6a2f4b43378f"));
        carts.add(new Cart( "101","101","Thực phẩm chữa rách",100000,1,false,"Hộp","https://firebasestorage.googleapis.com/v0/b/harupharmacy-f4929.appspot.com/o/images%2Fpro2.jpg?alt=media&token=90fb857e-aaf8-4c58-b2cd-d8dcc0635025"));
        carts.add(new Cart( "101","101","Thực phẩm chữa què",100000,1,false,"Hộp","https://firebasestorage.googleapis.com/v0/b/harupharmacy-f4929.appspot.com/o/images%2Fpro3.png?alt=media&token=3a9cb0ca-a8fa-4ea0-9c95-3b9421955772"));
        carts.add(new Cart( "101","101","Thực phẩm chữa tổn thương",100000,1,false,"Hộp","https://firebasestorage.googleapis.com/v0/b/harupharmacy-f4929.appspot.com/o/images%2Fpro3.png?alt=media&token=3a9cb0ca-a8fa-4ea0-9c95-3b9421955772"));
    }
    private void loadFireBase(){
        // Lấy ID người dùng đã được xác thực
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.i("ID",userId);
        // Tạo tham chiếu đến node "cart" trong Firebase
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart").child(userId);
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa dữ liệu cũ trong danh sách giỏ hàng
                carts.clear();
                // Duyệt qua các nút con trong node "cart"
                for (DataSnapshot cartSnapshot : dataSnapshot.getChildren()) {
                    String productId_unit = cartSnapshot.getKey();
                    Log.i("PRD_ID",productId_unit);
                    int quantity = cartSnapshot.child("quantity").getValue(Integer.class);
                    String unit = cartSnapshot.child("unit").getValue(String.class);
                    Integer price = cartSnapshot.child("price").getValue(Integer.class);
                    Log.i("PRICE"," "+quantity+" "+unit+" "+price);
                    // Tách prd_id từ productId
                    String[] productIdParts = productId_unit.split("_");
                    String prd_id = productIdParts[0];
                    Log.i("PRD_ID",prd_id);
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("product").child(prd_id);
                    databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                product = dataSnapshot.getValue(Product.class);
                                carts.add(new Cart(userId,prd_id,product.getName(),price,quantity,false,unit,product.getImg()));
                                Log.i("Carrt",carts.toString());
                                // Cập nhật lại Adapter để hiển thị danh sách giỏ hàng mới
                                mcartadapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Xử lý khi có lỗi xảy ra
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy
            }
        });
    }
}
