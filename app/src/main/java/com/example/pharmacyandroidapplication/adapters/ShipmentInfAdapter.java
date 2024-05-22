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
            holder.img_delete_address_item = convertView.findViewById(R.id.img_delete_address_item);
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
        //Xử lý sự kiện Xóa
        ImageView imgDeleteAddress = convertView.findViewById(R.id.img_delete_address_item);
        imgDeleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ra address_id của địa chỉ
                String addressId = ShipmentInfList.get(position).getAddress_id();

                // Gọi phương thức xử lý sự kiện khi bấm vào img_delete_address
                if (deleteClickListener != null) {
                    deleteClickListener.onDeleteClick(addressId);
                }
            }
        });
        ImageView img_edit_address = convertView.findViewById(R.id.img_edit_address_item);
        img_edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editClickListener != null) {
                    editClickListener.onEditClick(shipmentInf);
                }
            }
        });
        return convertView;
    }
    private static class ViewHolder {
        ImageView img_delete_address_item;
    }
}

