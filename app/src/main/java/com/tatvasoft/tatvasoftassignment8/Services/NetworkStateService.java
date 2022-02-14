package com.tatvasoft.tatvasoftassignment8.Services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.tatvasoft.tatvasoftassignment8.BroadcastReceiver.NetworkStateReceiver;

public class NetworkStateService extends Service {
    NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
    IntentFilter intentFilter = new IntentFilter();
    public NetworkStateService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver,intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkStateReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}