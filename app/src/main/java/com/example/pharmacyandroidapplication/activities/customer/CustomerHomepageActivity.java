package com.example.pharmacyandroidapplication.activities.customer;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.ChatActivity;
import com.example.pharmacyandroidapplication.activities.LoginActivity;
import com.example.pharmacyandroidapplication.adapters.HomeCategoryAdapter;
import com.example.pharmacyandroidapplication.adapters.HomeProductAdapter;
import com.example.pharmacyandroidapplication.models.Category;
import com.example.pharmacyandroidapplication.models.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerHomepageActivity extends AppCompatActivity {
    GridView categoryGV;
    GridView productGV;
    ArrayList<Product> ProductArrayList;
    String userID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_homepage);

        userID = getIntent().getExtras().getString("userID");

        ScrollView scrollView = findViewById(R.id.scroll_view);
        CardView searchBar = findViewById(R.id.search_bar);
        ImageView searchIcon = findViewById(R.id.ic_search);
        categoryGV = findViewById(R.id.rcv_category);
        productGV = findViewById(R.id.rcv_shopping);

        openDrawer();
        closeDrawer();
        loadDataFromFirebase();


        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
//                    searchBar.setVisibility(View.GONE);
                    searchIcon.setVisibility(View.VISIBLE);

                    // Lấy margin của thanh search
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) searchBar.getLayoutParams();
                    int startMarginTop = params.topMargin;
                    int startMarginBottom = params.bottomMargin;
                    int startHeight = searchBar.getHeight();

                    // Tạo một ValueAnimator để thay đổi chiều cao của thanh search
                    ValueAnimator animator = ValueAnimator.ofInt(startHeight, 0); // startHeight là chiều cao ban đầu, endHeight là chiều cao cuối cùng sau khi thu nhỏ
                    animator.setDuration(500); // Thiết lập thời gian thực hiện animation

                    // Listener để cập nhật chiều cao của thanh search và layout cha
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            // Lấy giá trị chiều cao hiện tại từ ValueAnimator
                            int animatedValue = (int) animation.getAnimatedValue();

                            // Cập nhật chiều cao của thanh search
                            searchBar.getLayoutParams().height = animatedValue;

                            // Cập nhật margin của thanh search
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) searchBar.getLayoutParams();
                            params.topMargin = (int) (startMarginTop * ((float) animatedValue / startHeight));
                            params.bottomMargin = (int) (startMarginBottom * ((float) animatedValue / startHeight));
                            searchBar.setLayoutParams(params);

                            searchBar.requestLayout();
                        }
                    });
                    animator.start();
                } else if (scrollY < oldScrollY) {
//                    searchBar.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
//                    searchBar.setVisibility(View.VISIBLE);
//                    searchIcon.setVisibility(View.INVISIBLE);
                }
            }
        });


        // Gridview Category
        ArrayList<Category> CategoryArrayList = new ArrayList<Category>();

        CategoryArrayList.add(new Category("Cải thiện giấc ngủ"));
        CategoryArrayList.add(new Category("Hỗ trợ gan"));
        CategoryArrayList.add(new Category("Hỗ trợ tim mạch"));

        HomeCategoryAdapter categoryAdapter = new HomeCategoryAdapter(this, CategoryArrayList);
        categoryGV.setAdapter(categoryAdapter);

        categoryAdapter.setOnButtonClickListener(new HomeCategoryAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(int position) {
                // Xử lý sự kiện click tại vị trí position
                Toast.makeText(getApplicationContext(), "Button clicked at position: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        // Click Product
        productGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị của item được click
                Product productDetails = ProductArrayList.get(position);
                Toast.makeText(getApplicationContext(), "Item clicked at position: ", Toast.LENGTH_SHORT).show();

                // Truyền giá trị của item qua layout tiếp theo để hiển thị
                Intent intent = new Intent(CustomerHomepageActivity.this, ProductDetailsActivity.class);
                intent.putExtra("product_img", productDetails.getProductImg());
                intent.putExtra("product_name", productDetails.getProductName());
                intent.putExtra("product_price", productDetails.getProductPrice());
                startActivity(intent);
            }
        });

        // Click Bottom nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    // Xử lý khi người dùng chọn trang chủ
                    Intent supportIntent = new Intent(CustomerHomepageActivity.this, CustomerHomepageActivity.class);
                    startActivity(supportIntent);
                    finish();
                    return true;
                } else if (itemId == R.id.support) {
                    // Xử lý khi người dùng chọn trang tư vấn
                    Intent supportIntent = new Intent(CustomerHomepageActivity.this, ChatActivity.class);
                    startActivity(supportIntent);
                    return true;
                } else if (itemId == R.id.cart) {
                    // Xử lý khi người dùng chọn trang giỏ hàng
                    Intent CartIntent = new Intent(CustomerHomepageActivity.this, CartActivity.class);
                    startActivity(CartIntent);
                    return true;
                } else if (itemId == R.id.profile) {
                    // Xử lý khi người dùng chọn trang tài khoản
                    Intent intent = new Intent(CustomerHomepageActivity.this, UserActivity.class);
                    intent.putExtra("userID", userID);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

    }

    private void loadDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference productsRef = database.getReference("product");
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProductArrayList = new ArrayList<Product>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy dữ liệu từ snapshot và tạo đối tượng Product
                    String productName = snapshot.child("name").getValue(String.class);
                    int productPrice = snapshot.child("price").getValue(Integer.class);
                    int productImg = R.drawable.pro1;
//                    int productImg = snapshot.child("image").getValue(Integer.class);

                    Product product = new Product(productName, productPrice, productImg);

                    // Sau đó, thêm sản phẩm vào danh sách productList
                    ProductArrayList.add(product);
                }

                // Sau khi lấy được danh sách sản phẩm, tạo adapter và gán cho GridView
                HomeProductAdapter productAdapter = new HomeProductAdapter(CustomerHomepageActivity.this, ProductArrayList);
                productGV.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void openDrawer() {
        ImageView openDrawerButton = findViewById(R.id.icon_drawer_menu);
        openDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(GravityCompat.START); // Mở Drawer từ trái sang
            }
        });

    }

    private void closeDrawer() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Xử lý các sự kiện khi người dùng chọn các mục trong Drawer Navigation
                int id = item.getItemId();

                if (id == R.id.nav_home_drawer) {
                    Intent intent = new Intent(CustomerHomepageActivity.this, CustomerHomepageActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.nav_support_drawer) {
                    Intent intent = new Intent(CustomerHomepageActivity.this, ChatActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.nav_shopping_drawer) {
                    Intent intent = new Intent(CustomerHomepageActivity.this, CartActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.nav_cart_drawer) {
                    Intent intent = new Intent(CustomerHomepageActivity.this, CartActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.nav_info_drawer) {
                    Intent intent = new Intent(CustomerHomepageActivity.this, UserActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.nav_logout_drawer) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(CustomerHomepageActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }

                // Sau khi xử lý xong, đóng Drawer Navigation
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START); // Đóng Drawer từ trái sang
                return true;
            }
        });
    }
}