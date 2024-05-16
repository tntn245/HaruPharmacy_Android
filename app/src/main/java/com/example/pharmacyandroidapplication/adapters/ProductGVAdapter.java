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
import com.example.pharmacyandroidapplication.models.Product;

import java.util.ArrayList;

public class ProductGVAdapter extends ArrayAdapter<Product> {
    public ProductGVAdapter(@NonNull Context context, ArrayList<Product> productArrayList) {
        super(context, 0, productArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
        }

        Product product = getItem(position);
        TextView name_product = listitemView.findViewById(R.id.txt_name_product);
        ImageView img_product = listitemView.findViewById(R.id.img_product);

        assert product != null;
        name_product.setText(product.getName());
//        img_product.setImageResource(product.getImg());
        return listitemView;
    }
}

