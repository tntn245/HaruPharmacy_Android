package com.example.pharmacyandroidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.R;

import java.util.ArrayList;

public class CategoryManagementAdapter extends RecyclerView.Adapter<OrderRVAdapter.ViewHolder> {
    private ArrayList<Product> products;
    private Context context;
    @NonNull
    public OrderRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderRVAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRVAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class DataProductViewHolder extends RecyclerView.ViewHolder {
        TextView txt_product_name;
        ImageView img_product;
        TextView txt_inventory_quantity;
        TextView txt_price;

        public DataProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_product_name = itemView.findViewById(R.id.product_name);
            img_product = itemView.findViewById(R.id.img_product);
            txt_inventory_quantity = itemView.findViewById(R.id.inventory);
            txt_price = null;
        }
    }
}
