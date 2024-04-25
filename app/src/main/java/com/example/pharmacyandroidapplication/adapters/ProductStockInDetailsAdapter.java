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
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.ProductStockInDetails;

import java.util.ArrayList;

public class ProductStockInDetailsAdapter extends ArrayAdapter<ProductStockInDetails> {
    public ProductStockInDetailsAdapter(@NonNull Context context, ArrayList<ProductStockInDetails> productStockInDetailsArrayList) {
        super(context, 0, productStockInDetailsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_product_stock_in, parent, false);
        }

        ProductStockInDetails productStockInDetails = getItem(position);
        TextView lot_number = listitemView.findViewById(R.id.lot_number);
        TextView production_date = listitemView.findViewById(R.id.production_date);
        TextView expiration_date = listitemView.findViewById(R.id.expiration_date);
        TextView name_product = listitemView.findViewById(R.id.name_product);
        TextView product_unit_price = listitemView.findViewById(R.id.product_unit_price);
        TextView product_quantity = listitemView.findViewById(R.id.product_quantity);
        TextView product_total_price = listitemView.findViewById(R.id.product_total_price);
        ImageView img_product = listitemView.findViewById(R.id.img_product);

        assert productStockInDetails != null;
        name_product.setText(productStockInDetails.getProduct_name());
        lot_number.setText(productStockInDetails.getLot_number());

        DateFormat dateFormat = new DateFormat(productStockInDetails.getProduction_date());
        production_date.setText(dateFormat.formatDateToString());

        dateFormat = new DateFormat(productStockInDetails.getExpiration_date());
        expiration_date.setText(dateFormat.formatDateToString());

        name_product.setText(productStockInDetails.getProduct_name());
        product_unit_price.setText(Integer.toString(productStockInDetails.getUnit_price()));
        product_quantity.setText(Integer.toString(productStockInDetails.getQuantity()));
        product_total_price.setText(Integer.toString(productStockInDetails.totalPrice()));
        img_product.setImageResource(productStockInDetails.getImg());

        return listitemView;
    }
}