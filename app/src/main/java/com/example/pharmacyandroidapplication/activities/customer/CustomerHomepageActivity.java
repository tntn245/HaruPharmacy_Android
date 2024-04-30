package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.ChatActivity;
import com.example.pharmacyandroidapplication.adapters.HomeCategoryAdapter;
import com.example.pharmacyandroidapplication.adapters.HomeProductAdapter;
import com.example.pharmacyandroidapplication.models.Category;
import com.example.pharmacyandroidapplication.models.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class CustomerHomepageActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_homepage);

        // Gridview Category
        GridView categoryGV= findViewById(R.id.rcv_category);
        ArrayList<Category> CategoryArrayList = new ArrayList<Category>();

        CategoryArrayList.add(new Category("Cải thiện giấc ngủ"));
        CategoryArrayList.add(new Category("Hỗ trợ gan"));
        CategoryArrayList.add(new Category("Hỗ trợ tim mạch"));

        HomeCategoryAdapter categoryAdapter = new HomeCategoryAdapter(this, CategoryArrayList);
        categoryGV.setAdapter(categoryAdapter);

        // Gridview Product
        GridView productGV= findViewById(R.id.rcv_shopping);
        ArrayList<Product> ProductArrayList = new ArrayList<Product>();

        ProductArrayList.add(new Product("Chromium", 100000, R.drawable.pro1));
        ProductArrayList.add(new Product("Omega3",200000, R.drawable.pro2));
        ProductArrayList.add(new Product("Thyroid-Pro Formula",150000, R.drawable.pro3));

        HomeProductAdapter productAdapter = new HomeProductAdapter(this, ProductArrayList);
        productGV.setAdapter(productAdapter);

        // Click Product
        productGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị của item được click
                Product productDetails = ProductArrayList.get(position);

                // Truyền giá trị của item qua layout tiếp theo để hiển thị
                Intent intent = new Intent(CustomerHomepageActivity.this, ProductDetailsActivity.class);
                intent.putExtra("product_img", productDetails.getProductImg());
                intent.putExtra("product_name", productDetails.getProductName());
                intent.putExtra("product_price", productDetails.getProductPrice());
                startActivity(intent);
            }
        });

        // Click Bottom nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    // Xử lý khi người dùng chọn trang chủ
                    return true;
                } else if (itemId == R.id.support) {
                    // Xử lý khi người dùng chọn trang tư vấn
                    Intent supportIntent = new Intent(CustomerHomepageActivity.this, ChatActivity.class);
                    startActivity(supportIntent);
                    return true;
                } else if (itemId == R.id.cart) {
                    // Xử lý khi người dùng chọn trang giỏ hàng
                    Intent CartIntent = new Intent(CustomerHomepageActivity.this, CartActivity.class);
                    startActivity(CartIntent);
                    return true;
                } else if (itemId == R.id.profile) {
                    // Xử lý khi người dùng chọn trang tài khoản
                    Intent CartIntent = new Intent(CustomerHomepageActivity.this, UserActivity.class);
                    startActivity(CartIntent);
                    return true;
                }
                return false;
            }
        });

    }
}

