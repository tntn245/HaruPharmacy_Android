package com.example.pharmacyandroidapplication.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.customer.CustomerHomepageActivity;
import com.example.pharmacyandroidapplication.adapters.HomeProductAdapter;
import com.example.pharmacyandroidapplication.adapters.ProductGVAdapter;
import com.example.pharmacyandroidapplication.adapters.ProductInventoryAdapter;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.StockIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WarehouseInventoryActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<Product> ProductArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_inventory);

        GridView productGV= findViewById(R.id.list_inventory);
        ProductArrayList = new ArrayList<Product>();

        loadProductFromFirebase();

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

    private void loadProductFromFirebase() {
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
                    String id_category = snapshot.child("id_category").getValue(String.class);
                    String unit = snapshot.child("unit").getValue(String.class);
                    String inventory_quantity = snapshot.child("uses").getValue(String.class);
                    String uses = snapshot.child("uses").getValue(String.class);
                    String ingredient = snapshot.child("ingredient").getValue(String.class);
                    Boolean prescription = Boolean.TRUE.equals(snapshot.child("prescription").getValue(Boolean.class));

                    Product product = new Product(id,id_category,productImg, productName,0,productPrice,unit,uses, ingredient, prescription);
                    // Sau đó, thêm sản phẩm vào danh sách productList
                    ProductArrayList.add(product);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}