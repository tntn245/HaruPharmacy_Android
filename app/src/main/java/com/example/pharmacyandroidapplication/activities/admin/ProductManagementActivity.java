package com.example.pharmacyandroidapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ProductGVAdapter;
import com.example.pharmacyandroidapplication.models.Product;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class ProductManagementActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);
        GridView productGV= findViewById(R.id.list_item);
        ArrayList<Product> ProductArrayList = new ArrayList<Product>();

        ProductArrayList.add(new Product("Chromium", 100000, R.drawable.pro1));
        ProductArrayList.add(new Product("Omega3",100000, R.drawable.pro2));
        ProductArrayList.add(new Product("Thyroid-Pro Formula",100000, R.drawable.pro3));
        ProductArrayList.add(new Product("Magnesium",100000, R.drawable.ic_launcher_foreground));
        ProductArrayList.add(new Product("CocoaVia",100000, R.drawable.ic_launcher_foreground));
        ProductArrayList.add(new Product("Whey",100000, R.drawable.ic_launcher_foreground));
        ProductArrayList.add(new Product("A",100000, R.drawable.ic_launcher_foreground));
        ProductArrayList.add(new Product("B",100000, R.drawable.ic_launcher_foreground));
        ProductArrayList.add(new Product("C",100000, R.drawable.ic_launcher_foreground));
        ProductArrayList.add(new Product("D",100000, R.drawable.ic_launcher_foreground));

        ProductGVAdapter adapter = new ProductGVAdapter(this, ProductArrayList);
        productGV.setAdapter(adapter);
    }

}
