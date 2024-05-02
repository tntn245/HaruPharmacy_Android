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

public class ProductInventoryLotNumDetailsAdapter extends ArrayAdapter<ProductStockInDetails> {
    public ProductInventoryLotNumDetailsAdapter(@NonNull Context context, ArrayList<ProductStockInDetails> productStockInDetailsArrayList) {
        super(context, 0, productStockInDetailsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_product_inventory_lotnum_details, parent, false);
        }

        ProductStockInDetails productStockInDetails = getItem(position);
        TextView lot_number = listitemView.findViewById(R.id.lot_number);
        TextView production_date = listitemView.findViewById(R.id.production_date);
        TextView expiration_date = listitemView.findViewById(R.id.expiration_date);
        TextView lotnum_quantity_in_stock = listitemView.findViewById(R.id.lotnum_quantity_in_stock);

        assert productStockInDetails != null;
        lot_number.setText(productStockInDetails.getLot_number());

        DateFormat dateFormat = new DateFormat(productStockInDetails.getProduction_date());
        production_date.setText(dateFormat.formatDateToString());

        dateFormat = new DateFormat(productStockInDetails.getExpiration_date());
        expiration_date.setText(dateFormat.formatDateToString());

        lotnum_quantity_in_stock.setText(Integer.toString(productStockInDetails.getQuantity_in_stock()));

        return listitemView;
    }
}

