package com.example.pharmacyandroidapplication.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.Cart;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Cart>mCart;
    public CartAdapter(Context context, ArrayList<Cart> cart){
        this.mContext = context;
        this.mCart = cart;
    }
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (parent == null) {
            return null;
        }
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View cartView =inflater.inflate(R.layout.item_cart,parent,false);
        ViewHolder viewHolder =new ViewHolder(cartView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Cart cart = mCart.get(position);
        holder.tv_name.setText(cart.getName_product());
        holder.tv_quanlity.setText(String.valueOf(cart.getQuanlity()));
        holder.tv_price.setText(String.valueOf(cart.getPrice_product()));
    }

    @Override
    public int getItemCount() {
        return mCart.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgv;
        private TextView tv_name;
        private TextView tv_price;
        private TextView tv_quanlity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgv = itemView.findViewById(R.id.img_item_cart);
            tv_name = itemView.findViewById(R.id.item_cart_name);
            tv_price = itemView.findViewById(R.id.item_cart_price);
            tv_quanlity = itemView.findViewById(R.id.item_cart_quanlity);
        }
    }
}
