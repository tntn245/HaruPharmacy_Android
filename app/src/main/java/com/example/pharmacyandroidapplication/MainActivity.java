package com.example.pharmacyandroidapplication;
import android.os.Bundle;
import android.widget.LinearLayout;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        LinearLayout Detail = findViewById(R.id.detail_product);
    }
}