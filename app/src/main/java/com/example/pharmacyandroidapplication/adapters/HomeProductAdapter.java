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

import java.util.ArrayList;


public class HomeProductAdapter extends ArrayAdapter<Product> {
    public HomeProductAdapter(@NonNull Context context, ArrayList<Product> productArrayList) {
        super(context, 0, productArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_shopping, parent, false);
        }

        Product product = getItem(position);
        TextView name_product = listitemView.findViewById(R.id.item_name);
        TextView price_product = listitemView.findViewById(R.id.item_price);
        ImageView img_product = listitemView.findViewById(R.id.img_product);

        assert product != null;
        name_product.setText(product.getProductName());
        price_product.setText(Integer.toString(product.getProductPrice()));
        img_product.setImageResource(product.getProductImg());

        return listitemView;
    }
}
