package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pharmacyandroidapplication.MainActivity;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.ChatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {
    String userID;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        userID = getIntent().getExtras().getString("userID");
//        DatabaseReference orderByIdRef = database.getReference("order").child(userID).getRef();
        //Load quantity of Orders
        TextView txt_processing_order_quantity, txt_delivering_order_quantity, txt_delivered_order_quantity;
        txt_processing_order_quantity = findViewById(R.id.txt_processing_order);
        txt_delivering_order_quantity = findViewById(R.id.txt_delivering_order);
        txt_delivered_order_quantity = findViewById(R.id.txt_delivered_order);
        DatabaseReference orderByIdRef = database.getReference("order").child(userID).getRef();
        DatabaseReference orderDetailRef = database.getReference("orderdetail");
//        orderByIdRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot: snapshot.getChildren())
//                {
//                    String idOrder = dataSnapshot.child("id_order").getValue().toString();
//                    orderDetailRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
//                            if(idOrder.equals(dataSnapshot1.getKey().toString()))
//                            {
//                                // Tạo DatabaseRef tới Product để lấy ra tất cả product để lấy ra key product
//                                    // Tạo DatabaseRef tới tới Unit để lấy ra tất cả unit để lấy ra key unit
//                                       // Lấy được lotnumber, quantity, unitprice,...
//                            }}
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        //Click User Profile
        CardView userProfile = findViewById(R.id.user_profile);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, UserProfileActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        CardView userAddresses = findViewById(R.id.user_addresses);
        userAddresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, UserAddressesActivity.class);
                startActivity(intent);
            }
        });

        TextView viewOrders = findViewById(R.id.view_orders);
        viewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, OrdersTrackingActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout linearLayout = findViewById(R.id.order_processing);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện hành động khi LinearLayout được click
                // Ví dụ: Chuyển sang trang khác
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
                    Intent supportIntent = new Intent(UserActivity.this, CustomerHomepageActivity.class);
                    startActivity(supportIntent);
                    finish();
                    return true;
                } else if (itemId == R.id.support) {
                    // Xử lý khi người dùng chọn trang tư vấn
                    Intent intent = new Intent(UserActivity.this, ChatActivity.class);
                    intent.putExtra("userID", "zDVjeEon70POnmT25BdJbEmB5jG3");
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.cart) {
                    // Xử lý khi người dùng chọn trang giỏ hàng
                    Intent CartIntent = new Intent(UserActivity.this, CartActivity.class);
                    startActivity(CartIntent);
                    return true;
                } else if (itemId == R.id.profile) {
                    return true;
                }
                return false;
            }
        });

    }
}
