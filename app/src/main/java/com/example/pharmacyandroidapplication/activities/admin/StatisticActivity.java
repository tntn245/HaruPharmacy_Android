package com.example.pharmacyandroidapplication.activities.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;

public class StatisticActivity extends AppCompatActivity {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private BarChart barChart;
    private Spinner monthSpinner;
    private Spinner yearSpinner;
    ArrayList<BarEntry> entries ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        monthSpinner = findViewById(R.id.monthSpinner);
        yearSpinner = findViewById(R.id.yearSpinner);
        barChart = findViewById(R.id.barChart);
        barChart.getDescription().setText("Ngày");
        entries = new ArrayList<>();

        // Thiết lập dữ liệu cho Spinner tháng và năm (giả sử bạn đã có dữ liệu cho chúng)
        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.months, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.years, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        // Gán sự kiện lắng nghe cho Spinner tháng và năm
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadFirebaseData();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không cần làm gì khi không chọn gì cả
            }
        });

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadFirebaseData();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không cần làm gì khi không chọn gì cả
            }
        });

        // Ban đầu, hiển thị dữ liệu cho biểu đồ với tháng và năm hiện tại
        loadFirebaseData();
    }
    private void updateChart(ArrayList<BarEntry> entries) {
        // Tạo BarDataSet từ các BarEntry
        BarDataSet barDataSet = new BarDataSet(entries, "Doanh thu");

        // Tạo BarData từ BarDataSet
        BarData barData = new BarData(barDataSet);

        // Đặt dữ liệu của biểu đồ
        barChart.setData(barData);

        // Refresh biểu đồ
        barChart.invalidate();
    }

    private ArrayList<BarEntry> generateDataForChart(int month, int year) {
        // Ở đây bạn cần truy vấn dữ liệu từ Firebase và tạo dữ liệu cho biểu đồ
        // Dữ liệu có thể được tạo dựa trên tổng doanh thu trong 1 ngày trong tháng và năm được chọn từ Spinner
        // Sau đó, trả về một danh sách các BarEntry với các giá trị x và y tương ứng
        // Dưới đây là một ví dụ mẫu:

        if(month == 2){
            entries.add(new BarEntry(1f, 200f)); // Ví dụ: ngày 1 có doanh thu là 100
            entries.add(new BarEntry(2f, 100f)); // Ví dụ: ngày 2 có doanh thu là 200
        }else {
            entries.add(new BarEntry(1f, 100f)); // Ví dụ: ngày 1 có doanh thu là 100
            entries.add(new BarEntry(2f, 200f)); // Ví dụ: ngày 2 có doanh thu là 200
        }
        // Thêm các ngày khác tương tự
        return entries;
    }
    private void loadFirebaseData() {
        int selectedMonth = monthSpinner.getSelectedItemPosition() + 1; // Vị trí bắt đầu từ 0
        int selectedYear = yearSpinner.getSelectedItemPosition() + 2022;
        Log.i("DU LIEU TIM THAY",String.valueOf(selectedMonth) );
        // Tạo tham chiếu đến node "order" trong Firebase
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("order");
        // Khởi tạo HashMap để lưu tổng giá trị đơn cho mỗi ngày trong tháng
        HashMap<Integer, Integer> dailyTotalMap = new HashMap<>();
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Duyệt qua các nút con trong node "order"
                for (DataSnapshot cartSnapshot : dataSnapshot.getChildren()){
                    for (DataSnapshot orderSnapshot : cartSnapshot.getChildren()) {
                        String orderDate = orderSnapshot.child("order_date").getValue(String.class);
                        String orderStatus = orderSnapshot.child("order_status").getValue(String.class);
                        String totalPaymentString = orderSnapshot.child("total_payment").getValue(String.class);

                        // Kiểm tra xem ngày hóa đơn có thuộc tháng và năm cụ thể không
                        if (orderDate != null && orderStatus != null && totalPaymentString != null) {
                            String[] dateParts = orderDate.split("/");
                            int orderMonth = Integer.parseInt(dateParts[1]);
                            int orderYear = Integer.parseInt(dateParts[2]);
                            if (orderMonth == selectedMonth && orderYear == selectedYear && orderStatus.equals("Đã giao")) {
                                // Lấy ngày từ dữ liệu hóa đơn
                                int orderDay = Integer.parseInt(dateParts[0]);
                                // Lấy giá trị đơn từ dữ liệu hóa đơn
                                int totalPayment = Integer.parseInt(totalPaymentString);
                                Log.i("DU LIEU ", String.valueOf(orderDay) );
                                Log.i("DU LIEU ", String.valueOf(totalPayment) );

                                // Cộng tổng giá trị đơn vào ngày tương ứng trong HashMap
                                int currentTotal = dailyTotalMap.getOrDefault(orderDay, 0);
                                dailyTotalMap.put(orderDay, currentTotal + totalPayment);
                                Log.i("DU LIEU TIM THAY", String.valueOf(totalPayment));
                            }
                        }
                    }
                }

                // Chuyển dữ liệu từ HashMap vào một mảng các BarEntry
                for (Map.Entry<Integer, Integer> entry : dailyTotalMap.entrySet()) {
                    int day = entry.getKey();
                    int total = entry.getValue();
                    entries.add(new BarEntry((float) day, (float) total));
                }
                // Gọi phương thức để cập nhật biểu đồ với dữ liệu mới
                updateChart(entries);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy
            }
        });
    }
}
//    // Sample revenue data for demonstration
//    int[][] revenueData = {
//            {1, 100}, // Day 1: x = 1 (ngày), y = 100 (doanh thu)
//            {2, 200}, // Day 2: x = 2 (ngày), y = 200 (doanh thu)
//            {3, 300}, // Day 3: x = 3 (ngày), y = 300 (doanh thu)
//            {4, 400}, // Day 4: x = 4 (ngày), y = 400 (doanh thu)
//            {5, 500}  // Day 5: x = 5 (ngày), y = 500 (doanh thu)
//    };
//
//    ArrayList<BarEntry> entries = new ArrayList<>();
//        for (int[] data : revenueData) {
//                entries.add(new BarEntry((float) data[0], data[1])); // (X, Y)
//                }
//
//                // Tạo BarDataSet từ các BarEntry
//                BarDataSet barDataSet = new BarDataSet(entries, "Doanh thu");
//
//                // Tạo BarData từ BarDataSet
//                BarData barData = new BarData(barDataSet);
//
//                // Đặt dữ liệu của biểu đồ
//                barChart.setData(barData);
//
//                // Customize X axis to display days
//                XAxis xAxis = barChart.getXAxis();
//                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//                xAxis.setDrawGridLines(false);
//                xAxis.setGranularityEnabled(true); // Tắt tính năng tối ưu hóa giá trị x
//                xAxis.setValueFormatter(new ValueFormatter() {
//@Override
//public String getAxisLabel(float value, AxisBase axis) {
//        return String.valueOf((int) value);
//        }
//        });
//
//        barChart.invalidate(); // Refresh chart