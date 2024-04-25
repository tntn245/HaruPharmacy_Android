package com.example.pharmacyandroidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.StockIn;

import java.util.ArrayList;

public class OrderDetailsAdapter extends ArrayAdapter<Product> {
    ArrayList<Product> productArrayList;

    public OrderDetailsAdapter(@NonNull Context context, ArrayList<Product> productArrayList) {
        super(context, 0, productArrayList);
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_product_orderdetail, parent, false);
        }

        Product product = getItem(position);
        ImageView product_img = listitemView.findViewById(R.id.product_img);
        TextView product_name = listitemView.findViewById(R.id.product_name);
        TextView product_price = listitemView.findViewById(R.id.product_price);
        TextView product_quantity = listitemView.findViewById(R.id.product_quantity);

        assert product != null;
        product_img.setImageResource(product.getImg());
        product_name.setText(product.getName());
        product_price.setText(Integer.toString(product.getPrice()));
        product_quantity.setText(Integer.toString(product.getQuantity()));
        return listitemView;
    }
}
