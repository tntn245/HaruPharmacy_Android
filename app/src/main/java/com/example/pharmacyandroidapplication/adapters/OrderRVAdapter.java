package com.example.pharmacyandroidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.ChatMessage;
import com.example.pharmacyandroidapplication.models.Order;

import java.util.ArrayList;
import java.util.List;
public class OrderRVAdapter extends RecyclerView.Adapter<OrderRVAdapter.ViewHolder> {
    private ArrayList<Order> orderArrayList;

    public OrderRVAdapter(ArrayList<Order> orderArrayList) {
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderArrayList.get(position);
        holder.txt_order_id.setText("#ID DON HANG: " + order.getId_order());
        holder.txt_total_payment.setText("Total payment: " + order.getTotal_payment());
        if (order.isOrder_status()) holder.order_status.setText("Done");
        else holder.order_status.setText("Not yet");
        if (order.isPayment_status()) holder.payment_status.setText("Done");
        else holder.payment_status.setText("Not yet");
    }

    public int getItemCount() {
        return orderArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_order_id;
        TextView txt_staff_name;
        TextView txt_total_payment;
        TextView order_status;
        TextView payment_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_order_id = itemView.findViewById(R.id.txt_order_id);
            txt_staff_name = itemView.findViewById(R.id.txt_staff_name);
            txt_total_payment = itemView.findViewById(R.id.txt_total_payment);
            order_status = itemView.findViewById(R.id.order_status);
            payment_status = itemView.findViewById(R.id.payment_status);
        }
    }
}