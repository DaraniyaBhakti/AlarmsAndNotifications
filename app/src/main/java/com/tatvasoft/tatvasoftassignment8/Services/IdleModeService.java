package com.tatvasoft.tatvasoftassignment8.Services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import com.tatvasoft.tatvasoftassignment8.BroadcastReceiver.IdleModeReceiver;

public class IdleModeService extends Service {
    IdleModeReceiver idleModeReceiver = new IdleModeReceiver();
    IntentFilter intentFilter = new IntentFilter();
    public IdleModeService() {
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate() {
        super.onCreate();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(idleModeReceiver,intentFilter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(idleModeReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }
}