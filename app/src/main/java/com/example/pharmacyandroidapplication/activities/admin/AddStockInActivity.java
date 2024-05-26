package com.example.pharmacyandroidapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ProductStockInDetailsAdapter;
import com.example.pharmacyandroidapplication.models.ProductStockInDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddStockInActivity extends AppCompatActivity {
    String productID, productName, lotNumber, unitName, productionDate, expirationDate;
    int inQuantity, unitPrice;
    ArrayList<ProductStockInDetails> productStockInDetails;
    GridView StockInDetails;
    ProductStockInDetailsAdapter adapter;
    EditText date_stock_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stockin);

        StockInDetails = findViewById(R.id.list_product_stock_in);
        productStockInDetails = new ArrayList<ProductStockInDetails>();
        adapter = new ProductStockInDetailsAdapter(this, productStockInDetails, true);
        StockInDetails.setAdapter(adapter);

        date_stock_in = findViewById(R.id.date_stock_in);
        date_stock_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        Button btn_add = findViewById(R.id.btn_add_product_stockin);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddStockInActivity.this, AddProductStockInActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            productID = data.getStringExtra("productID");
            productName = data.getStringExtra("productName");
            lotNumber = data.getStringExtra("lotNumber");
            unitName = data.getStringExtra("unitName");
            productionDate = data.getStringExtra("productionDate");
            expirationDate = data.getStringExtra("expirationDate");
            inQuantity = data.getIntExtra("inQuantity", 0);
            unitPrice = data.getIntExtra("unitPrice", 0);
            Toast.makeText(this, "Đã chọn " + productName, Toast.LENGTH_LONG).show();
        }

        productStockInDetails.add(new ProductStockInDetails(productID,productName, lotNumber, productionDate, expirationDate, inQuantity, 0, unitPrice, unitName, ""));
        adapter.notifyDataSetChanged();
    }

    public void showDatePickerDialog(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Khi người dùng chọn ngày, cập nhật TextView với ngày đã chọn
                Calendar selectedCalendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                selectedCalendar.set(year, month, dayOfMonth);
                String selectedDate = dateFormat.format(selectedCalendar.getTime());
                date_stock_in.setText(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
}