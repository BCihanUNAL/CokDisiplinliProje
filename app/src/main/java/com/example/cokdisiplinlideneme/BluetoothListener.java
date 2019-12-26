package com.example.cokdisiplinlideneme;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;



public class BluetoothListener extends AsyncTask<Void, Void, byte[]> {
    private static final String TAG = "BluetoothListener";
    private Context context = null;

    private String deviceAddress = null;
    private String deviceName = null;
    private BluetoothDevice bluetoothDevice = null;
    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothSocket bluetoothSocket = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private DataInputStream dataInputStream = null;
    
    private static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");



    public BluetoothListener(Context context) throws IOException {
        this.context = context;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null)
            throw new UnsupportedOperationException();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            ((Activity)context).startActivityForResult(enableBtIntent, 0);
            while(MainActivity.activateLock);
            Log.d(TAG, "BluetoothListener: hata");

        }

        getDevice();

        
    }

    public void getDevice(){
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        for(BluetoothDevice device : pairedDevices){
            if(device.getName().toString().equals("Kumbasar")) {
                deviceName = "Kumbasar";
                deviceAddress = device.getAddress().toString();
                Log.d(TAG, "getDevice: "+deviceName+" "+deviceAddress);
            }
        }

        Log.d(TAG, "BluetoothListener: ");
        if(deviceAddress == null){
            Toast.makeText(context,"Lütfen HC-05 modulü ile cihazınızı bluetooth üzerinden eşleştiriniz",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.bluetooth.BluetoothSettings");
            intent.setComponent(cn);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ((Activity)context).startActivityForResult(intent, 1);
            while(MainActivity.askLock);
        }

        try {
            setup();
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public void setup() throws IOException{
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddress);
        bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(myUUID);
        bluetoothSocket.connect();

        inputStream = bluetoothSocket.getInputStream();
        outputStream = bluetoothSocket.getOutputStream();
        dataInputStream = new DataInputStream(inputStream);
        Log.d(TAG, "setup: oldu");
        MainActivity.askLock = MainActivity.activateLock = true;
    }

    @Override
    protected void onPostExecute(byte[] bytes) {
        super.onPostExecute(bytes);

        int evTuketim[] = new int[3];
        int uretim = 0;
        for(int i = 0; i < 4; i++){
            int val = (int)bytes[3-i];
            if(val < 0)
                val+=256;

            uretim += val << 8*i;
        }
        for(int j = 0; j < 3; j++){
            for(int i = 0; i < 4; i++){
                int val = (int)bytes[((j+1)*4)+3-i];
                if(val < 0)
                    val += 256;
                Log.d(TAG, "onPostExecute: val="+val);

                evTuketim[j] += val << 8*i;
            }
        }
          /*  Log.d(TAG, "doInBackground: "+uretim+" "+evTuketim[0]+" "+evTuketim[1]+" "+evTuketim[2]);
            Log.d(TAG, "doInBackground: cikti");*/
        ((MainActivity)context).saatArttir((int)bytes[16]);
        ((MainActivity)context).getMessage(evTuketim, uretim);
        try {
            bluetoothSocket.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected byte[] doInBackground(Void... params){
        byte buffer [] = new byte[17];


            try {
                dataInputStream.readFully(buffer, 0, 17);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 16; i++)
                Log.d(TAG, "doInBackground: buffer: " + i + " " + buffer[i]);


        return buffer;

    }



}
