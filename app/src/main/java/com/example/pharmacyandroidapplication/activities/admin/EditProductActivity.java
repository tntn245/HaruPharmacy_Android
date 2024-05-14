package com.example.pharmacyandroidapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EditProductActivity extends AppCompatActivity {
    private TextView txt_product_id;
    private EditText edt_txt_product_name, edt_txt_product_price, edt_txt_product_ingredient, edt_txt_product_uses;
    private Spinner edt_spn_product_type, edt_spn_product_unit, edt_spn_product_status;
    private Button btn_saved_edt_product;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference productRef = database.getReference("product");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        txt_product_id = findViewById(R.id.txt_product_id);
        edt_txt_product_name = findViewById(R.id.edt_txt_product_name);
        edt_txt_product_ingredient = findViewById(R.id.edt_txt_product_ingredient);
        edt_txt_product_price = findViewById(R.id.edt_txt_product_price);
        edt_txt_product_uses = findViewById(R.id.edt_txt_product_uses);
        edt_spn_product_status = findViewById(R.id.edt_spn_product_status);
        edt_spn_product_type = findViewById(R.id.edt_spn_product_type);
        edt_spn_product_unit = findViewById(R.id.edt_spn_product_unit);
        btn_saved_edt_product = findViewById(R.id.btn_saved_edt_product);
        btn_saved_edt_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedEditProduct();

            }
        });
    }

    private void savedEditProduct() {
        String id, name, uses, ingredient, unit;
        int price, img_link;
        boolean valid_flag;
//        id = txt_product_id.getText().toString();
        id ="#0235732";
        name = edt_txt_product_name.getText().toString();
        price = Integer.valueOf(edt_txt_product_price.getText().toString());
        uses = edt_txt_product_uses.getText().toString();
        ingredient = edt_txt_product_ingredient.getText().toString();
//        unit = edt_spn_product_unit.getSelectedItem().toString();
        unit = "lọ";
        img_link = 0;
//        if (edt_spn_product_status.getSelectedItem().toString() == "On sale") valid_flag = true;
//        else valid_flag = false;
        valid_flag = true;
        Product product = new Product(id, name, img_link, price, uses, ingredient, unit, valid_flag);

        productRef.child(id).setValue(product);
    }
}