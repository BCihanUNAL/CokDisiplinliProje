package com.example.cokdisiplinlideneme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class KarbonAyakiziActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karbon_ayakizi);
        TextView tv = findViewById(R.id.tvKarbonAyakiziSaat);
        tv.setText("Saat = "+getIntent().getStringExtra("Saat")+":00");
        setTitle("Oluşan Karbon Ayak İzi Değerleri");

        BarChart mChart = (BarChart) findViewById(R.id.barChartKarbonAyakizi);

        float uretim = ((float)getIntent().getIntExtra("Uretim_Degerleri", -1))/3000.0f;
        int tuketimArr[] = getIntent().getIntArrayExtra("Tuketim_Degerleri");
        float tuketim[] = new float[3];
        for(int i = 0; i < 3; i++){
            tuketim[i] = ((float)tuketimArr[i])/1000.0f;
        }

        Toast.makeText(this, "Yenilenebilir Enerji Sayesinde Ev Başına "+Float.toString(0.4483f*uretim)+" Kg daha az CO2 üretildi",Toast.LENGTH_LONG).show();

        ArrayList<BarEntry> yValues = new ArrayList<>();
        ArrayList<BarEntry> yValues1 = new ArrayList<>();
        ArrayList<BarEntry> yValues2 = new ArrayList<>();

        yValues.add(new BarEntry(1f,0.4483f*tuketim[0]));

        yValues1.add(new BarEntry(2f,0.4483f*tuketim[1]));

        yValues2.add(new BarEntry(3f,0.4483f*tuketim[2]));


        ArrayList<Integer> colors = new ArrayList<>();

        for(int c : ColorTemplate.VORDIPLOM_COLORS){
            colors.add(c);
        }

        BarDataSet set1 = new BarDataSet(yValues, "Ev1");
        BarDataSet set2 = new BarDataSet(yValues1, "Ev2");
        BarDataSet set3 = new BarDataSet(yValues2, "Ev3");
        set1.setDrawValues(true);
        set2.setDrawValues(true);
        set3.setDrawValues(true);
        set1.setColor(colors.get(0));
        set2.setColor(colors.get(1));
        set3.setColor(colors.get(2));
        Description description = new Description();
        description.setText("CO2");
        mChart.setDescription(description);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);

        BarData data = new BarData(dataSets);

        mChart.setData(data);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        ViewGroup.LayoutParams layoutParams = mChart.getLayoutParams();
        layoutParams.height = displayMetrics.heightPixels * 2 / 5;

    }
}
