package com.example.pharmacyandroidapplication.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.rxjava3.annotations.NonNull;

public class EditOrderActivity extends AppCompatActivity {
    private String order_id,user_id,status_order,status_pay;
    private Spinner spinner_order,spinner_payment;
    private String[] options = {"Đang xử lý", "Đang giao", "Đã giao"};
    private String[] options_pay = {"Chưa thanh toán", "Đã thanh toán"};
    private TextView id_order, id_user, order_date, total_price, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
        user_id = intent.getStringExtra("user_id");
        id_user = findViewById(R.id.user_id);
        id_order = findViewById(R.id.order_id);
        id_user.setText(user_id);
        id_order.setText(order_id);
        spinner_order = findViewById(R.id.spinner_order);
        spinner_payment = findViewById(R.id.spinner_payment);
        order_date = findViewById(R.id.order_date);
        total_price = findViewById(R.id.total_price);
        address = findViewById(R.id.address);
        ArrayAdapter<String> adapter_order = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter_order.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_order.setAdapter(adapter_order);
        ArrayAdapter<String> adapter_payment = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options_pay);
        adapter_payment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_payment.setAdapter(adapter_payment);
        loadFireBase();
        Button btn_Finish = findViewById(R.id.finish);
        Button btn_Cancel = findViewById(R.id.cancel);

        btn_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị của Spinner Order
                String selectedOrder = (String) spinner_order.getSelectedItem();
                // Lấy giá trị của Spinner Payment
                String selectedPayment = (String) spinner_payment.getSelectedItem();
                DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("order").child(user_id).child(order_id);
                // Cập nhật trực tiếp các trường
                orderRef.child("order_status").setValue(selectedOrder);
                orderRef.child("payment_status").setValue(selectedPayment);
                Intent intent = new Intent(EditOrderActivity.this, OrderManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditOrderActivity.this, OrderDetailsManagementActivity.class);
                startActivity(intent);
            }
        });
    }
    private void loadFireBase(){
        Log.i("ID",user_id);
        // Tạo tham chiếu đến node "cart" trong Firebase

        DatabaseReference orderdetailRef = FirebaseDatabase.getInstance().getReference("order").child(user_id).child(order_id);
        orderdetailRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status_order = dataSnapshot.child("order_status").getValue(String.class);
                status_pay = dataSnapshot.child("payment_status").getValue(String.class);
                // Sử dụng phương thức này:
                int orderIndex = getIndexOfString(options, status_order);
                spinner_order.setSelection(orderIndex);
                int paymentIndex = getIndexOfString(options_pay, status_pay);
                spinner_payment.setSelection(paymentIndex);
                order_date.setText(dataSnapshot.child("order_date").getValue(String.class));
                total_price.setText(dataSnapshot.child("total_payment").getValue(String.class));
                address.setText(dataSnapshot.child("address").getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi ở đây
            }
        });
    }

    private int getIndexOfString(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1; // Không tìm thấy
    }

}
