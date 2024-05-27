package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.HomeProductAdapter;
import com.example.pharmacyandroidapplication.adapters.OrderDetailsAdapter;
import com.example.pharmacyandroidapplication.adapters.StockInAdapter;
import com.example.pharmacyandroidapplication.models.DateFormat;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.StockIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class OrderDetailsActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference orderDetailRef = database.getReference("orderdetail");
    private DatabaseReference productRef = database.getReference("product");
    private ArrayList<Product> ProductArrayList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        // Lấy Intent từ Activity
        Intent intent = getIntent();
        String orderID = intent.getStringExtra("selectedOrderID");
        Date orderDate = (Date) intent.getSerializableExtra("selectedOrderDate");
        int orderTotalPayment = intent.getIntExtra("selectedTotalPayment", 0);
        // Hiển thị thông tin
        TextView orderID_tv = findViewById(R.id.order_id);
        orderID_tv.setText(orderID);

        TextView orderDate_tv = findViewById(R.id.order_date);
        assert orderDate != null;
        DateFormat dateFormat = new DateFormat(orderDate);
        orderDate_tv.setText(dateFormat.formatDateToString());

        TextView orderTotalPayment_tv = findViewById(R.id.order_total_payment);
        orderTotalPayment_tv.setText(Integer.toString(orderTotalPayment));

        // Hiển thị item trong gridview (load từ dtb dựa trên id của order)
        GridView ProductGV = findViewById(R.id.list_products);

        DatabaseReference orderdetailByIdRef = orderDetailRef.child(orderID);
        ProductArrayList = new ArrayList<Product>();

        orderdetailByIdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product product = new Product("");
                String id;
                for (DataSnapshot item : snapshot.getChildren()) {
                    id = item.getKey().toString();
                    product.setId(id);
                    DatabaseReference orderDetailByIdProductRef = orderdetailByIdRef.child(id).getRef();
                    orderDetailByIdProductRef.addValueEventListener(new ValueEventListener() {
                        String unit;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                            for (DataSnapshot item1: snapshot1.getChildren())
                            {
                                DatabaseReference productByIdRef = productRef.child(product.getId()).getRef();
                                productByIdRef.addValueEventListener(new ValueEventListener() {
                                    String namePro, imgPro;
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshotPro) {
                                        namePro = snapshotPro.child("name").getValue().toString();
                                        imgPro = snapshotPro.child("img").getValue().toString();
                                        product.setName(namePro);
                                        product.setImg(imgPro);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                unit = item.getKey().toString();
                                product.setUnit(unit);
                                DatabaseReference orderDetailByIdProductUnitRef = orderDetailByIdProductRef.child(unit).getRef();
                                orderDetailByIdProductUnitRef.addValueEventListener(new ValueEventListener() {
                                    int quantity_ordered, sell_price;
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                        Log.e("?", product.getName().toString() );
                                        for (DataSnapshot item2: snapshot2.getChildren())
                                        {
                                            quantity_ordered = item2.child("quantity").getValue(Integer.class);
                                            sell_price = item2.child("unit_sell_price").getValue(Integer.class);
                                            product.setInventory_quantity(quantity_ordered);
                                            product.setPrice(sell_price);
                                            ProductArrayList.add(product);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                OrderDetailsAdapter productAdapter = new OrderDetailsAdapter(OrderDetailsActivity.this, ProductArrayList);
                ProductGV.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dataSnapshot.getValue().toString().equals("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
