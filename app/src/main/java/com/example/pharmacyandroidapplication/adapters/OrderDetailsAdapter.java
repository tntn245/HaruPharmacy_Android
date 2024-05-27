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

import com.bumptech.glide.Glide;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.Product;

import java.util.ArrayList;

public class OrderDetailsAdapter extends ArrayAdapter<Product> {
    private Context mContext;
    ArrayList<Product> productArrayList;

    public OrderDetailsAdapter(@NonNull Context context, ArrayList<Product> productArrayList) {
        super(context, 0, productArrayList);
        this.productArrayList = productArrayList;
        this.mContext = context;
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
        ImageView product_img = listitemView.findViewById(R.id.img_prd);
        TextView product_name = listitemView.findViewById(R.id.name_prd);
        TextView product_price = listitemView.findViewById(R.id.product_price);
        TextView product_quantity = listitemView.findViewById(R.id.product_quantity);
        TextView product_unit = listitemView.findViewById(R.id.unit);

        assert product != null;
//        product_img.setImageResource(product.getImg()); error_due_to
        Glide.with(this.mContext)
                .load(product.getImg())
                .into(product_img);
        product_name.setText(product.getName());
        product_price.setText(Integer.toString(product.getPrice()));
        product_quantity.setText(Integer.toString(product.getInventory_quantity()));
        product_unit.setText(product.getUnit());
        return listitemView;
    }
}
