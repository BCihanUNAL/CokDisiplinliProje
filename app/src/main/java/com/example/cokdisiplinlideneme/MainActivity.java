package com.example.cokdisiplinlideneme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private BluetoothListener bluetoothListener = null;
    private int saat;
    private int saatKontrol;
    private int son24SaatTuketim[];
    private int son24SaatUretim;
    private LinkedList<Integer> son24SaatTuketimDegerler[];
    private LinkedList<Integer> son24SaatUretimDegerler;
    private boolean gunOldu;
    protected static boolean activateLock;
    protected static boolean askLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Akıllı Ev Denetim Arayüzü");

        activateLock = true;
        askLock = true;
        son24SaatTuketim = new int[3];
        son24SaatUretim = 0;
        saat = -1;
        saatKontrol = 0;
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
                /*Uygulamadan gorsel almak istersen bu commentleri silip asagidaki kod parcasini commentle
                Intent intent = new Intent(MainActivity.this, FiyatlandirmaActivity.class);
                intent.putExtra("Tuketim_Degerleri",new int[]{6500,5700,12111});
                intent.putExtra("Uretim_Degerleri",2100);
                intent.putExtra("Saat",11);
                startActivity(intent);*/
                Intent intent = new Intent(MainActivity.this, KarbonAyakiziActivity.class);
                intent.putExtra("Tuketim_Degerleri",son24SaatTuketim);
                intent.putExtra("Uretim_Degerleri",son24SaatUretim);
                intent.putExtra("Saat",Integer.toString(saat));
                startActivity(intent);
            }
        });

        buttonYenilenebilirSebeke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Uygulamadan gorsel almak istersen bu commentleri silip asagidaki kod parcasini commentle
                Intent intent = new Intent(MainActivity.this, FiyatlandirmaActivity.class);
                intent.putExtra("Tuketim_Degerleri",new int[]{6500,5700,12111});
                intent.putExtra("Uretim_Degerleri",2100);
                intent.putExtra("Saat",11);
                startActivity(intent);*/
                Intent intent = new Intent(MainActivity.this, YenilenebilirSebekeActivity.class);
                intent.putExtra("Tuketim_Degerleri",son24SaatTuketim);
                intent.putExtra("Uretim_Degerleri",son24SaatUretim);
                intent.putExtra("Saat",Integer.toString(saat));
                startActivity(intent);
            }
        });

        buttonFiyatlandirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Uygulamadan gorsel almak istersen bu commentleri silip asagidaki kod parcasini commentle
                Intent intent = new Intent(MainActivity.this, FiyatlandirmaActivity.class);
                intent.putExtra("Tuketim_Degerleri",new int[]{6500,5700,12111});
                intent.putExtra("Uretim_Degerleri",2100);
                intent.putExtra("Saat",11);
                startActivity(intent);*/
                Intent intent = new Intent(MainActivity.this, FiyatlandirmaActivity.class);
                intent.putExtra("Tuketim_Degerleri",son24SaatTuketim);
                intent.putExtra("Uretim_Degerleri",son24SaatUretim);
                intent.putExtra("Saat",Integer.toString(saat));
                startActivity(intent);
            }
        });

        try {
             //Uygulamadan gorselleri almak istersen bu try catch blogunu comment icine al
             bluetoothListener = new BluetoothListener(this );
             bluetoothListener.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getMessage(int evTuketim[], int uretim){
        for(int i = 0; i < 3; i++){
            son24SaatTuketim[i] = evTuketim[i];
        }
        son24SaatUretim = uretim;

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            bluetoothListener = new BluetoothListener(MainActivity.this);
                            bluetoothListener.execute();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask,1000);
    }

    public void saatArttir(int saat){
        if(this.saat>=0)
            this.saat = (saat + 1) % 24;
        if(this.saat == saatKontrol)
            gunOldu = true;
        if(this.saat < 0){
            this.saat = saatKontrol = saat;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: enter");
        if(requestCode == 0 && resultCode == RESULT_OK){
            activateLock = false;
        }
        if(requestCode == 1){
            try {
                askLock = false;
                bluetoothListener.getDevice();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
