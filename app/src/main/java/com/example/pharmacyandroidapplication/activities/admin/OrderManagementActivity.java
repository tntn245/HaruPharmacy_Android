package com.example.pharmacyandroidapplication.activities.admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.OrderRVAdapter;
import com.example.pharmacyandroidapplication.models.Order;

import java.util.ArrayList;

public class OrderManagementActivity extends AppCompatActivity {
    private RecyclerView orderRV;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order("#1", "1", 200000, true, false, "blacklist", "QNam"));
        orders.add(new Order("#2", "3", 50000, true, false, "ok", "QNam"));
        orders.add(new Order("#3", "4", 850000, false, false, "ok", "QNam"));
        orders.add(new Order("#4", "5", 240000, true, false, "ok", "QNam"));
        orders.add(new Order("#5", "1", 200000, true, false, "ok", "QNam"));
        orderRV = (RecyclerView) findViewById(R.id.orderRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        orderRV.setLayoutManager(layoutManager);
        orderRV.setHasFixedSize(true);
        orderRV.setAdapter(new OrderRVAdapter(this, orders));
    }
}
