package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.CartAdapter;
import com.example.pharmacyandroidapplication.models.Cart;
import com.example.pharmacyandroidapplication.models.ItemPay;
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
        TextView sumprice = findViewById(R.id.sum_price);

        Button btnBuy = findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if (mcartadapter.getSelectedItems().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                intent.putParcelableArrayListExtra("selectedItems", new ArrayList<>(mcartadapter.getSelectedItems()));
                startActivity(intent);
            }
        });
        CheckBox checkAllCheckBox = findViewById(R.id.check_all);
        checkAllCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Xử lý sự kiện khi trạng thái của nút "check_all" thay đổi
                int pricesum = 0;
                if (isChecked) {
                    // Đánh dấu tất cả các mục trong RecyclerView là đã chọn
                    // Duyệt qua danh sách các mục trong RecyclerView và gọi phương thức setChecked(true)
                    mcartadapter.empty();
                    for (int i = 0; i < mcartadapter.getItemCount(); i++) {
                        mcartadapter.setChecked(i, true);
                        // Lấy giá và số lượng của mỗi mục đã chọn và cập nhật tổng giá trị
                        ItemPay itemPay = new ItemPay(carts.get(i).getId_product(), carts.get(i).getUnit(), carts.get(i).getName_product(), carts.get(i).getQuanlity(), carts.get(i).getPrice_product(), carts.get(i).getImg());
                        mcartadapter.addSelectedItem(itemPay);
                        mcartadapter.setSumPrice(mcartadapter.calculateTotalPrice());
                    }
                } else {
                    // Bỏ đánh dấu tất cả các mục trong RecyclerView
                    mcartadapter.empty();
                    // Duyệt qua danh sách các mục trong RecyclerView và gọi phương thức setChecked(false)
                    for (int i = 0; i < mcartadapter.getItemCount(); i++) {
                        mcartadapter.setChecked(i, false);
                    }
                    mcartadapter.setSumPrice(0);
                }
                // Cập nhật TextView sum_price sau khi duyệt qua toàn bộ danh sách
                sumprice.setText(String.valueOf(mcartadapter.getSumPrice()) + "đ");
            }
        });

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
                    String[] productIdParts = productId_unit.split("@");
                    String prd_id = productIdParts[0];
                    Log.i("PRD_ID",prd_id);
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("product").child(prd_id);
                    databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                product = dataSnapshot.getValue(Product.class);
                                carts.add(new Cart(userId,productId_unit,product.getName(),price,quantity,false,unit,product.getImg()));
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
