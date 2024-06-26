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
import com.example.pharmacyandroidapplication.models.StockIn;

import java.util.ArrayList;

public class StockInAdapter extends ArrayAdapter<StockIn> {
    private Context context;
    ArrayList<StockIn> StockInArrayList;
    public StockInAdapter(@NonNull Context context, ArrayList<StockIn> StockInArrayList) {
        super(context, 0, StockInArrayList);
        this.context = context;
        this.StockInArrayList = StockInArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_stock_in, parent, false);
        }

        StockIn stockIn = getItem(position);
        TextView id_stock_in = listitemView.findViewById(R.id.id_stock_in);
        TextView date_stock_in = listitemView.findViewById(R.id.date_stock_in);
        TextView price_stock_in = listitemView.findViewById(R.id.price_stock_in);

        assert stockIn != null;
        id_stock_in.setText(stockIn.getId());
//        DateFormat dateFormat = new DateFormat(stockIn.getDate());
        date_stock_in.setText(stockIn.getDate());
        price_stock_in.setText(Integer.toString(stockIn.getTotal_payment()));
        return listitemView;
    }
}
