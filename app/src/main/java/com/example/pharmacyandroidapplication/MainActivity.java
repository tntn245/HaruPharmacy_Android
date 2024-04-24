package com.example.pharmacyandroidapplication;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.adapters.StockInAdapter;
import com.example.pharmacyandroidapplication.models.StockIn;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    EditText editTextEmail, editTextPassword;
    Button buttonReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super. onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}