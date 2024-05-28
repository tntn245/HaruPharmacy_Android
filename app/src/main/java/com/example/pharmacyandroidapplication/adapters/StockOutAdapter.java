package com.example.pharmacyandroidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.DateFormat;
import com.example.pharmacyandroidapplication.models.StockIn;
import com.example.pharmacyandroidapplication.models.StockOut;

import java.util.ArrayList;

public class StockOutAdapter extends ArrayAdapter<StockOut> {
    ArrayList<StockOut> StockOutArrayList;
    public StockOutAdapter(@NonNull Context context, ArrayList<StockOut> StockOutArrayList) {
        super(context, 0, StockOutArrayList);
        this.StockOutArrayList = StockOutArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_stock_out, parent, false);
        }

        StockOut stockOut = getItem(position);
        TextView id_stock_out = listitemView.findViewById(R.id.id_stock_out);
        TextView date_stock_out = listitemView.findViewById(R.id.date_stock_out);
        TextView noted_stock_out = listitemView.findViewById(R.id.noted_stock_out);

        assert stockOut != null;
        id_stock_out.setText(stockOut.getId());
        date_stock_out.setText(stockOut.getDate());
        noted_stock_out.setText(stockOut.getNoted());
        return listitemView;
    }
}
