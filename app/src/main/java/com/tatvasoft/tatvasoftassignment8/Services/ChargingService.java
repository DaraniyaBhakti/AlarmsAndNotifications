package com.tatvasoft.tatvasoftassignment8.Services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.tatvasoft.tatvasoftassignment8.BroadcastReceiver.ChargingReceiver;

public class ChargingService extends Service {
    ChargingReceiver chargingReceiver = new ChargingReceiver();
    IntentFilter intentFilter = new IntentFilter();
    public ChargingService() {
    }
    @Override
    public void onCreate() {
        super.onCreate();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(chargingReceiver,intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(chargingReceiver);
    }
    @Override
    public IBinder onBind(Intent intent) {
     return null;
    }
}