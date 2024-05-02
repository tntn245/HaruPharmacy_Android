package com.example.pharmacyandroidapplication.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ShipmentInfAdapter;
import com.example.pharmacyandroidapplication.models.ShipmentInf;

import java.util.ArrayList;

public class UserAddressesActivity extends AppCompatActivity {
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

        GridView addressGV= findViewById(R.id.list_addresses);
        ArrayList<ShipmentInf> shipmentInfArrayList = new ArrayList<ShipmentInf>();

        shipmentInfArrayList.add(new ShipmentInf("1111", "2222", "0123456789", "123, đường X", "Tân An", "Long An"));

        ShipmentInfAdapter adapter = new ShipmentInfAdapter(this, shipmentInfArrayList);
        addressGV.setAdapter(adapter);

        // Đặt sự kiện click cho mỗi item trong GridView
        addressGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị của item được click
                ShipmentInf item = shipmentInfArrayList.get(position);

            }
        });
    }
}
