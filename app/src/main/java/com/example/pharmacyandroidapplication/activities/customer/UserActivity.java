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
        //Load quantity of Orders
        TextView txt_processing_order_quantity, txt_delivering_order_quantity, txt_delivered_order_quantity;
        LinearLayout order_processing, order_delivering, order_delivered;
        txt_processing_order_quantity = findViewById(R.id.txt_processing_order_quantity);
        txt_delivering_order_quantity = findViewById(R.id.txt_delivering_order_quantity);
        txt_delivered_order_quantity = findViewById(R.id.txt_delivered_order_quantity);
        order_processing = findViewById(R.id.order_processing);
        order_delivering = findViewById(R.id.order_delivering);
        order_delivered = findViewById(R.id.order_delivered);
        DatabaseReference orderByIdRef = database.getReference("order").child(userID).getRef();
        DatabaseReference orderDetailRef = database.getReference("orderdetail");
        orderByIdRef.addValueEventListener(new ValueEventListener() {
            int countProcessingOrder = 0, countDeliveringOrder = 0, countDeliveredOrder = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("order_status").getValue(String.class).equals("Đang xử lý"))
                        countProcessingOrder++;
                    if (dataSnapshot.child("order_status").getValue(String.class).equals("Đang giao"))
                        countDeliveringOrder++;
                    if (dataSnapshot.child("order_status").getValue(String.class).equals("Đã giao"))
                        countDeliveredOrder++;
                }
                txt_processing_order_quantity.setText("(" + countProcessingOrder + ")");
                txt_delivering_order_quantity.setText("(" + countDeliveringOrder + ")");
                txt_delivered_order_quantity.setText("(" + countDeliveredOrder + ")");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Click Orders
        order_processing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, OrdersTrackingActivity.class);
                intent.putExtra("orderStatus", 1);
                startActivity(intent);
            }
        });
        order_delivering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, OrdersTrackingActivity.class);
                intent.putExtra("orderStatus", 2);
                startActivity(intent);
            }
        });
        order_delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, OrdersTrackingActivity.class);
                intent.putExtra("orderStatus", 3);
                startActivity(intent);
            }
        });
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
