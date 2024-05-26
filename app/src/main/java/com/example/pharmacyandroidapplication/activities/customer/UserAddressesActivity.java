package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ShipmentInfAdapter;
import com.example.pharmacyandroidapplication.models.ShipmentInf;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserAddressesActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference addressRef = database.getReference("shipment details");
    private String address_id, user_id, receiverName, phone, province, district, commune, address_details;
    private ArrayList<ShipmentInf> shipmentInfArrayList = new ArrayList<>();

    private ShipmentInf shipmentInfSelected;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        ImageButton btnAddAddress = findViewById(R.id.add_button);
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAddressesActivity.this, UserAddAddressesActivity.class);
                startActivity(intent);
            }
        });

        setupGridView(); // Gọi phương thức setupGridView() để cập nhật GridView
    }

    private void setupGridView() {
        GridView addressGV = findViewById(R.id.list_addresses);

        //load data from Firebase
        addressRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shipmentInfArrayList.clear(); // Xóa các phần tử cũ trong shipmentInfArrayList

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy dữ liệu từ Firebase
                    address_id = snapshot.child("address_id").getValue().toString();
                    user_id = snapshot.child("user_id").getValue().toString();
                    receiverName = snapshot.child("receiverName").getValue().toString();
                    phone = snapshot.child("phone").getValue().toString();
                    province = snapshot.child("province").getValue().toString();
                    district = snapshot.child("district").getValue().toString();
                    commune = snapshot.child("commune").getValue().toString();
                    address_details = snapshot.child("address_details").getValue().toString();
                    ShipmentInf shipmentInf = new ShipmentInf(address_id, user_id, receiverName, phone, province, district, commune, address_details);
                    shipmentInfArrayList.add(shipmentInf);
                }

                ShipmentInfAdapter adapter = new ShipmentInfAdapter(UserAddressesActivity.this, shipmentInfArrayList);
                addressGV.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
            }
        });

        // Đặt sự kiện click cho mỗi item trong GridView
        addressGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị của item được click
                ShipmentInf item = shipmentInfArrayList.get(position);
                shipmentInfSelected = shipmentInfArrayList.get(position);
                String addressId =item.getAddress_id();
                //Xử lý sự kiện khi item được click
                ImageView img_edit_address = findViewById(R.id.img_edit_address_item);
                img_edit_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAddressFromFirebase(addressId);
                    }
                });
            }
        });

    }

    private void deleteAddressFromFirebase(String addressId) {
        // Thực hiện xóa địa chỉ từ Firebase dựa trên addressId
        DatabaseReference addressToDeleteRef = addressRef.child(addressId);
        addressToDeleteRef.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    // Xóa thành công
                    // Thực hiện các hành động sau khi xóa, ví dụ: hiển thị thông báo, cập nhật giao diện, vv.
                    Toast.makeText(UserAddressesActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // Xóa thất bại
                    // Xử lý lỗi nếu cần
                    Toast.makeText(UserAddressesActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}