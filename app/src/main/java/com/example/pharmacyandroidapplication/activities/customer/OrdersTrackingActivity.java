package com.example.pharmacyandroidapplication.activities.customer;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.OrderTrackingAdapter;
import com.example.pharmacyandroidapplication.adapters.StockInAdapter;
import com.example.pharmacyandroidapplication.models.Order;
import com.example.pharmacyandroidapplication.models.StockIn;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class OrdersTrackingActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        GridView listOrders= findViewById(R.id.list_orders);
        ArrayList<Order> OrdersArrayList = new ArrayList<Order>();
        String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        OrdersArrayList.add(new Order("FDSFGFSG",userID, 100000, true, true,"","496, đường 833", new Date(2024, 4, 2), 10));

        OrderTrackingAdapter adapter = new OrderTrackingAdapter(this, OrdersArrayList);
        listOrders.setAdapter(adapter);
    }
}
