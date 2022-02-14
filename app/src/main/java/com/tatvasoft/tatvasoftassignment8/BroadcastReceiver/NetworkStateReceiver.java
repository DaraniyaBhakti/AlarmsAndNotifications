package com.tatvasoft.tatvasoftassignment8.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tatvasoft.tatvasoftassignment8.Activity.MainActivity;
import com.tatvasoft.tatvasoftassignment8.Activity.NotificationActivity;
import com.tatvasoft.tatvasoftassignment8.R;

public class NetworkStateReceiver extends BroadcastReceiver {

    int smallIcon;
    String text,title,longText;
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null){
            if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                smallIcon = R.drawable.wifi;
                title = context.getResources().getString(R.string.wifiTitle);
                text = context.getResources().getString(R.string.wifiShortText);
                longText = context.getResources().getString(R.string.wifiLongText);
            }else if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                smallIcon = R.drawable.ic_baseline_network_cell_24;
                title = context.getResources().getString(R.string.mobileTitle);
                text = context.getResources().getString(R.string.mobileShortText);
                longText = context.getResources().getString(R.string.mobileLongText);
            }

        }else {
            smallIcon = R.drawable.ic_baseline_signal_cellular_off_24;
            title = context.getResources().getString(R.string.notConnectedTitle);
            text = context.getResources().getString(R.string.notConnectedShortText);
            longText = context.getResources().getString(R.string.notConnectedLongText);
        }
        MainActivity.setNotification(context,
                smallIcon,
                title,
                text,
                longText,
                3,
                NotificationActivity.class);
    }
}