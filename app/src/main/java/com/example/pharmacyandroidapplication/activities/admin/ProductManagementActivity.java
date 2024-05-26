package com.example.pharmacyandroidapplication.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.customer.CustomerHomepageActivity;
import com.example.pharmacyandroidapplication.activities.customer.ProductDetailsActivity;
import com.example.pharmacyandroidapplication.adapters.HomeCategoryAdapter;
import com.example.pharmacyandroidapplication.adapters.ProductGVAdapter;
import com.example.pharmacyandroidapplication.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductManagementActivity extends AppCompatActivity {
    private ArrayList<Product> productArrayList;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference productRef = database.getReference("product");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);
        GridView productGV= findViewById(R.id.list_item);
        productArrayList = new ArrayList<Product>();

        productRef.addValueEventListener(new ValueEventListener() {
            String id, id_category, unit, name, img, uses, ingredient;
            int price, inventory_quantity;
            boolean flag_valid, prescription;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    id = dataSnapshot.getKey().toString();
                    id_category = dataSnapshot.child("id_category").getValue(String.class);
                    name = dataSnapshot.child("name").getValue(String.class);
                    img = dataSnapshot.child("img").getValue(String.class);
                    price = dataSnapshot.child("price").getValue(Integer.class);
                    inventory_quantity = Integer.valueOf(dataSnapshot.child("inventory_quantity").getValue().toString());
                    unit = dataSnapshot.child("unit").getValue(String.class);
                    uses = dataSnapshot.child("uses").getValue(String.class);
                    ingredient = dataSnapshot.child("ingredient").getValue(String.class);
                    flag_valid = dataSnapshot.child("flag_valid").getValue(Boolean.class);
                    prescription  = dataSnapshot.child("prescription").getValue(Boolean.class);

                    Product product = new Product(id, id_category, img, name, inventory_quantity ,price, unit, uses, ingredient, flag_valid, prescription);
                    productArrayList.add(product);
                }
                ProductGVAdapter adapter = new ProductGVAdapter(ProductManagementActivity.this, productArrayList);
                productGV.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        productGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị của item được click
                Product productDetails = productArrayList.get(position);

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
