package com.example.pharmacyandroidapplication.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.customer.OrderDetailsActivity;
import com.example.pharmacyandroidapplication.activities.customer.OrdersTrackingActivity;
import com.example.pharmacyandroidapplication.adapters.CategoryManagementAdapter;
import com.example.pharmacyandroidapplication.adapters.HomeCategoryAdapter;
import com.example.pharmacyandroidapplication.models.Category;
import com.example.pharmacyandroidapplication.models.Order;
import com.example.pharmacyandroidapplication.models.Product;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DataTruncation;
import java.util.ArrayList;

public class CategoryManagementActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference categoryRef = database.getReference("category");
    private ArrayList<Category> categoryArrayList;
    private CategoryManagementAdapter categoryManagementAdapter;
    private EditText searchBar;
    private ImageButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_management);
        GridView list_category = findViewById(R.id.list_category);
        categoryArrayList = new ArrayList<Category>();
        //Load Category
        retrieveCategory();
        categoryManagementAdapter = new CategoryManagementAdapter(CategoryManagementActivity.this,categoryArrayList);
        list_category.setAdapter(categoryManagementAdapter);

        searchBar = findViewById(R.id.edit_text_search);
        searchButton = findViewById(R.id.image_search);

        // Set up the search functionality
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchBar.getText().toString().trim();
                // Perform the search operation
                filterProductsByName(searchQuery, list_category);
            }
        });


        categoryManagementAdapter.setOnButtonClickListener(new HomeCategoryAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(int position, String text) {
                // Get the clicked category item
                Category item = categoryArrayList.get(position);
                // Create the intent with correct context and target activity
                Intent intent = new Intent(CategoryManagementActivity.this, EditCategoryActivity.class);
                // Pass category information to the next activity
                intent.putExtra("selectedCategoryId", item.getId());
                intent.putExtra("selectedCategoryName", item.getName());
                intent.putExtra("selectedCategoryStatus", item.isFlag_valid());
                startActivity(intent);
                finish();
            }
        });
        ImageView btn_add = findViewById(R.id.btn_add_category);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryManagementActivity.this, AddCategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void retrieveCategory()
    {
        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Category> validCategories = new ArrayList<>();
                ArrayList<Category> inValidCategories = new ArrayList<>();
                categoryArrayList.clear();
                String categoryName, categoryId;
                boolean flag_valid;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    flag_valid = dataSnapshot.child("flag_valid").getValue(Boolean.class);
                    categoryName = dataSnapshot.child("name").getValue(String.class);
                    categoryId = dataSnapshot.child("id").getValue(String.class);
                    Category category = new Category(categoryId, categoryName, flag_valid);
//                    if(flag_valid) validCategories.add(category);
//                    else inValidCategories.add(category);
//                    validCategories.addAll(inValidCategories);
                    categoryArrayList.add(category);
                }
                categoryManagementAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void filterProductsByName(String searchQuery, GridView list_category) {
        // Filter the product list based on the search query
        // and update the CategoryManagementAdapter accordingly
        ArrayList<Category> filteredCategory = new ArrayList<>();
        for (Category category : categoryArrayList) {
            if (category.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                filteredCategory.add(category);
            }
        }
        CategoryManagementAdapter newCategoryManagementAdapter = new CategoryManagementAdapter(CategoryManagementActivity.this, filteredCategory);
        list_category.setAdapter(newCategoryManagementAdapter);
    }
}
