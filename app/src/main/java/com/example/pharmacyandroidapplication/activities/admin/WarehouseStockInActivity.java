package com.example.pharmacyandroidapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.StockInAdapter;
import com.example.pharmacyandroidapplication.models.StockIn;

import java.util.ArrayList;
import java.util.Date;

public class WarehouseStockInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_stock_in);
        GridView StockInWH= findViewById(R.id.list_stock_in);
        ArrayList<StockIn> StockArrayList = new ArrayList<StockIn>();

        StockArrayList.add(new StockIn("FDSFGFSG",new Date(2024, 4, 2), 100000));
        StockArrayList.add(new StockIn("MEFGFSGG",new Date(2024, 4, 1),100000));
        StockArrayList.add(new StockIn("ODSAXVSG",new Date(2024, 3, 29),100000));
        StockArrayList.add(new StockIn("EKUGFSYG",new Date(2024, 3, 18),100000));

        StockInAdapter adapter = new StockInAdapter(this, StockArrayList);
        StockInWH.setAdapter(adapter);

        // Đặt sự kiện click cho mỗi item trong GridView
        StockInWH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị của item được click
                StockIn itemStockIn = StockArrayList.get(position);

                // Truyền giá trị của item qua layout tiếp theo để hiển thị
                Intent intent = new Intent(WarehouseStockInActivity.this, WarehouseStockInDetailsActivity.class);
                intent.putExtra("selectedStockInID", itemStockIn.getId());
                startActivity(intent);
            }
        });
    }
}