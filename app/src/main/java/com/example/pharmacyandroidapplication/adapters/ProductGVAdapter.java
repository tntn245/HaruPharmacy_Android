package com.example.pharmacyandroidapplication.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.listeners.OnProductClickListener;
import com.example.pharmacyandroidapplication.models.Product;

import java.util.ArrayList;

public class ProductGVAdapter extends ArrayAdapter<Product> {
    private OnProductClickListener onProductClickListener;
    public ProductGVAdapter(@NonNull Context context, ArrayList<Product> productArrayList) {
        super(context, 0, productArrayList);
    }
    public void setOnProductClickListener(OnProductClickListener listener) {
        this.onProductClickListener = listener;
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
        TextView price_product = listitemView.findViewById(R.id.product_price);
        ImageButton imageButton = listitemView.findViewById(R.id.btn_edit);
        assert product != null;

        name_product.setText(product.getName());
        price_product.setText(String.valueOf(product.getPrice()));

        Glide.with(this.getContext().getApplicationContext())
                .load(product.getImg())
                .into(img_product);
        // Thêm OnClickListener cho ImageButton
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onProductClickListener != null) {
                    onProductClickListener.onProductClick(product.getId());
                }
            }
        });
        return listitemView;
    }
}

