package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.OrderDetailsAdapter;
import com.example.pharmacyandroidapplication.models.DateFormat;
import com.example.pharmacyandroidapplication.models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderDetailsActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference orderDetailRef = database.getReference("orderdetail");
    private DatabaseReference productRef = database.getReference("product");
    private ArrayList<Product> ProductArrayList = new ArrayList<>();
    private String nameActivity = this.getClass().getSimpleName().toString();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        // Lấy Intent từ Activity
        Intent intent = getIntent();
        String orderID = intent.getStringExtra("selectedOrderID");
        Date orderDate = (Date) intent.getSerializableExtra("selectedOrderDate");
        int orderTotalPayment = intent.getIntExtra("selectedTotalPayment", 0);
        boolean choose = intent.getBooleanExtra("choose", true);
        // Hiển thị thông tin
        TextView orderID_tv = findViewById(R.id.order_id);
        orderID_tv.setText(orderID);
        Button btn_cancel_order = findViewById(R.id.btn_cancel_order);
        if(choose) btn_cancel_order.setVisibility(View.VISIBLE);
        TextView orderDate_tv = findViewById(R.id.order_date);
        assert orderDate != null;
        DateFormat dateFormat = new DateFormat(orderDate);
        orderDate_tv.setText(dateFormat.formatDateToString());

        TextView orderTotalPayment_tv = findViewById(R.id.order_total_payment);
        orderTotalPayment_tv.setText(Integer.toString(orderTotalPayment));

        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser != null) {
                    String uid = currentUser.getUid();
                    DatabaseReference orderRef = database.getReference("order").child(uid).child(orderID);

                    // Tạo một HashMap để lưu các giá trị cập nhật
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("order_status", "Đã hủy");

                    // Thực hiện cập nhật
                    orderRef.updateChildren(updates, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                // Có lỗi xảy ra khi cập nhật
                                Log.d("FirebaseUpdate", "Cập nhật thất bại: " + databaseError.getMessage());
                            } else {
                                // Cập nhật thành công
                                Log.d("FirebaseUpdate", "Cập nhật thành công!");
                                Intent intent = new Intent(OrderDetailsActivity.this, OrdersTrackingActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Log.d("CurrentUser", "User is not signed in.");
                }
            }
        });
        // Hiển thị item trong gridview (load từ dtb dựa trên id của order)
        GridView ProductGV = findViewById(R.id.list_products);

        DatabaseReference orderdetailByIdRef = orderDetailRef.child(orderID);
        orderdetailByIdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    String id = item.getKey().toString();
                    DatabaseReference productByIdRef = productRef.child(id).getRef();
                    productByIdRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = snapshot.child("name").getValue(String.class);
                            String img = snapshot.child("img").getValue(String.class);

                            DatabaseReference orderDetailByIdProductRef = orderdetailByIdRef.child(id).getRef();
                            orderDetailByIdProductRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                    for (DataSnapshot item1 : snapshot1.getChildren()) {
                                        String unit = item1.getKey().toString();

                                        DatabaseReference orderDetailByIdProductUnitRef = orderDetailByIdProductRef.child(unit).getRef();
                                        orderDetailByIdProductUnitRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                                int quantity_ordered = Integer.valueOf(snapshot2.child("quantity").getValue(String.class));
                                                int sell_price = Integer.valueOf(snapshot2.child("unit_sell_price").getValue(String.class));

                                                Product product = new Product(id);
                                                if (nameActivity.equals("OrderDetailsActivity")) {
                                                product.setName(name);
                                                product.setImg(img);
                                                product.setUnit(unit);
                                                product.setInventory_quantity(quantity_ordered);
                                                product.setPrice(sell_price);
                                                }
                                                ProductArrayList.add(product);

                                                // Tạo adapter và đặt adapter cho GridView
                                                OrderDetailsAdapter productAdapter = new OrderDetailsAdapter(OrderDetailsActivity.this, ProductArrayList);
                                                ProductGV.setAdapter(productAdapter);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
