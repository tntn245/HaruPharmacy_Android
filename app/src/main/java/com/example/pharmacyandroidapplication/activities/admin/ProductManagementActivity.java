package com.example.pharmacyandroidapplication.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.CategoryManagementAdapter;
import com.example.pharmacyandroidapplication.adapters.ProductGVAdapter;
import com.example.pharmacyandroidapplication.listeners.OnProductClickListener;
import com.example.pharmacyandroidapplication.models.Category;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductManagementActivity extends AppCompatActivity implements OnProductClickListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<Product> ProductArrayList;
    GridView productGV;
    private EditText searchBar;
    private ImageButton searchButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);
        productGV = findViewById(R.id.list_item);
        ProductArrayList = new ArrayList<Product>();

        loadProductFromFirebase();
        searchBar = findViewById(R.id.edit_text_search);
        searchButton = findViewById(R.id.image_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchBar.getText().toString().trim();
                // Perform the search operation
                filterProductsByName(searchQuery, productGV);
            }
        });
        ImageView btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductManagementActivity.this, AddNewProductActivity.class);
                startActivity(intent);
            }
        });
        ImageView btn_back = findViewById(R.id.back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductManagementActivity.this, AdminHomepageActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onProductClick(String productId) {
        Intent intent = new Intent(ProductManagementActivity.this, EditProductActivity.class);
        intent.putExtra("productID",productId);
        startActivity(intent);
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

                    Product product = new Product(id, id_category, productImg, productName, 0, productPrice, unit, uses, ingredient, prescription);
                    // Sau đó, thêm sản phẩm vào danh sách productList
                    ProductArrayList.add(product);
                }
//                Toast.makeText(ProductManagementActivity.this,ProductArrayList.size() , Toast.LENGTH_SHORT).show();

                ProductGVAdapter adapter = new ProductGVAdapter(ProductManagementActivity.this, ProductArrayList);
                adapter.setOnProductClickListener(ProductManagementActivity.this);
                productGV.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void filterProductsByName(String searchQuery, GridView list_product) {
        // Filter the product list based on the search query
        // and update the CategoryManagementAdapter accordingly
        ArrayList<Product> filteredProduct = new ArrayList<>();
        for (Product product : ProductArrayList) {
            if (product.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                filteredProduct.add(product);
            }
        }
        ProductGVAdapter newProductGVAdapter = new ProductGVAdapter(ProductManagementActivity.this, filteredProduct);
        list_product.setAdapter(newProductGVAdapter);
    }
}
