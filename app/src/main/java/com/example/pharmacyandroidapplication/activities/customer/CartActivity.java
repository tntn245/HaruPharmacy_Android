package com.example.pharmacyandroidapplication.activities.customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.MainActivity;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.LoginActivity;
import com.example.pharmacyandroidapplication.adapters.CartAdapter;
import com.example.pharmacyandroidapplication.models.Cart;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private ArrayList<Cart> carts;
private RecyclerView mrecycleview;
private CartAdapter mcartadapter;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cart);

    mrecycleview =findViewById(R.id.recyclercart);
    carts = new ArrayList<>();
    createCartList();
    mcartadapter = new CartAdapter(this,carts);
    mrecycleview.setAdapter(mcartadapter);
    mrecycleview.setLayoutManager(new LinearLayoutManager(this));

    Button btnBuy = findViewById(R.id.btnBuy);
    btnBuy.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
            startActivity(intent);
        }
    });
}
    private void createCartList() {
        carts.add(new Cart( "101","101","Thực phẩm chữa lành",100000,1));
        carts.add(new Cart( "101","101","Thực phẩm chữa rách",100000,1));
        carts.add(new Cart( "101","101","Thực phẩm chữa què",100000,1));
        carts.add(new Cart( "101","101","Thực phẩm chữa tổn thương",100000,1));
    }
}
