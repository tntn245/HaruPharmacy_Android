package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

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
    private ArrayList<Order> orderArrayList;
    private OrderTrackingAdapter adapter;
    private TextView txt_processing_order, txt_delivering_order, txt_delivered_order,txt_cancel_order;
    private boolean choose = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        GridView listOrders = findViewById(R.id.list_orders);
        txt_processing_order = findViewById(R.id.txt_processing_order);
        txt_delivering_order = findViewById(R.id.txt_delivering_order);
        txt_delivered_order = findViewById(R.id.txt_delivered_order);
        txt_cancel_order = findViewById(R.id.txt_cancel_order);
        orderArrayList = new ArrayList<Order>();
        retrieveOrderDataByStatus("Đang xử lý");
        adapter = new OrderTrackingAdapter(OrdersTrackingActivity.this, orderArrayList);
        listOrders.setAdapter(adapter);
        Bundle extras = getIntent().getExtras();
        txt_processing_order.setTypeface(null, Typeface.BOLD);
        if (extras != null) {
            int orderStatus = extras.getInt("orderStatus");
            Log.e("status", "status: " + orderStatus );
            if (orderStatus == 2) {
                        txt_processing_order.setTypeface(null, Typeface.NORMAL);
                        txt_delivered_order.setTypeface(null, Typeface.NORMAL);
                        txt_delivering_order.setTypeface(null, Typeface.BOLD);
                        retrieveOrderDataByStatus("Đang giao");

            } else if (orderStatus == 3) {
                        txt_processing_order.setTypeface(null, Typeface.NORMAL);
                        txt_delivering_order.setTypeface(null, Typeface.NORMAL);
                        txt_delivered_order.setTypeface(null, Typeface.BOLD);
                        retrieveOrderDataByStatus("Đã giao");
            } else {
                        txt_delivering_order.setTypeface(null, Typeface.NORMAL);
                        txt_delivered_order.setTypeface(null, Typeface.NORMAL);
                        txt_processing_order.setTypeface(null, Typeface.BOLD);
                        retrieveOrderDataByStatus("Đang xử lý");
            }
        }
        txt_processing_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_delivering_order.setTypeface(null, Typeface.NORMAL);
                txt_delivered_order.setTypeface(null, Typeface.NORMAL);
                txt_cancel_order.setTypeface(null, Typeface.NORMAL);
                txt_processing_order.setTypeface(null, Typeface.BOLD);
                retrieveOrderDataByStatus("Đang xử lý");
            }
        });
        txt_delivering_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_processing_order.setTypeface(null, Typeface.NORMAL);
                txt_delivered_order.setTypeface(null, Typeface.NORMAL);
                txt_cancel_order.setTypeface(null, Typeface.NORMAL);
                txt_delivering_order.setTypeface(null, Typeface.BOLD);
                retrieveOrderDataByStatus("Đang giao");
            }
        });
        txt_delivered_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_processing_order.setTypeface(null, Typeface.NORMAL);
                txt_delivering_order.setTypeface(null, Typeface.NORMAL);
                txt_cancel_order.setTypeface(null, Typeface.NORMAL);
                txt_delivered_order.setTypeface(null, Typeface.BOLD);
                retrieveOrderDataByStatus("Đã giao");
            }
        });
        txt_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_processing_order.setTypeface(null, Typeface.NORMAL);
                txt_delivering_order.setTypeface(null, Typeface.NORMAL);
                txt_delivered_order.setTypeface(null, Typeface.NORMAL);
                txt_cancel_order.setTypeface(null, Typeface.BOLD);
                retrieveOrderDataByStatus("Đã hủy");
            }
        });
        listOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị của item được click
                Order item = orderArrayList.get(position);

                // Truyền giá trị của item qua layout tiếp theo để hiển thị
                Intent intent = new Intent(OrdersTrackingActivity.this, OrderDetailsActivity.class);
                intent.putExtra("selectedOrderID", item.getId_order());
                intent.putExtra("selectedOrderDate", item.getOrder_date());
                intent.putExtra("selectedTotalPayment", item.getTotal_payment());
                Typeface typeface = txt_processing_order.getTypeface();
                if (typeface != null && typeface.isBold()) {
                    choose = true;
                } else {
                    choose = false;
                }
                intent.putExtra("choose", choose);
                startActivity(intent);
            }
        });
    }

    public void retrieveOrderDataByStatus(String status) {
        orderByIdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderArrayList.clear();
                String id_order, noted, address, payment_status, order_status;
                int total_payment, total_product;
                Date order_date;
                for (DataSnapshot item : snapshot.getChildren()) {
                    if (item.child("order_status").getValue().toString().equals(status)) {
                        id_order = id_order = item.getKey().toString();
                        noted = item.child("noted").getValue().toString();
                        address = item.child("address").getValue().toString();
                        total_payment = Integer.valueOf(item.child("total_payment").getValue().toString());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(2024, Calendar.MAY, 23);
                        order_date = calendar.getTime();
                        payment_status = item.child("payment_status").getValue().toString();
                        order_status = item.child("order_status").getValue().toString();
                        Order newOrder = new Order(id_order, userID, total_payment, payment_status, order_status, noted, address, order_date, 0);
                        orderArrayList.add(newOrder);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
