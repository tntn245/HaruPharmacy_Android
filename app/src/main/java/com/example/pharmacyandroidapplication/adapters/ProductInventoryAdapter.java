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

public class ProductInventoryAdapter extends ArrayAdapter<Product> {
    public ProductInventoryAdapter(@NonNull Context context, ArrayList<Product> productArrayList) {
        super(context, 0, productArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_product_inventory, parent, false);
        }

        Product product = getItem(position);
        TextView product_name = listitemView.findViewById(R.id.product_name);
        TextView product_inventory_quantity = listitemView.findViewById(R.id.product_inventory_quantity);
        ImageView product_img = listitemView.findViewById(R.id.product_img);

        assert product != null;
        product_name.setText(product.getProductName());
        product_inventory_quantity.setText(Integer.toString(product.getInventory_quantity()));
        product_img.setImageResource(product.getImg());
        return listitemView;
    }
}

