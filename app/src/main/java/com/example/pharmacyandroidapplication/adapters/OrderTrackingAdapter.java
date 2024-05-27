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
import com.example.pharmacyandroidapplication.models.Order;

import java.util.ArrayList;

public class OrderTrackingAdapter extends ArrayAdapter<Order> {
    private Context context;
    ArrayList<Order> orderArrayList;
    public OrderTrackingAdapter(@NonNull Context context, ArrayList<Order> OrderArrayList) {
        super(context, 0, OrderArrayList);
        this.context = context;
        this.orderArrayList = OrderArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_order_tracking, parent, false);
        }

        Order order = getItem(position);
        TextView order_id = listitemView.findViewById(R.id.order_id);
        TextView order_date = listitemView.findViewById(R.id.order_date);
        TextView order_total_payment = listitemView.findViewById(R.id.order_total_payment);
//        TextView order_total_quantity = listitemView.findViewById(R.id.order_total_quantity);

        assert order != null;
        order_id.setText(order.getId_order());
        DateFormat dateFormat = new DateFormat(order.getOrder_date());
        order_date.setText(dateFormat.formatDateToString());
        order_total_payment.setText(Integer.toString(order.getTotal_payment()));
//        order_total_quantity.setText(Integer.toString(order.getTotal_product()));
        return listitemView;
    }
}
