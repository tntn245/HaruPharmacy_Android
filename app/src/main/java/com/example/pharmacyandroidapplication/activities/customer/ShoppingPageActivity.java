package com.example.pharmacyandroidapplication.activities.customer;

import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.HomeProductAdapter;
import com.example.pharmacyandroidapplication.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoppingPageActivity extends AppCompatActivity {
    ArrayList<Product> ProductArrayList;
    GridView shoppingGV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_page);

        shoppingGV = findViewById(R.id.shoppingGV);
        loadDataFromFirebase();
    }
    private void loadDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference productsRef = database.getReference("product");
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProductArrayList = new ArrayList<Product>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy dữ liệu từ snapshot và tạo đối tượng Product
                    String productName = snapshot.child("name").getValue(String.class);
                    int productPrice = snapshot.child("price").getValue(Integer.class);
                    String productImg = snapshot.child("img").getValue(String.class);
                    String id = snapshot.child("id").getValue(String.class);
                    Product product = new Product(id,"",productImg, productName,0,productPrice);
                    // Sau đó, thêm sản phẩm vào danh sách productList
                    ProductArrayList.add(product);
                }

                // Sau khi lấy được danh sách sản phẩm, tạo adapter và gán cho GridView
                HomeProductAdapter productAdapter = new HomeProductAdapter(ShoppingPageActivity.this, ProductArrayList);
                shoppingGV.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
