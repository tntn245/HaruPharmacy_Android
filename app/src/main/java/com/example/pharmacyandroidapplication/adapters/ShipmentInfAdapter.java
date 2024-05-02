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
import com.example.pharmacyandroidapplication.models.ShipmentInf;

import java.util.ArrayList;

public class ShipmentInfAdapter extends ArrayAdapter<ShipmentInf> {
    private Context context;
    ArrayList<ShipmentInf> shipmentInfArrayList;
    public ShipmentInfAdapter(@NonNull Context context, ArrayList<ShipmentInf> shipmentInfArrayList) {
        super(context, 0, shipmentInfArrayList);
        this.context = context;
        this.shipmentInfArrayList = shipmentInfArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_address, parent, false);
        }

        ShipmentInf shipmentInf = getItem(position);
        TextView address_details = listitemView.findViewById(R.id.multilineTextView);

        assert shipmentInf != null;
        address_details.setText(shipmentInf.getAddress_details());
//        DateFormat dateFormat = new DateFormat(shipmentDetails.getDate());
//        date_stock_in.setText(dateFormat.formatDateToString());
//        price_stock_in.setText(Integer.toString(shipmentDetails.getTotal_payment()));
        return listitemView;
    }
}