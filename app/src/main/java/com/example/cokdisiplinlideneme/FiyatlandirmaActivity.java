package com.example.cokdisiplinlideneme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        setTitle("Fiyatlandırma");


        ArrayList<Integer> colors = new ArrayList<>();

        for(int c : ColorTemplate.VORDIPLOM_COLORS){
            colors.add(c);
        }


        ArrayList<BarEntry> harcamaList_1 = new ArrayList<>();
        ArrayList<BarEntry> fiyatlandirmaList_1 = new ArrayList<>();
        ArrayList<BarEntry> harcamaList_2 = new ArrayList<>();
        ArrayList<BarEntry> fiyatlandirmaList_2 = new ArrayList<>();
        ArrayList<BarEntry> harcamaList_3 = new ArrayList<>();
        ArrayList<BarEntry> fiyatlandirmaList_3 = new ArrayList<>();
        ArrayList<BarEntry> harcamaList_4 = new ArrayList<>();

        float uretim = ((float)getIntent().getIntExtra("Uretim_Degerleri", -1))/1000.0f;
        int tuketimArr[] = getIntent().getIntArrayExtra("Tuketim_Degerleri");
        int maxValue = maxVal(tuketimArr);
        float tuketim[] = new float[3];

        for(int i = 0; i < 3; i++){
            tuketim[i] = ((float)tuketimArr[i])/1000.0f;
        }

        HorizontalBarChart barChartHarcama = findViewById(R.id.horizontalBarChartHarcama);
        HorizontalBarChart barChartFiyatlandirma = findViewById(R.id.horizontalBarChartFiyatlandirma);
        barChartHarcama.getAxisLeft().setAxisMaximum(maxValue + 1);
        barChartFiyatlandirma.getAxisLeft().setAxisMaximum(maxValue + 1);
        barChartHarcama.getAxisLeft().setAxisMinimum(0);
        barChartFiyatlandirma.getAxisLeft().setAxisMinimum(0);
        barChartHarcama.setDrawValueAboveBar(false);
        barChartFiyatlandirma.setDrawValueAboveBar(false);

        Description d1 = new Description();
        d1.setText("kWh");
        Description d2 = new Description();
        d2.setText("TL");
        barChartHarcama.setDescription(d1);
        barChartFiyatlandirma.setDescription(d2);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        ViewGroup.LayoutParams layoutParams1 = barChartHarcama.getLayoutParams();
        layoutParams1.height = displayMetrics.heightPixels * 3 / 10;
        ViewGroup.LayoutParams layoutParams2 = barChartFiyatlandirma.getLayoutParams();
        layoutParams2.height = layoutParams1.height;


        harcamaList_1.add(new BarEntry(1.0f, uretim));
        harcamaList_2.add(new BarEntry(2.0f, tuketim[0]));
        harcamaList_3.add(new BarEntry(3.0f, tuketim[1]));
        harcamaList_4.add(new BarEntry(4.0f, tuketim[2]));

        fiyatlandirmaList_1.add(new BarEntry(1.0f, 0.4612f*tuketim[0]));
        fiyatlandirmaList_2.add(new BarEntry(2.0f, 0.4612f*tuketim[1]));
        fiyatlandirmaList_3.add(new BarEntry(3.0f, 0.4612f*tuketim[2]));

        BarDataSet dataSetHarcama_1 = new BarDataSet(harcamaList_1,"Üretilen Enerji");
        BarDataSet dataSetHarcama_2 = new BarDataSet(harcamaList_2,"1. Ev Tüketim");
        BarDataSet dataSetHarcama_3 = new BarDataSet(harcamaList_3,"2. Ev Tüketim");
        BarDataSet dataSetHarcama_4 = new BarDataSet(harcamaList_4,"3. Ev Tüketim");
        dataSetHarcama_1.setDrawValues(true);
        dataSetHarcama_2.setDrawValues(true);
        dataSetHarcama_3.setDrawValues(true);
        dataSetHarcama_4.setDrawValues(true);
        BarDataSet dataSetFiyatlandirma_1 = new BarDataSet(fiyatlandirmaList_1,"1. Evin Fatura Tutarı");
        BarDataSet dataSetFiyatlandirma_2 = new BarDataSet(fiyatlandirmaList_2,"2. Evin Fatura Tutarı");
        BarDataSet dataSetFiyatlandirma_3 = new BarDataSet(fiyatlandirmaList_3,"3. Evin Fatura Tutarı");
        dataSetFiyatlandirma_1.setDrawValues(true);
        dataSetFiyatlandirma_2.setDrawValues(true);
        dataSetFiyatlandirma_3.setDrawValues(true);
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

        Toast.makeText(this, "Son 24 Saatte "+uretim+" kWh yenilenebilir enerji üretildi ve ev başına "+0.4612f*uretim/3.0f+" TL tasarruf edildi",Toast.LENGTH_LONG).show();
    }

    private int maxVal(int tuketim[]){
        int max = -1;
        for(int i = 0; i < 3; i++){
            if(tuketim[i] > max)
                max = tuketim[i];
        }
        return max;
    }
}
