package com.tatvasoft.tatvasoftassignment8.Services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.IBinder;

import com.tatvasoft.tatvasoftassignment8.BroadcastReceiver.GPSLocationReceiver;

public class GPSLocationService extends Service {
    GPSLocationReceiver gpsLocationReceiver = new GPSLocationReceiver();
    IntentFilter intentFilter = new IntentFilter();
    public GPSLocationService() {
    }
    @Override
    public void onCreate() {
        super.onCreate();
        intentFilter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        registerReceiver(gpsLocationReceiver,intentFilter);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(gpsLocationReceiver);
    }
    @Override
    public IBinder onBind(Intent intent) {
      return null;
    }
}