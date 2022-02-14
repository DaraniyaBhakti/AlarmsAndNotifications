package com.tatvasoft.tatvasoftassignment8.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tatvasoft.tatvasoftassignment8.Activity.MainActivity;
import com.tatvasoft.tatvasoftassignment8.Activity.NotificationActivity;
import com.tatvasoft.tatvasoftassignment8.R;

public class ChargingReceiver extends BroadcastReceiver {

    String text,title,longText;

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            title=context.getResources().getString(R.string.chargerConnectedTitle);
            text=context.getResources().getString(R.string.chargerConnectedShortText);
            longText=context.getResources().getString(R.string.chargerConnectedLongText);

        }
        else if(intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
            title=context.getResources().getString(R.string.chargerDisconnectedTitle);
            text=context.getResources().getString(R.string.chargerDisconnectedShortText);
            longText=context.getResources().getString(R.string.chargerDisconnectedLongText);
        }

        MainActivity.setNotification(context,
                R.drawable.battery_charging,
                title,
                text,
                longText,
                2,
                NotificationActivity.class);
    }
}