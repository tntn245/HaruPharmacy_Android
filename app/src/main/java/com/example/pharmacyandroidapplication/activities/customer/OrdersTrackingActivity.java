package com.example.pharmacyandroidapplication.activities.customer;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.StockInAdapter;
import com.example.pharmacyandroidapplication.models.StockIn;

import java.util.ArrayList;
import java.util.Date;

public class OrdersTrackingActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        GridView listOrders= findViewById(R.id.list_orders);
        ArrayList<StockIn> OrdersArrayList = new ArrayList<StockIn>();

        OrdersArrayList.add(new StockIn("FDSFGFSG",new Date(2024, 4, 2), 100000));
        OrdersArrayList.add(new StockIn("MEFGFSGG",new Date(2024, 4, 1),100000));
        OrdersArrayList.add(new StockIn("ODSAXVSG",new Date(2024, 3, 29),100000));
        OrdersArrayList.add(new StockIn("EKUGFSYG",new Date(2024, 3, 18),100000));

        StockInAdapter adapter = new StockInAdapter(this, OrdersArrayList);
        listOrders.setAdapter(adapter);
    }
}
