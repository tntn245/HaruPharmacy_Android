package com.example.pharmacyandroidapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ProductGVAdapter;
import com.example.pharmacyandroidapplication.adapters.ProductInventoryAdapter;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.StockIn;

import java.util.ArrayList;

public class WarehouseInventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_inventory);

        GridView productGV= findViewById(R.id.list_inventory);
        ArrayList<Product> ProductArrayList = new ArrayList<Product>();

        ProductArrayList.add(new Product("Chromium", 100000, 10, R.drawable.pro1));
        ProductArrayList.add(new Product("Omega3",100000,20, R.drawable.pro2));
        ProductArrayList.add(new Product("Thyroid-Pro Formula",100000, 20, R.drawable.pro3));
        ProductArrayList.add(new Product("Magnesium",100000,10, R.drawable.ic_launcher_foreground));
        ProductArrayList.add(new Product("CocoaVia",100000,10, R.drawable.ic_launcher_foreground));
        ProductArrayList.add(new Product("Whey",100000, 50,R.drawable.ic_launcher_foreground));

        ProductInventoryAdapter adapter = new ProductInventoryAdapter(this, ProductArrayList);
        productGV.setAdapter(adapter);

        // Đặt sự kiện click cho mỗi item trong GridView
        productGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị của item được click
                Product item = ProductArrayList.get(position);

                // Truyền giá trị của item qua layout tiếp theo để hiển thị
                Intent intent = new Intent(WarehouseInventoryActivity.this, WarehouseInventoryDetailsActivity.class);
                intent.putExtra("selectedProductID", item.getId());
                intent.putExtra("selectedProductImg", item.getProductImg());
                intent.putExtra("selectedProductName", item.getProductName());
                intent.putExtra("selectedProductInventoryQuantity", item.getInventory_quantity());
                startActivity(intent);
            }
        });
    }
}