package com.example.cokdisiplinlideneme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class YenilenebilirSebekeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yenilenebilir_sebeke);
        TextView tv = findViewById(R.id.tvYenilenebilirSebekeSaat);
        tv.setText("Saat = "+getIntent().getStringExtra("Saat")+":00");

        PieChart mChart1 = (PieChart) findViewById(R.id.pieChartYenilenebilirSebekeEv1);
        PieChart mChart2 = (PieChart) findViewById(R.id.pieChartYenilenebilirSebekeEv2);
        PieChart mChart3 = (PieChart) findViewById(R.id.pieChartYenilenebilirSebekeEv3);
        ArrayList<PieEntry> yValues = new ArrayList<>();
        mChart1.setUsePercentValues(true);
        mChart1.setDrawHoleEnabled(true);
        mChart2.setUsePercentValues(true);
        mChart2.setDrawHoleEnabled(true);
        mChart3.setUsePercentValues(true);
        mChart3.setDrawHoleEnabled(true);

        yValues.add(new PieEntry(2,30f));
        yValues.add(new PieEntry(1,30f));

        PieDataSet set1 = new PieDataSet(yValues, "Yenilenebilir/Şebeke");

        Description description1 = new Description();
        description1.setText("Ev1");
        mChart1.setDescription(description1);
        Description description2 = new Description();
        description2.setText("Ev2");
        mChart2.setDescription(description2);
        Description description3 = new Description();
        description3.setText("Ev3");
        mChart3.setDescription(description3);

        ArrayList<Integer> colors = new ArrayList<>();

        for(int c : ColorTemplate.VORDIPLOM_COLORS){
            colors.add(c);
        }

        set1.setColors(colors);

        PieData data = new PieData(set1);
        mChart1.setUsePercentValues(true);
        mChart2.setUsePercentValues(true);
        mChart3.setUsePercentValues(true);
        mChart1.setData(data);
        mChart2.setData(data);
        mChart3.setData(data);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        ViewGroup.LayoutParams layoutParams1 = mChart1.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = mChart2.getLayoutParams();
        ViewGroup.LayoutParams layoutParams3 = mChart3.getLayoutParams();
        layoutParams1.height = displayMetrics.heightPixels * 2 / 5;
        layoutParams2.height = layoutParams1.height;
        layoutParams3.height = layoutParams1.height;
        layoutParams1.width = displayMetrics.widthPixels / 3;
        layoutParams2.width = layoutParams1.width;
        layoutParams3.width = layoutParams1.width;
    }
}
