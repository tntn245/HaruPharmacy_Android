package com.example.pharmacyandroidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.Category;
import com.example.pharmacyandroidapplication.models.DateFormat;
import com.example.pharmacyandroidapplication.models.Product;
import com.example.pharmacyandroidapplication.models.StockIn;

import java.util.ArrayList;
public class HomeCategoryAdapter extends ArrayAdapter<Category> {

    private Context context;
    ArrayList<Category> categoryArrayList;
    private OnButtonClickListener buttonClickListener;

    public HomeCategoryAdapter(@NonNull Context context, ArrayList<Category> categoryArrayList) {
        super(context, 0, categoryArrayList);
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    public interface OnButtonClickListener {
        void onButtonClick(int position, String text);
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
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
        Button button_category = listitemView.findViewById(R.id.button_category);

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
        return listitemView;
    }

}
