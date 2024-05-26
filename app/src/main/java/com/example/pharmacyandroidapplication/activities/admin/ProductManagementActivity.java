package com.example.pharmacyandroidapplication.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pharmacyandroidapplication.R;
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

import java.util.ArrayList;

public class ProductManagementActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<Product> ProductArrayList;
    GridView productGV;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);
        productGV= findViewById(R.id.list_item);
        ProductArrayList = new ArrayList<Product>();

        loadProductFromFirebase();

        productGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị của item được click
                Product productDetails = ProductArrayList.get(position);

                // Truyền giá trị của item qua layout tiếp theo để hiển thị
                Intent intent = new Intent(ProductManagementActivity.this, EditProductActivityOld.class);
//                intent.putExtra("productID", productDetails.getId());
                startActivity(intent);
            }
        });

        ImageView btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductManagementActivity.this, EditProductActivity.class);
                startActivity(intent);
//                finish();
            }
        });

    }

    private void loadProductFromFirebase() {
        DatabaseReference productsRef = database.getReference("product");
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
//                Toast.makeText(ProductManagementActivity.this,ProductArrayList.size() , Toast.LENGTH_SHORT).show();

                ProductGVAdapter adapter = new ProductGVAdapter(ProductManagementActivity.this, ProductArrayList);
                productGV.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
