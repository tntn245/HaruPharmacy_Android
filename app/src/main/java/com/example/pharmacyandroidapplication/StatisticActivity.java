package com.example.pharmacyandroidapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        BarChart barChart = findViewById(R.id.barChart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1f, 100f)); // (X, Y)
        entries.add(new BarEntry(2f, 200f)); // (X, Y)
        entries.add(new BarEntry(3f, 300f)); // (X, Y)
        entries.add(new BarEntry(4f, 400f)); // (X, Y)
        entries.add(new BarEntry(5f, 500f)); // (X, Y)

        BarDataSet barDataSet = new BarDataSet(entries, "Label");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate(); // refresh
    }
}