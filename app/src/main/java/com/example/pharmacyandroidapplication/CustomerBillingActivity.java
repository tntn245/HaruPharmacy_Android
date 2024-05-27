package com.example.pharmacyandroidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.activities.customer.CartActivity;
import com.example.pharmacyandroidapplication.activities.customer.CustomerHomepageActivity;
import com.example.pharmacyandroidapplication.adapters.ItemPayAdapter;
import com.example.pharmacyandroidapplication.models.ItemPay;

import java.util.List;

public class CustomerBillingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemPayAdapter adapter;
    private List<ItemPay> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_billing);

        TextView order_id = findViewById(R.id.id_order);
        TextView order_date = findViewById(R.id.date_order);
        TextView receiver = findViewById(R.id.name_receiver);
        TextView phone = findViewById(R.id.phone);
        TextView address = findViewById(R.id.address);
        TextView sumhang = findViewById(R.id.sumhang);

        order_id.setText(getIntent().getStringExtra("id_order"));
        order_date.setText(getIntent().getStringExtra("order_date"));
        receiver.setText(getIntent().getStringExtra("name"));
        phone.setText(getIntent().getStringExtra("phone"));
        address.setText(getIntent().getStringExtra("address"));
        sumhang.setText(getIntent().getStringExtra("sumhang"));
        recyclerView = findViewById(R.id.recycler_view_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = getIntent().getParcelableArrayListExtra("selectedItems");
        adapter = new ItemPayAdapter(this, itemList);
        recyclerView.setAdapter(adapter);
        Button back_cart = findViewById(R.id.btn_backcart);
        Button back_home = findViewById(R.id.btn_continuebuy);
        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerBillingActivity.this, CustomerHomepageActivity.class);
                startActivity(intent);
                finish();
            }
        });
        back_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerBillingActivity.this, CartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}