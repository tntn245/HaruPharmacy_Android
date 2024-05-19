package com.example.pharmacyandroidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.ShipmentInf;

import java.util.List;

public class ShipmentInfAdapter extends BaseAdapter {
    private Context context;
    private List<ShipmentInf> ShipmentInfList;

    public ShipmentInfAdapter(Context context, List<ShipmentInf> ShipmentInfList) {
        this.context = context;
        this.ShipmentInfList = ShipmentInfList;
    }

    @Override
    public int getCount() {
        return ShipmentInfList.size();
    }

    @Override
    public Object getItem(int position) {
        return ShipmentInfList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false);
        }

        ShipmentInf ShipmentInf = ShipmentInfList.get(position);

        TextView txtName = convertView.findViewById(R.id.txt_name_receiver_address);
        TextView txtPhone = convertView.findViewById(R.id.txt_phone_address);
        TextView txtAddress = convertView.findViewById(R.id.txt_total_address_address);

        txtName.setText(ShipmentInf.getReceiverName());
        txtPhone.setText(ShipmentInf.getPhone());
        txtAddress.setText(ShipmentInf.getAddress_details() + ", " + ShipmentInf.getCommune() + ", " + ShipmentInf.getDistrict() + ", " + ShipmentInf.getProvince());

        return convertView;
    }
}
