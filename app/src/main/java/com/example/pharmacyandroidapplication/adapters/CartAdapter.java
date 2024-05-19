package com.example.pharmacyandroidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.customer.ProductDetailsActivity;
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
        holder.unit.setText(cart.getUnit());
        Glide.with(this.mContext)
                .load(cart.getImg())
                .into(holder.imgv);
        if(cart.isChecked()){
            holder.check.setChecked(true);
        }else {
            holder.check.setChecked(false);
        }
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = mCart.get(position).getQuanlity();
                quantity++;
                mCart.get(position).setQuanlity(quantity);
                holder.tv_quanlity.setText(String.valueOf(quantity));
            }
        });

        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = mCart.get(position).getQuanlity();
                if (quantity > 1) {
                    quantity--;
                    mCart.get(position).setQuanlity(quantity);
                    holder.tv_quanlity.setText(String.valueOf(quantity));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCart.size();
    }
    // Phương thức triển khai getItem()
    public Cart getItem(int position) {
        return mCart.get(position);
    }

    // Phương thức triển khai getAdapter()
    public CartAdapter getAdapter() {
        return this;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgv;
        private TextView tv_name;
        private TextView tv_price;
        private TextView tv_quanlity;
        private CheckBox check;
        private Button increase;
        private Button decrease;
        private TextView unit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgv = itemView.findViewById(R.id.img_item_cart);
            tv_name = itemView.findViewById(R.id.item_cart_name);
            tv_price = itemView.findViewById(R.id.item_cart_price);
            tv_quanlity = itemView.findViewById(R.id.item_cart_quanlity);
            check = itemView.findViewById(R.id.item_check_box);
            increase = itemView.findViewById(R.id.item_cart_increase);
            decrease =itemView.findViewById(R.id.item_cart_decrease);
            unit = itemView.findViewById(R.id.unit);
        }
    }
    public void setChecked(int position, boolean checked) {
        Cart cart = mCart.get(position);
        cart.setChecked(checked);
        notifyItemChanged(position);
    }
}