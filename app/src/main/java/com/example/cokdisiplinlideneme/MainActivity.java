package com.example.cokdisiplinlideneme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.LinkedList;
import java.util.Queue;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private BluetoothListener bluetoothListener = null;
    private int saat;
    private int son24SaatTuketim[];
    private int son24SaatUretim;
    private LinkedList<Integer> son24SaatTuketimDegerler[];
    private LinkedList<Integer> son24SaatUretimDegerler;
    private boolean gunOldu;
    private boolean arttirimmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        son24SaatTuketim = new int[3];
        son24SaatUretim = 0;
        saat = 0;
        gunOldu = false;
        son24SaatTuketimDegerler = new LinkedList[3];
        son24SaatUretimDegerler = new LinkedList<>();
        for(int i = 0; i < 3; i++)
            son24SaatTuketimDegerler[i] = new LinkedList<>();

        Button buttonKarbonAyakizi = findViewById(R.id.buttonKarbonAyakizi);
        Button buttonYenilenebilirSebeke = findViewById(R.id.buttonYenilenebilirSebeke);
        Button buttonFiyatlandirma = findViewById(R.id.buttonFiyatlandirma);

        buttonKarbonAyakizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KarbonAyakiziActivity.class);
                intent.putExtra("Tuketim_Degerleri",son24SaatTuketim);
                intent.putExtra("Uretim_Degerleri",son24SaatUretim);
                intent.putExtra("Saat","00");
                startActivity(intent);
            }
        });

        buttonYenilenebilirSebeke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, YenilenebilirSebekeActivity.class);
                intent.putExtra("Tuketim_Degerleri",son24SaatTuketim);
                intent.putExtra("Uretim_Degerleri",son24SaatUretim);
                intent.putExtra("Saat","00");
                startActivity(intent);
            }
        });

        buttonFiyatlandirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FiyatlandirmaActivity.class);
                intent.putExtra("Tuketim_Degerleri",son24SaatTuketim);
                intent.putExtra("Uretim_Degerleri",son24SaatUretim);
                intent.putExtra("Saat","00");
                startActivity(intent);
            }
        });

        try {
             bluetoothListener = new BluetoothListener(this );
             bluetoothListener.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getMessage(int evTuketim[], int uretim){
        /*Bunlari biseyler yap.*/
        for(int i = 0; i < 3; i++){
            son24SaatTuketimDegerler[i].addLast(evTuketim[i]);
            son24SaatTuketim[i] += evTuketim[i];
        }
        son24SaatUretimDegerler.addLast(uretim);
        son24SaatUretim += uretim;
        if(gunOldu){
            for(int i = 0; i < 3; i++) {
                son24SaatTuketim[i] -= son24SaatTuketimDegerler[i].getFirst();
                son24SaatTuketimDegerler[i].removeFirst();
            }
            son24SaatUretim -= son24SaatUretimDegerler.getFirst();
            son24SaatUretimDegerler.removeFirst();
        }
        //bluetoothListener.execute();

    }

    public void saatArttir(){
        saat = (saat + 1)%24;
        if(arttirimmi){
            gunOldu = true;
            arttirimmi =false;
        }
        if(!gunOldu && saat == 0)
            arttirimmi=true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: enter");
        if(requestCode == 0 && resultCode == RESULT_OK){
            bluetoothListener.getDevice();
        }
        if(requestCode == 1){
            try {
                bluetoothListener.getDevice();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
