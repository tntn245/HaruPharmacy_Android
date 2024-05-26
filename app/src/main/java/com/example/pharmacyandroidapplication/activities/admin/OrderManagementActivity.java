package com.example.pharmacyandroidapplication.activities.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.customer.OrderDetailsActivity;
import com.example.pharmacyandroidapplication.adapters.OrderRVAdapter;
import com.example.pharmacyandroidapplication.itemClickListener.OrderItemClickListener;
import com.example.pharmacyandroidapplication.models.Order;

import java.util.ArrayList;

public class OrderManagementActivity extends AppCompatActivity implements OrderItemClickListener{
    private RecyclerView orderRV;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order("#1", "1", 200000, "Đã thanh toán", "Chưa xử lý", "blacklist", "QNam"));
        orders.add(new Order("#2", "3", 50000, "Đã thanh toán", "Chưa xử lý", "ok", "QNam"));
        orders.add(new Order("#3", "4", 850000, "Chưa thanh toán", "Chưa xử lý", "ok", "QNam"));
        orders.add(new Order("#4", "5", 240000, "Đã thanh toán", "Chưa xử lý", "ok", "QNam"));
        orders.add(new Order("#5", "1", 200000, "Đã thanh toán", "Chưa xử lý", "ok", "QNam"));
        orderRV = (RecyclerView) findViewById(R.id.orderRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        orderRV.setLayoutManager(layoutManager);
        orderRV.setHasFixedSize(true);
        orderRV.setAdapter(new OrderRVAdapter(this, orders, this));
    }

    @Override
    public void onOrderItemClick(String orderId) {
        // Xử lý khi item được click, ví dụ chuyển đến trang mới và truyền ID
        Intent intent = new Intent(OrderManagementActivity.this, OrderDetailsManagementActivity.class);
        intent.putExtra("selectedOrderID", orderId);
        startActivity(intent);
    }
}
