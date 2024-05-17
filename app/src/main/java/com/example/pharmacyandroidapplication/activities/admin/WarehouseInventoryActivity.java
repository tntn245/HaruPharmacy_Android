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

        ProductArrayList.add(new Product("","","link img","Chromium", 100000));
        ProductArrayList.add(new Product("","","link img","Omega3",100000));
        ProductArrayList.add(new Product("","","link img","Thyroid-Pro Formula",100000));
        ProductArrayList.add(new Product("","","link img","Magnesium",100000));
        ProductArrayList.add(new Product("","","link img","CocoaVia",100000));
        ProductArrayList.add(new Product("","","link img","Whey",100000));

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
                intent.putExtra("selectedProductImg", item.getImg());
                intent.putExtra("selectedProductName", item.getName());
                intent.putExtra("selectedProductInventoryQuantity", item.getInventory_quantity());
                startActivity(intent);
            }
        });
    }
}