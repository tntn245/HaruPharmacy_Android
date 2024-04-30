package com.example.pharmacyandroidapplication.activities.customer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.MainActivity;
import com.example.pharmacyandroidapplication.R;

public class ProductDetailsActivity extends AppCompatActivity {
    private void showProductDetailDialog() {
        Dialog dialog = new Dialog(ProductDetailsActivity.this,R.style.FullScreenDialog);
        dialog.setContentView(R.layout.drawer_product_detail);
        // Lấy kích thước của màn hình
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        // Thiết lập kích thước dialog
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height =  LayoutParams.WRAP_CONTENT; // 70% chiều cao của màn hình
        layoutParams.gravity = Gravity.BOTTOM; // Hiển thị ở dưới cùng
        layoutParams.horizontalMargin = 0; // Thiết lập margin ngang
        layoutParams.verticalMargin = 0; // Thiết lập margin dọc
        // Thiết lập cho dialog
        dialog.getWindow().setAttributes(layoutParams);
        // Xử lý sự kiện khi nhấn các nút trong dialog (nếu cần)

        // Hiển thị dialog
        dialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Bên trong activity hoặc fragment
        @SuppressLint("ResourceType") Animation slideUp = AnimationUtils.loadAnimation(this, R.drawable.animation_slide_up);
        Button btnChooseToBuy = findViewById(R.id.btn_Chonmua);
        btnChooseToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị dialog
                showProductDetailDialog();
            }
        });
    }
}
