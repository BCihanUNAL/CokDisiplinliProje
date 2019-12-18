package com.example.cokdisiplinlideneme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class FiyatlandirmaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiyatlandirma);
        TextView tv = findViewById(R.id.tvFiyatlandirmaSaat);
        tv.setText("Saat = "+getIntent().getStringExtra("Saat")+":00");

        ArrayList<Integer> colors = new ArrayList<>();

        for(int c : ColorTemplate.VORDIPLOM_COLORS){
            colors.add(c);
        }


        HorizontalBarChart barChartHarcama = findViewById(R.id.horizontalBarChartHarcama);
        HorizontalBarChart barChartFiyatlandirma = findViewById(R.id.horizontalBarChartFiyatlandirma);
        Legend legendHarcama = barChartFiyatlandirma.getLegend();
        Legend legendFiyatlandirma = barChartHarcama.getLegend();

        Description d = new Description();
        d.setText("");
        barChartHarcama.setDescription(d);
        barChartFiyatlandirma.setDescription(d);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        ViewGroup.LayoutParams layoutParams1 = barChartHarcama.getLayoutParams();
        layoutParams1.height = displayMetrics.heightPixels * 7 / 20;
        ViewGroup.LayoutParams layoutParams2 = barChartFiyatlandirma.getLayoutParams();
        layoutParams2.height = layoutParams1.height;

        ArrayList<BarEntry> harcamaList_1 = new ArrayList<>();
        ArrayList<BarEntry> fiyatlandirmaList_1 = new ArrayList<>();
        ArrayList<BarEntry> harcamaList_2 = new ArrayList<>();
        ArrayList<BarEntry> fiyatlandirmaList_2 = new ArrayList<>();
        ArrayList<BarEntry> harcamaList_3 = new ArrayList<>();
        ArrayList<BarEntry> fiyatlandirmaList_3 = new ArrayList<>();
        ArrayList<BarEntry> harcamaList_4 = new ArrayList<>();



        harcamaList_1.add(new BarEntry(1.0f, 80.0f));
        harcamaList_2.add(new BarEntry(2.0f, 90.0f));
        harcamaList_3.add(new BarEntry(3.0f, 65.0f));
        harcamaList_4.add(new BarEntry(4.0f, 105.0f));

        fiyatlandirmaList_1.add(new BarEntry(2.0f, 90.0f));
        fiyatlandirmaList_2.add(new BarEntry(3.0f, 65.0f));
        fiyatlandirmaList_3.add(new BarEntry(4.0f, 105.0f));

        BarDataSet dataSetHarcama_1 = new BarDataSet(harcamaList_1,"Üretilen Enerji");
        BarDataSet dataSetHarcama_2 = new BarDataSet(harcamaList_2,"1. Evin Tüketimi");
        BarDataSet dataSetHarcama_3 = new BarDataSet(harcamaList_3,"2. Evin Tüketimi");
        BarDataSet dataSetHarcama_4 = new BarDataSet(harcamaList_4,"3. Evin Tüketimi");
        BarDataSet dataSetFiyatlandirma_1 = new BarDataSet(fiyatlandirmaList_1,"1. Evin Fatura Tutarı");
        BarDataSet dataSetFiyatlandirma_2 = new BarDataSet(fiyatlandirmaList_2,"2. Evin Fatura Tutarı");
        BarDataSet dataSetFiyatlandirma_3 = new BarDataSet(fiyatlandirmaList_3,"3. Evin Fatura Tutarı");
        dataSetHarcama_1.setColors(colors.get(0));
        dataSetHarcama_2.setColors(colors.get(1));
        dataSetHarcama_3.setColors(colors.get(2));
        dataSetHarcama_4.setColors(colors.get(3));
        dataSetFiyatlandirma_1.setColors(colors.get(0));
        dataSetFiyatlandirma_2.setColors(colors.get(1));
        dataSetFiyatlandirma_3.setColors(colors.get(2));

        ArrayList<IBarDataSet> dataSetsHarcama = new ArrayList<>();
        dataSetsHarcama.add(dataSetHarcama_1);
        dataSetsHarcama.add(dataSetHarcama_2);
        dataSetsHarcama.add(dataSetHarcama_3);
        dataSetsHarcama.add(dataSetHarcama_4);

        BarData dataHarcama = new BarData(dataSetsHarcama);

        barChartHarcama.setData(dataHarcama);

        ArrayList<IBarDataSet> dataSetsFiyatlandirma = new ArrayList<>();
        dataSetsFiyatlandirma.add(dataSetFiyatlandirma_1);
        dataSetsFiyatlandirma.add(dataSetFiyatlandirma_2);
        dataSetsFiyatlandirma.add(dataSetFiyatlandirma_3);

        BarData dataFiyatlandirma = new BarData(dataSetsFiyatlandirma);

        barChartFiyatlandirma.setData(dataFiyatlandirma);
    }
}
