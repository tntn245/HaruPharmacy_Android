package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.HomeProductAdapter;
import com.example.pharmacyandroidapplication.adapters.OrderDetailsAdapter;
import com.example.pharmacyandroidapplication.adapters.StockInAdapter;
import com.example.pharmacyandroidapplication.models.DateFormat;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.StockIn;

import java.util.ArrayList;
import java.util.Date;

public class OrderDetailsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        // Lấy Intent từ Activity
        Intent intent = getIntent();
        String orderID = intent.getStringExtra("selectedOrderID");
        Date orderDate = (Date) intent.getSerializableExtra("selectedOrderDate");
        int orderTotalPayment = intent.getIntExtra("selectedTotalPayment", 0);

        // Hiển thị thông tin
        TextView orderID_tv = findViewById(R.id.order_id);
        orderID_tv.setText(orderID);

        TextView orderDate_tv = findViewById(R.id.order_date);
        assert orderDate != null;
        DateFormat dateFormat = new DateFormat(orderDate);
        orderDate_tv.setText(dateFormat.formatDateToString());

        TextView orderTotalPayment_tv = findViewById(R.id.order_total_payment);
        orderTotalPayment_tv.setText(Integer.toString(orderTotalPayment));

        // Hiển thị item trong gridview (load từ dtb dựa trên id của order)
        GridView ProductGV= findViewById(R.id.list_products);
        ArrayList<Product> ProductArrayList = new ArrayList<Product>();

        ProductArrayList.add(new Product("","","", "Omega3", 1,100000 ));

        OrderDetailsAdapter productAdapter = new OrderDetailsAdapter(this, ProductArrayList);
        ProductGV.setAdapter(productAdapter);
    }
}
