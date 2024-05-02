package com.example.pharmacyandroidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.DateFormat;
import com.example.pharmacyandroidapplication.models.ProductStockInDetails;
import com.example.pharmacyandroidapplication.models.ProductStockOutDetails;

import java.util.ArrayList;

public class ProductStockOutDetailsAdapter extends ArrayAdapter<ProductStockOutDetails> {
    public ProductStockOutDetailsAdapter(@NonNull Context context, ArrayList<ProductStockOutDetails> productStockOutDetailsArrayList) {
        super(context, 0, productStockOutDetailsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_product_stock_out, parent, false);
        }

        ProductStockOutDetails productStockOutDetails = getItem(position);
        TextView name_product = listitemView.findViewById(R.id.name_product);
        TextView lot_number = listitemView.findViewById(R.id.lot_number);
        TextView quantity = listitemView.findViewById(R.id.quantity);
        ImageView img_product = listitemView.findViewById(R.id.img_product);

        assert productStockOutDetails != null;
        name_product.setText(productStockOutDetails.getProduct_name());
        lot_number.setText(productStockOutDetails.getLot_number());
        quantity.setText(Integer.toString(productStockOutDetails.getOut_quantity()));
        img_product.setImageResource(productStockOutDetails.getProduct_img());

        return listitemView;
    }
}