package com.example.pharmacyandroidapplication.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.OrderDetailsAdapter;
import com.example.pharmacyandroidapplication.adapters.OrderRVAdapter;
import com.example.pharmacyandroidapplication.models.Cart;
import com.example.pharmacyandroidapplication.models.Order;
import com.example.pharmacyandroidapplication.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.rxjava3.annotations.NonNull;

public class OrderDetailsManagementActivity extends AppCompatActivity {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private OrderRVAdapter orderAdapter;
    private String order_idd;
    private String userid;
    private ArrayList<Cart> carts;
    private ArrayList<Order> orders ;
    private Product product;
    private TextView User_Id,Order_Date,Total_Pay,Status_Order,Pay_Order;
    protected GridView ProductGV;
    private ArrayList<Product> ProductArrayList;
    private OrderDetailsAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_management);

        // Lấy Intent từ Activity
        Intent intent = getIntent();
        order_idd = intent.getStringExtra("selectedOrderID");
        userid = intent.getStringExtra("selectedUserID");
        carts = new ArrayList<>();
        ProductArrayList = new ArrayList<Product>();
        TextView orderID_tv = findViewById(R.id.order_id);
        orderID_tv.setText(order_idd);
//        TextView orderDate_tv = findViewById(R.id.order_date);
//        assert orderDate != null;
//        DateFormat dateFormat = new DateFormat(orderDate);
//        orderDate_tv.setText(dateFormat.formatDateToString());
//
//        TextView orderTotalPayment_tv = findViewById(R.id.order_total_payment);
//        orderTotalPayment_tv.setText(Integer.toString(orderTotalPayment));

        // Hiển thị item trong gridview (load từ dtb dựa trên id của order)
        loadFireBase();
        productAdapter = new OrderDetailsAdapter(this, ProductArrayList);
        ProductGV.setAdapter(productAdapter);

        ImageView btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailsManagementActivity.this, EditOrderActivity.class);
                intent.putExtra("order_id", order_idd);
                intent.putExtra("user_id", userid);
                startActivity(intent);
            }
        });
    }
    private void loadFireBase(){
        // Lấy ID người dùng đã được xác thực
        Intent intent = getIntent();
        Log.i("ID",userid);
        User_Id = findViewById(R.id.user_id);
        User_Id.setText(userid);
        Order_Date = findViewById(R.id.order_date);
        Total_Pay =findViewById(R.id.order_total_payment);
        Status_Order=findViewById(R.id.order_status);
        Pay_Order=findViewById(R.id.payment_status);
        ProductGV = findViewById(R.id.list_products);
        // Tạo tham chiếu đến node "cart" trong Firebase
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("order").child(userid).child(order_idd);
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa dữ liệu cũ trong danh sách giỏ hàng
                        Order_Date.setText(String.valueOf(dataSnapshot.child("order_date").getValue(String.class)));
                        Total_Pay.setText(String.valueOf(dataSnapshot.child("total_payment").getValue(String.class)));
                        Status_Order.setText(String.valueOf(dataSnapshot.child("order_status").getValue(String.class)));
                        Pay_Order.setText(String.valueOf(dataSnapshot.child("payment_status").getValue(String.class)));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy
            }
        });
        // Tạo tham chiếu đến node "cart" trong Firebase
        DatabaseReference item = FirebaseDatabase.getInstance().getReference("orderdetail").child(order_idd);
        item.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa dữ liệu cũ trong danh sách giỏ hàng
                carts.clear();
                // Duyệt qua các nút con trong node "cart"
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    String productId = itemSnapshot.getKey();
                    Log.i("PRD_ID",productId);
                    for (DataSnapshot infoSnapshot : itemSnapshot.getChildren()){
                        String quantity = infoSnapshot.child("quantity").getValue(String.class);
                        String unit = infoSnapshot.getKey();
                        String price = infoSnapshot.child("unit_price").getValue(String.class);
                        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("product").child(productId);
                        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    product = dataSnapshot.getValue(Product.class);
                                    ProductArrayList.add(new Product(productId, "" , product.getImg(), product.getName(),Integer.parseInt(quantity) ,Integer.parseInt(price),unit ));
                                    Log.i("Carrt",carts.toString());
                                    // Cập nhật lại Adapter để hiển thị danh sách giỏ hàng mới
                                    productAdapter.notifyDataSetChanged();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Xử lý khi có lỗi xảy ra
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
    private String formatDate(Date date) {
        return dateFormat.format(date);
    }
}