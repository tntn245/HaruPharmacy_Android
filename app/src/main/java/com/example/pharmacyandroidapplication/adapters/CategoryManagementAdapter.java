package com.example.pharmacyandroidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.models.Category;
import com.example.pharmacyandroidapplication.models.DateFormat;
import com.example.pharmacyandroidapplication.models.Order;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.R;

import java.util.ArrayList;

public class CategoryManagementAdapter extends ArrayAdapter<Category> {
//    private Context context;
//    ArrayList<Category> categoryArrayList;
//    public CategoryManagementAdapter(@NonNull Context context, ArrayList<Category> categoryArrayList) {
//        super(context, 0, categoryArrayList);
//        this.context = context;
//        this.categoryArrayList = categoryArrayList;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        View listitemView = convertView;
//        if (listitemView == null) {
//            // Layout Inflater inflates each item to be displayed in GridView.
//            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_category, parent, false);
//        }
//
//        Category category = getItem(position);
//        TextView  txt_category = listitemView.findViewById(R.id.txt_category);
//        TextView order_id = listitemView.findViewById(R.id.order_id);
//        TextView order_date = listitemView.findViewById(R.id.order_date);
//        TextView order_total_payment = listitemView.findViewById(R.id.order_total_payment);
//
//
//        assert category != null;
//        order_id.setText(category.getId());
//        order_total_payment.setText(category.getName());
//        txt_category.setText(category.getName());
//        return listitemView;
//    }
    private Context context;
    ArrayList<Category> categoryArrayList;
    private HomeCategoryAdapter.OnButtonClickListener buttonClickListener;

    public CategoryManagementAdapter(@NonNull Context context, ArrayList<Category> categoryArrayList) {
        super(context, 0, categoryArrayList);
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }
    public interface OnButtonClickListener {
        void onButtonClick(int position, String text);
    }

    public void setOnButtonClickListener(HomeCategoryAdapter.OnButtonClickListener listener) {
        this.buttonClickListener = listener;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_category, parent, false);
        }

        Category category = getItem(position);
        TextView button_category = listitemView.findViewById(R.id.button_category);

        assert category != null;
        button_category.setText(category.getName());
        button_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonClickListener != null) {
                    buttonClickListener.onButtonClick(position, category.getName());
                }
            }
        });
        if (!category.isFlag_valid()) button_category.setAlpha(0.4f);
        else button_category.setAlpha(1f);
        return listitemView;
    }

}
