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
    private Context context;

    public OrderRVAdapter(Context context, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderArrayList.get(position);
        holder.order_id.setText("124224");
        holder.order_date.setText("2/5/2024");
        holder.order_total_payment.setText("100000");
        if (order.isOrder_status()) holder.order_status.setText("Đã giao");
        else holder.order_status.setText("Đang vận chuyển");
        if (order.isPayment_status()) holder.payment_status.setText("Đã thanh toán");
        else holder.payment_status.setText("Chưa thanh toán");
    }

    public int getItemCount() {
        return orderArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView order_id;
        TextView order_date;
        TextView order_total_payment;
        TextView payment_status;
        TextView order_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_id = itemView.findViewById(R.id.order_id);
            order_date = itemView.findViewById(R.id.order_date);
            order_total_payment = itemView.findViewById(R.id.order_total_payment);
            order_status = itemView.findViewById(R.id.order_status);
            payment_status = itemView.findViewById(R.id.payment_status);
        }
    }
}