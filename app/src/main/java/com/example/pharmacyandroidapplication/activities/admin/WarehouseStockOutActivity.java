package com.example.pharmacyandroidapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.StockInAdapter;
import com.example.pharmacyandroidapplication.adapters.StockOutAdapter;
import com.example.pharmacyandroidapplication.models.StockIn;
import com.example.pharmacyandroidapplication.models.StockOut;

import java.util.ArrayList;
import java.util.Date;

public class WarehouseStockOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_stock_out);

        GridView StockOutGV= findViewById(R.id.list_stock_out);
        ArrayList<StockOut> StockArrayList = new ArrayList<StockOut>();

        StockArrayList.add(new StockOut("FDSFGFSG",new Date(2024, 4, 2), "Hàng hết hạn"));
        StockArrayList.add(new StockOut("MEFGFSGG",new Date(2024, 4, 1),"Hàng hết hạn"));
        StockArrayList.add(new StockOut("ODSAXVSG",new Date(2024, 3, 29),"Hàng hết hạn"));
        StockArrayList.add(new StockOut("EKUGFSYG",new Date(2024, 3, 18),"Hàng hết hạn"));

        StockOutAdapter adapter = new StockOutAdapter(this, StockArrayList);
        StockOutGV.setAdapter(adapter);

        // Đặt sự kiện click cho mỗi item trong GridView
        StockOutGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị của item được click
                StockOut itemStockOut = StockArrayList.get(position);

                // Truyền giá trị của item qua layout tiếp theo để hiển thị
                Intent intent = new Intent(WarehouseStockOutActivity.this, WarehouseStockOutDetailsActivity.class);
                intent.putExtra("selectedStockOutID", itemStockOut.getId());
                startActivity(intent);
            }
        });

        ImageView btn_add = findViewById(R.id.btn_add_stockout);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarehouseStockOutActivity.this, AddStockOutActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}