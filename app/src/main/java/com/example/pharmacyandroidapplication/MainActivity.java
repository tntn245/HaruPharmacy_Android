package com.example.pharmacyandroidapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);
        GridView productGV= findViewById(R.id.list_item);
        ArrayList<Product> ProductArrayList = new ArrayList<Product>();

        ProductArrayList.add(new Product("DSA", R.drawable.ic_launcher_foreground));
        ProductArrayList.add(new Product("JAVA", R.drawable.ic_launcher_foreground));
        ProductArrayList.add(new Product("C++", R.drawable.ic_launcher_foreground));
        ProductArrayList.add(new Product("Python", R.drawable.ic_launcher_foreground));
        ProductArrayList.add(new Product("Javascript", R.drawable.ic_launcher_foreground));
        ProductArrayList.add(new Product("DSA", R.drawable.ic_launcher_foreground));

        ProductGVAdapter adapter = new ProductGVAdapter(this, ProductArrayList);
        productGV.setAdapter(adapter);
    }
}