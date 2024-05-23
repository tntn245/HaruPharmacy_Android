package com.example.pharmacyandroidapplication.activities.customer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.HomeProductAdapter;
import com.example.pharmacyandroidapplication.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoppingPageActivity extends AppCompatActivity {
    ArrayList<Product> ProductArrayList;
    GridView shoppingGV;
    ImageButton imageSearch;
    EditText editTextSearch;
    String categoryID = "";
    String categoryName;
    String searchStr = "";
    FirebaseDatabase database;
    HomeProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_page);
        shoppingGV = findViewById(R.id.shoppingGV);
        ProductArrayList = new ArrayList<Product>();

        categoryID = getIntent().getExtras().getString("categoryID");
        categoryName = getIntent().getExtras().getString("categoryName");
        searchStr = getIntent().getExtras().getString("searchQuery");
        database = FirebaseDatabase.getInstance();

        loadDataFromFirebase();

        TextView header_shopping = findViewById(R.id.header_shopping);
        if(!categoryName.equals("")) {
            header_shopping.setText(categoryName);
        }

        editTextSearch = findViewById(R.id.edit_text_search_shopping_page);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Thực hiện tìm kiếm khi text thay đổi
                searchProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
    private void searchProducts(String searchQuery) {
        // Tạo một danh sách mới để chứa các sản phẩm thỏa điều kiện tìm kiếm
        ArrayList<Product> searchResults = new ArrayList<Product>();

        // Lặp qua từng sản phẩm trong ArrayList 'product'
        for (Product p : ProductArrayList) {
            // Kiểm tra xem sản phẩm có chứa giá trị tìm kiếm hay không
            if (p.getName().toLowerCase().contains(searchQuery.toLowerCase())
                    || Integer.toString(p.getPrice()).contains(searchQuery)
                    || p.getUses().toLowerCase().contains(searchQuery.toLowerCase())
                    || p.getIngredient().toLowerCase().contains(searchQuery.toLowerCase())) {
                searchResults.add(p);
            }
        }

        // Sau khi lấy được danh sách sản phẩm, tạo adapter và gán cho GridView
        productAdapter = new HomeProductAdapter(ShoppingPageActivity.this, searchResults);
        shoppingGV.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }
    private void loadDataFromFirebase() {
        DatabaseReference productsRef = database.getReference("product");
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProductArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String productName = snapshot.child("name").getValue(String.class);
                    int productPrice = snapshot.child("price").getValue(Integer.class);
                    String productImg = snapshot.child("img").getValue(String.class);
                    String id = snapshot.child("id").getValue(String.class);
                    String id_category = snapshot.child("id_category").getValue(String.class);
                    String unit = snapshot.child("unit").getValue(String.class);
                    String uses = snapshot.child("uses").getValue(String.class);
                    String ingredient = snapshot.child("ingredient").getValue(String.class);

                    Product product = new Product(id, id_category, productImg, productName, 0, productPrice, unit, uses, ingredient);

                    if (categoryID.equals("")) { // Nếu không chọn lsp
                        if(searchStr.equals("")){ // Nếu không tìm kiếm => trường hợp chọn "mua sắm"
                            ProductArrayList.add(product);
                        }
                        else{ //Nếu tìm kiếm
                            // Kiểm tra xem các trường có chứa chuỗi tìm kiếm không
                            if (productName.toLowerCase().contains(searchStr.toLowerCase())
                                    || Integer.toString(productPrice).contains(searchStr)
                                    || uses.toLowerCase().contains(searchStr.toLowerCase())
                                    || ingredient.toLowerCase().contains(searchStr.toLowerCase())) {
                                ProductArrayList.add(product);
                            }
                        }
                    } else if (categoryID.equals(id_category)) {
                        ProductArrayList.add(product);
                    }
                }

                // Sau khi lấy được danh sách sản phẩm, tạo adapter và gán cho GridView
                productAdapter = new HomeProductAdapter(ShoppingPageActivity.this, ProductArrayList);
                shoppingGV.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
