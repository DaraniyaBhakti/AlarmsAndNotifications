package com.tatvasoft.tatvasoftassignment8.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tatvasoft.tatvasoftassignment8.Activity.MainActivity;
import com.tatvasoft.tatvasoftassignment8.Activity.NotificationActivity;
import com.tatvasoft.tatvasoftassignment8.R;

public class BatteryLowReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BATTERY_LOW))
        {

            MainActivity.setNotification(context,
                    R.drawable.battery_low,
                    context.getResources().getString(R.string.batteryTitle),
                    context.getResources().getString(R.string.batteryShortText),
                    context.getResources().getString(R.string.batteryLongText),
                    1,
                    NotificationActivity.class
                    ,1);
        }
    }
}