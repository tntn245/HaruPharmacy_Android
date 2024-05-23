package com.example.pharmacyandroidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.ItemPay;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class ItemPayAdapter extends RecyclerView.Adapter<ItemPayAdapter.ViewHolder> {
    private Context mContext;
    private List<ItemPay> itemList;

    public ItemPayAdapter(Context mContext, List<ItemPay> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemPay item = itemList.get(position);
        holder.itemName.setText(item.getName());
        holder.itemPrice.setText(String.valueOf(item.getPrice())+"đ");
        holder.itemQuantity.setText("SL: "+String.valueOf(item.getQuantity()));
        holder.itemUnit.setText("Đơn vị: "+String.valueOf(item.getUnit()));
        Glide.with(this.mContext)
                .load(item.getImg())
                .into(holder.imgv);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice,itemQuantity,itemUnit;
        ImageView imgv;
        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_pay_name);
            itemPrice = itemView.findViewById(R.id.item_pay_price);
            itemQuantity = itemView.findViewById(R.id.item_pay_quanlity);
            itemUnit = itemView.findViewById(R.id.item_pay_unit);
            imgv = itemView.findViewById(R.id.img_item_cart);
        }
    }
}