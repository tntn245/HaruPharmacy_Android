package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.admin.WarehouseInventoryActivity;
import com.example.pharmacyandroidapplication.activities.admin.WarehouseInventoryDetailsActivity;
import com.example.pharmacyandroidapplication.adapters.OrderTrackingAdapter;
import com.example.pharmacyandroidapplication.adapters.StockInAdapter;
import com.example.pharmacyandroidapplication.models.Order;
import com.example.pharmacyandroidapplication.models.Product;
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
}
