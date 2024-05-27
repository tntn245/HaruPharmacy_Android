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
import com.example.pharmacyandroidapplication.models.Inventory;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.StockIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WarehouseInventoryActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<Inventory> InventoryArrayList;
    GridView productGV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_inventory);

        productGV= findViewById(R.id.list_inventory);
        InventoryArrayList = new ArrayList<Inventory>();

        loadProductFromFirebase();

        // Đặt sự kiện click cho mỗi item trong GridView
        productGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị của item được click
                Inventory item = InventoryArrayList.get(position);

                // Truyền giá trị của item qua layout tiếp theo để hiển thị
                Intent intent = new Intent(WarehouseInventoryActivity.this, WarehouseInventoryDetailsActivity.class);
                intent.putExtra("selectedProductID", item.getId());
                intent.putExtra("selectedProductImg", item.getImg());
                intent.putExtra("selectedProductName", item.getName());
                intent.putExtra("selectedProductUnit", item.getUnit());
                intent.putExtra("selectedProductInventoryQuantity", item.getInventory_quantity());
                startActivity(intent);
            }
        });
    }

    private void loadProductFromFirebase() {
        DatabaseReference databaseInventory = database.getReference("inventory");

        databaseInventory.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot inventorySnapshot) {
                if (inventorySnapshot.exists()) {
                    for (DataSnapshot productSnapshot : inventorySnapshot.getChildren()) {
                        String productID = productSnapshot.getKey();

                        DatabaseReference databaseProduct = database.getReference("product").child(productID);
                        databaseProduct.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String productName = dataSnapshot.child("name").getValue(String.class);
                                String img = dataSnapshot.child("img").getValue(String.class);

                                for (DataSnapshot unitSnapshot : productSnapshot.getChildren()) {
                                    String unitName = unitSnapshot.getKey();
                                    Integer inventoryQuantity = unitSnapshot.child("inventory_quantity").getValue(Integer.class);

                                    Inventory item = new Inventory(productID,productName, unitName, img,inventoryQuantity);
                                    InventoryArrayList.add(item);
                                }

                                ProductInventoryAdapter adapter = new ProductInventoryAdapter(WarehouseInventoryActivity.this, InventoryArrayList);
                                productGV.setAdapter(adapter);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Xử lý khi có lỗi xảy ra
                            }
                        });


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });


    }
}