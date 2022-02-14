package com.tatvasoft.tatvasoftassignment8.Services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.tatvasoft.tatvasoftassignment8.BroadcastReceiver.BatteryLowReceiver;

public class BatteryLowService extends Service {
    BatteryLowReceiver batteryLowReceiver = new BatteryLowReceiver();
    public BatteryLowService() {
    }
    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(batteryLowReceiver,new IntentFilter(Intent.ACTION_BATTERY_LOW));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryLowReceiver);
    }
    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }
}