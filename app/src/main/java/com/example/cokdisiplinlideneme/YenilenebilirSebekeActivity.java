package com.example.cokdisiplinlideneme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        setTitle("Yenilenebilir Şebeke Tüketim Oranı");

        PieChart mChart1 = (PieChart) findViewById(R.id.pieChartYenilenebilirSebekeEv1);
        PieChart mChart2 = (PieChart) findViewById(R.id.pieChartYenilenebilirSebekeEv2);
        PieChart mChart3 = (PieChart) findViewById(R.id.pieChartYenilenebilirSebekeEv3);

        ArrayList<PieEntry> yValues1 = new ArrayList<>();
        ArrayList<PieEntry> yValues2 = new ArrayList<>();
        ArrayList<PieEntry> yValues3 = new ArrayList<>();
        mChart1.setUsePercentValues(true);
        mChart1.setDrawHoleEnabled(true);
        mChart2.setUsePercentValues(true);
        mChart2.setDrawHoleEnabled(true);
        mChart3.setUsePercentValues(true);
        mChart3.setDrawHoleEnabled(true);


        final float uretim = ((float)getIntent().getIntExtra("Uretim_Degerleri", -1))/3000.0f;
        int tuketimArr[] = getIntent().getIntArrayExtra("Tuketim_Degerleri");
        final float tuketim[] = new float[3];
        for(int i = 0; i < 3; i++){
            tuketim[i] = ((float)tuketimArr[i])/1000.0f;
        }

        mChart1.setOnTouchListener(null);
        mChart2.setOnTouchListener(null);
        mChart3.setOnTouchListener(null);
        mChart1.setClickable(true);
        mChart2.setClickable(true);
        mChart3.setClickable(true);

        mChart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(YenilenebilirSebekeActivity.this, "Ev1 için "+uretim+" kWh Yenilenebilir Enerji Üretildi, Şebekeden "+tuketim[0]+" kWh Enerji Tüketildi",Toast.LENGTH_LONG).show();
            }
        });

        mChart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(YenilenebilirSebekeActivity.this, "Ev2 için "+uretim+" kWh Yenilenebilir Enerji Üretildi, Şebekeden "+tuketim[1]+" kWh Enerji Tüketildi",Toast.LENGTH_LONG).show();
            }
        });

        mChart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(YenilenebilirSebekeActivity.this, "Ev3 için "+uretim+" kWh Yenilenebilir Enerji Üretildi, Şebekeden "+tuketim[2]+" kWh Enerji Tüketildi",Toast.LENGTH_LONG).show();
            }
        });


        yValues1.add(new PieEntry(uretim));
        yValues1.add(new PieEntry(tuketim[0]));
        yValues2.add(new PieEntry(uretim));
        yValues2.add(new PieEntry(tuketim[1]));
        yValues3.add(new PieEntry(uretim));
        yValues3.add(new PieEntry(tuketim[2]));

        PieDataSet set1 = new PieDataSet(yValues1, "Yenilenebilir/Şebeke");
        PieDataSet set2 = new PieDataSet(yValues2, "Yenilenebilir/Şebeke");
        PieDataSet set3 = new PieDataSet(yValues3, "Yenilenebilir/Şebeke");

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
        set2.setColors(colors);
        set3.setColors(colors);

        PieData data_1 = new PieData(set1);
        PieData data_2 = new PieData(set2);
        PieData data_3 = new PieData(set3);
        mChart1.setUsePercentValues(true);
        mChart2.setUsePercentValues(true);
        mChart3.setUsePercentValues(true);
        mChart1.setData(data_1);
        mChart2.setData(data_2);
        mChart3.setData(data_3);


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
