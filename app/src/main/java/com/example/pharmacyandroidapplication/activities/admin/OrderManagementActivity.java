package com.example.pharmacyandroidapplication.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.OrderRVAdapter;
import com.example.pharmacyandroidapplication.itemClickListener.OrderItemClickListener;
import com.example.pharmacyandroidapplication.models.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.rxjava3.annotations.NonNull;

public class OrderManagementActivity extends AppCompatActivity implements OrderItemClickListener{
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private RecyclerView orderRV;
    private OrderRVAdapter orderAdapter;
    private ArrayList<Order> orders ;
    private int dxl=0,dgg=0,dg=0;
    private TextView sldangxuly;
    private TextView sldanggiao;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);
        orders = new ArrayList<>();
        loadFireBaseDangxuly();
        orderAdapter = new OrderRVAdapter(this, orders,this);
        orderRV = (RecyclerView) findViewById(R.id.orderRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        orderRV.setLayoutManager(layoutManager);
        orderRV.setHasFixedSize(true);
        orderRV.setAdapter(orderAdapter);
        timsoluong();
        LinearLayout Dangxuly = findViewById(R.id.order_processing);
        LinearLayout Danggiao = findViewById(R.id.Danggiao);
        LinearLayout Dagiao = findViewById(R.id.Dagiao);
        Dangxuly.setBackground(ContextCompat.getDrawable(OrderManagementActivity.this, R.drawable.choose_background));
        Dangxuly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dangxuly.setBackground(ContextCompat.getDrawable(OrderManagementActivity.this, R.drawable.choose_background));
                Danggiao.setBackground(null);
                Dagiao.setBackground(null);
                loadFireBaseDangxuly();
            }
        });
        Danggiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Danggiao.setBackground(ContextCompat.getDrawable(OrderManagementActivity.this, R.drawable.choose_background));
                Dangxuly.setBackground(null);
                Dagiao.setBackground(null);
                loadFireBaseDanggiao();
            }
        });
        Dagiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dagiao.setBackground(ContextCompat.getDrawable(OrderManagementActivity.this, R.drawable.choose_background));
                Dangxuly.setBackground(null);
                Danggiao.setBackground(null);
                loadFireBaseDagiao();
            }
        });

        ImageView btn_back = findViewById(R.id.back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderManagementActivity.this, AdminHomepageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onOrderItemClick(String orderId, String UserId) {
        // Xử lý khi item được click, ví dụ chuyển đến trang mới và truyền ID
        Intent intent = new Intent(OrderManagementActivity.this, OrderDetailsManagementActivity.class);
        intent.putExtra("selectedOrderID", orderId);
        intent.putExtra("selectedUserID", UserId);
        startActivity(intent);
    }
    private void initorder() {




        try {
            // Chuyển đổi chuỗi ngày tháng thành đối tượng Date
            Date orderDate = dateFormat.parse("22/04/2019");

            // Thêm đơn hàng mới vào danh sách
            orders.add(new Order("#1", "1", 200000, "Đã thanh toán", "Chưa xử lý", "blacklist", "QNam", orderDate, 3));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            // Chuyển đổi chuỗi ngày tháng thành đối tượng Date
            Date orderDate = dateFormat.parse("22/04/2021");

            // Thêm đơn hàng mới vào danh sách
            orders.add(new Order("#2", "1", 200000, "Đã thanh toán", "Chưa xử lý", "blacklist", "QNam", orderDate, 3));
        } catch (ParseException e) {
            e.printStackTrace();
        } try {
            // Chuyển đổi chuỗi ngày tháng thành đối tượng Date
            Date orderDate = dateFormat.parse("22/04/2022");

            // Thêm đơn hàng mới vào danh sách
            orders.add(new Order("#3", "1", 200000, "Đã thanh toán", "Chưa xử lý", "blacklist", "QNam", orderDate, 3));
        } catch (ParseException e) {
            e.printStackTrace();
        } try {
            // Chuyển đổi chuỗi ngày tháng thành đối tượng Date
            Date orderDate = dateFormat.parse("22/04/2023");

            // Thêm đơn hàng mới vào danh sách
            orders.add(new Order("#4", "1", 200000, "Đã thanh toán", "Chưa xử lý", "blacklist", "QNam", orderDate, 3));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        orderRV = (RecyclerView) findViewById(R.id.orderRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        orderRV.setLayoutManager(layoutManager);
        orderRV.setHasFixedSize(true);
        orderRV.setAdapter(new OrderRVAdapter(this, orders, this));
    }
    private void loadFireBase(){
        // Lấy ID người dùng đã được xác thực
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.i("ID",userId);
        // Tạo tham chiếu đến node "cart" trong Firebase
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("order");
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa dữ liệu cũ trong danh sách giỏ hàng
                orders.clear();
                // Duyệt qua các nút con trong node "cart"
                for (DataSnapshot cartSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot orderSnapshot : cartSnapshot.getChildren()){
                        String id_order = orderSnapshot.getKey();
                        String id_account = cartSnapshot.getKey();
                        String total_payment = orderSnapshot.child("total_payment").getValue(String.class);
                        String payment_status = orderSnapshot.child("payment_status").getValue(String.class);
                        String order_status = orderSnapshot.child("order_status").getValue(String.class);
                        String noted = orderSnapshot.child("noted").getValue(String.class);
                        String address = orderSnapshot.child("address").getValue(String.class);
                        String order_date = orderSnapshot.child("order_date").getValue(String.class);
                        final int[] total_product = {0};
                        DatabaseReference orderdetailRef = FirebaseDatabase.getInstance().getReference("orderdetail").child(id_order);
                        orderdetailRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 total_product[0] = (int) dataSnapshot.getChildrenCount();
                                // Làm gì đó với size ở đây
                                System.out.println("Size of orderdetail: " + total_product[0]);
                                try {
                                    // Chuyển đổi chuỗi ngày tháng thành đối tượng Date
                                    Date orderDate = dateFormat.parse(order_date);
                                    // Thêm đơn hàng mới vào danh sách
                                    orders.add(new Order(id_order, id_account, Integer.parseInt(total_payment) , payment_status, order_status, noted, address, orderDate, total_product[0]));
                                    Log.i("THANH CONG ADDD","Oderr");
                                } catch (ParseException e) {
                                    Log.i("FAIL TO ADD ORRDER","DEO HIEU TAI SAO");
                                    e.printStackTrace();
                                }
                                orderAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Xử lý lỗi ở đây
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy
            }
        });
    }
    private void loadFireBaseDangxuly(){
        // Lấy ID người dùng đã được xác thực
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.i("ID",userId);
        // Tạo tham chiếu đến node "cart" trong Firebase
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("order");
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa dữ liệu cũ trong danh sách giỏ hàng
                orders.clear();
                // Duyệt qua các nút con trong node "cart"
                for (DataSnapshot cartSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot orderSnapshot : cartSnapshot.getChildren()){
                        String order_status = orderSnapshot.child("order_status").getValue(String.class);
                        if(order_status.equals("Đang xử lý")) {
                            String id_order = orderSnapshot.getKey();
                            String id_account = cartSnapshot.getKey();
                            String total_payment = orderSnapshot.child("total_payment").getValue(String.class);
                            String payment_status = orderSnapshot.child("payment_status").getValue(String.class);
                            String noted = orderSnapshot.child("noted").getValue(String.class);
                            String address = orderSnapshot.child("address").getValue(String.class);
                            String order_date = orderSnapshot.child("order_date").getValue(String.class);
                            final int[] total_product = {0};
                            DatabaseReference orderdetailRef = FirebaseDatabase.getInstance().getReference("orderdetail").child(id_order);
                            orderdetailRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    total_product[0] = (int) dataSnapshot.getChildrenCount();
                                    // Làm gì đó với size ở đây
                                    System.out.println("Size of orderdetail: " + total_product[0]);
                                    try {
                                        // Chuyển đổi chuỗi ngày tháng thành đối tượng Date
                                        Date orderDate = dateFormat.parse(order_date);
                                        // Thêm đơn hàng mới vào danh sách
                                        orders.add(new Order(id_order, id_account, Integer.parseInt(total_payment), payment_status, order_status, noted, address, orderDate, total_product[0]));
                                        Log.i("THANH CONG ADDD", "Oderr");
                                    } catch (ParseException e) {
                                        Log.i("FAIL TO ADD ORRDER", "DEO HIEU TAI SAO");
                                        e.printStackTrace();
                                    }
                                    orderAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Xử lý lỗi ở đây
                                }
                            });
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy
            }
        });

    }
    private void loadFireBaseDanggiao(){
        // Lấy ID người dùng đã được xác thực
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.i("ID",userId);
        // Tạo tham chiếu đến node "cart" trong Firebase
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("order");
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa dữ liệu cũ trong danh sách giỏ hàng
                orders.clear();
                // Duyệt qua các nút con trong node "cart"
                for (DataSnapshot cartSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot orderSnapshot : cartSnapshot.getChildren()){
                        String order_status = orderSnapshot.child("order_status").getValue(String.class);
                        if(order_status.equals("Đang giao")) {
                            String id_order = orderSnapshot.getKey();
                            String id_account = cartSnapshot.getKey();
                            String total_payment = orderSnapshot.child("total_payment").getValue(String.class);
                            String payment_status = orderSnapshot.child("payment_status").getValue(String.class);
                            String noted = orderSnapshot.child("noted").getValue(String.class);
                            String address = orderSnapshot.child("address").getValue(String.class);
                            String order_date = orderSnapshot.child("order_date").getValue(String.class);
                            final int[] total_product = {0};
                            DatabaseReference orderdetailRef = FirebaseDatabase.getInstance().getReference("orderdetail").child(id_order);
                            orderdetailRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    total_product[0] = (int) dataSnapshot.getChildrenCount();
                                    // Làm gì đó với size ở đây
                                    System.out.println("Size of orderdetail: " + total_product[0]);
                                    try {
                                        // Chuyển đổi chuỗi ngày tháng thành đối tượng Date
                                        Date orderDate = dateFormat.parse(order_date);
                                        // Thêm đơn hàng mới vào danh sách
                                        orders.add(new Order(id_order, id_account, Integer.parseInt(total_payment), payment_status, order_status, noted, address, orderDate, total_product[0]));
                                        Log.i("THANH CONG ADDD", "Oderr");
                                    } catch (ParseException e) {
                                        Log.i("FAIL TO ADD ORRDER", "DEO HIEU TAI SAO");
                                        e.printStackTrace();
                                    }
                                    orderAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Xử lý lỗi ở đây
                                }
                            });
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy
            }
        });

    }
    private void loadFireBaseDagiao(){
        // Lấy ID người dùng đã được xác thực
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.i("ID",userId);
        // Tạo tham chiếu đến node "cart" trong Firebase
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("order");
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa dữ liệu cũ trong danh sách giỏ hàng
                orders.clear();
                // Duyệt qua các nút con trong node "cart"
                for (DataSnapshot cartSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot orderSnapshot : cartSnapshot.getChildren()){
                        String order_status = orderSnapshot.child("order_status").getValue(String.class);
                        if(order_status.equals("Đã giao")) {
                            String id_order = orderSnapshot.getKey();
                            String id_account = cartSnapshot.getKey();
                            String total_payment = orderSnapshot.child("total_payment").getValue(String.class);
                            String payment_status = orderSnapshot.child("payment_status").getValue(String.class);
                            String noted = orderSnapshot.child("noted").getValue(String.class);
                            String address = orderSnapshot.child("address").getValue(String.class);
                            String order_date = orderSnapshot.child("order_date").getValue(String.class);
                            final int[] total_product = {0};
                            DatabaseReference orderdetailRef = FirebaseDatabase.getInstance().getReference("orderdetail").child(id_order);
                            orderdetailRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    total_product[0] = (int) dataSnapshot.getChildrenCount();
                                    // Làm gì đó với size ở đây
                                    System.out.println("Size of orderdetail: " + total_product[0]);
                                    try {
                                        // Chuyển đổi chuỗi ngày tháng thành đối tượng Date
                                        Date orderDate = dateFormat.parse(order_date);
                                        // Thêm đơn hàng mới vào danh sách
                                        orders.add(new Order(id_order, id_account, Integer.parseInt(total_payment), payment_status, order_status, noted, address, orderDate, total_product[0]));
                                        Log.i("THANH CONG ADDD", "Oderr");
                                    } catch (ParseException e) {
                                        Log.i("FAIL TO ADD ORRDER", "DEO HIEU TAI SAO");
                                        e.printStackTrace();
                                    }
                                    orderAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Xử lý lỗi ở đây
                                }
                            });
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy
            }
        });

    }
    private void timsoluong(){
        // Lấy ID người dùng đã được xác thực
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.i("ID",userId);
        // Tạo tham chiếu đến node "cart" trong Firebase
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("order");
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa dữ liệu cũ trong danh sách giỏ hàng
                orders.clear();
                // Duyệt qua các nút con trong node "cart"
                for (DataSnapshot cartSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot orderSnapshot : cartSnapshot.getChildren()){
                        String order_status = orderSnapshot.child("order_status").getValue(String.class);
                        if(order_status.equals("Đang xử lý")) {
                            dxl ++;
                        }
                        else if(order_status.equals("Đang giao")){
                            dgg ++;
                        }
                    }
                }
                sldangxuly = findViewById(R.id.txt_processing_order_quantity);
                sldanggiao = findViewById(R.id.txt_delivering_order_quantity);
                sldangxuly.setText("("+String.valueOf(dxl)+")");
                sldanggiao.setText("("+String.valueOf(dgg)+")");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy
            }
        });

    }
}