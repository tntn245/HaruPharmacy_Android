package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.OrderTrackingAdapter;
import com.example.pharmacyandroidapplication.models.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class OrdersTrackingActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private DatabaseReference orderDetailsRef = database.getReference("orderdetail");
    private DatabaseReference orderByIdRef = database.getReference("order").child(userID).getRef();
    private ArrayList<Order> OrdersArrayList;
    private int order_total_product;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        GridView listOrders = findViewById(R.id.list_orders);

        orderByIdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OrdersArrayList = new ArrayList<Order>();
                String id_order, noted, address;
                int total_payment, total_product;
                boolean payment_status, order_status;
                Date order_date;
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    id_order = item.getKey().toString();
                    noted = item.child("noted").getValue().toString();
                    address = item.child("address").getValue().toString();
                    total_payment = Integer.valueOf(item.child("total_payment").getValue().toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2024, Calendar.MAY, 23);
                    order_date = calendar.getTime();
                    payment_status = item.child("payment_status").getValue().toString().equals("Đã thanh toán");
                    order_status = item.child("order_status").getValue().toString().equals("Đang xử lý");
                    Order newOrder = new Order(id_order, userID, total_payment, payment_status, order_status, noted, address, order_date, 0);

                    DatabaseReference orderDetailByIdRef = orderDetailsRef.child(id_order).getRef().child("vỉ");
                    orderDetailByIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            int total_product = 0;
                            for (DataSnapshot detailItem : snapshot.getChildren()) {
                                // Lấy thông tin số lượng sản phẩm từ "detailItem"
                                Log.e("OrderTracking", "sl" + detailItem.getValue().toString() );
                                int quantity = Integer.parseInt(detailItem.child("quantity").getValue().toString());
                                total_product += quantity;

                                newOrder.setTotal_product(total_product);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    OrdersArrayList.add(newOrder);
                }
                OrdersArrayList.add(new Order("FDSFGFSG", userID, 100000, true, true, "", "496, đường 833", new Date(124, 4, 23), 10));
                OrderTrackingAdapter adapter = new OrderTrackingAdapter(OrdersTrackingActivity.this, OrdersArrayList);
                listOrders.setAdapter(adapter);
                listOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Lấy giá trị của item được click
                        Order item = OrdersArrayList.get(position);

                        // Truyền giá trị của item qua layout tiếp theo để hiển thị
                        Intent intent = new Intent(OrdersTrackingActivity.this, OrderDetailsActivity.class);
                        intent.putExtra("selectedOrderID", item.getId_order());
                        intent.putExtra("selectedOrderDate", item.getOrder_date());
                        intent.putExtra("selectedTotalPayment", item.getTotal_payment());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
