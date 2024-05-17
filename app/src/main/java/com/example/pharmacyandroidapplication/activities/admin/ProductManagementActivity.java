package com.example.pharmacyandroidapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.customer.CustomerHomepageActivity;
import com.example.pharmacyandroidapplication.activities.customer.ProductDetailsActivity;
import com.example.pharmacyandroidapplication.adapters.HomeCategoryAdapter;
import com.example.pharmacyandroidapplication.adapters.ProductGVAdapter;
import com.example.pharmacyandroidapplication.models.Product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductManagementActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);
        GridView productGV= findViewById(R.id.list_item);
        ArrayList<Product> ProductArrayList = new ArrayList<Product>();

        ProductArrayList.add(new Product("","","","Chromium", 0, 100000));
        ProductArrayList.add(new Product("","","","Omega3", 0,100000));
        ProductArrayList.add(new Product("","","","Thyroid-Pro Formula", 0,100000));
        ProductArrayList.add(new Product("","","","Magnesium", 0,100000));
        ProductArrayList.add(new Product("","","","CocoaVia", 0,100000));
        ProductArrayList.add(new Product("","","","Whey", 0,100000));
        ProductArrayList.add(new Product("","","","A", 0,100000));
        ProductArrayList.add(new Product("","","","B", 0,100000));
        ProductArrayList.add(new Product("","","","C", 0,100000));
        ProductArrayList.add(new Product("","","","D", 0,100000));

        ProductGVAdapter adapter = new ProductGVAdapter(this, ProductArrayList);
        productGV.setAdapter(adapter);

        productGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị của item được click
                Product productDetails = ProductArrayList.get(position);

                // Truyền giá trị của item qua layout tiếp theo để hiển thị
                Intent intent = new Intent(ProductManagementActivity.this, EditProductActivity.class);
//                intent.putExtra("product_img", productDetails.getProductImg());
//                intent.putExtra("product_name", productDetails.getProductName());
//                intent.putExtra("product_price", productDetails.getProductPrice());
                startActivity(intent);
            }
        });

        ImageView btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductManagementActivity.this, AddProductActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
