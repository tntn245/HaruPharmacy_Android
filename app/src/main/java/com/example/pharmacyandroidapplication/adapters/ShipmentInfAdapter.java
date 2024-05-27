package com.example.pharmacyandroidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.ShipmentInf;

import java.util.List;

public class ShipmentInfAdapter extends BaseAdapter {
    private Context context;
    private List<ShipmentInf> ShipmentInfList;
    private OnDeleteClickListener deleteClickListener;
    private OnEditClickListener editClickListener;

    public ShipmentInfAdapter(Context context, List<ShipmentInf> ShipmentInfList) {
        this.context = context;
        this.ShipmentInfList = ShipmentInfList;
    }
    public interface OnDeleteClickListener {
        void onDeleteClick(String addressId);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    public interface OnEditClickListener {
        void onEditClick(ShipmentInf shipmentInf);
    }

    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ShipmentInf shipmentInf = ShipmentInfList.get(position);

        TextView txtName = convertView.findViewById(R.id.txt_name_receiver_address);
        TextView txtPhone = convertView.findViewById(R.id.txt_phone_address);
        TextView txtAddress = convertView.findViewById(R.id.txt_total_address_address);

        txtName.setText(shipmentInf.getReceiverName());
        txtPhone.setText(shipmentInf.getPhone());
        txtAddress.setText(shipmentInf.getAddress_details() + ", " + shipmentInf.getCommune() + ", " + shipmentInf.getDistrict() + ", " + shipmentInf.getProvince());

        return convertView;
    }
    private static class ViewHolder {
        ImageView img_delete_address_item;
    }
}

