package com.tatvasoft.tatvasoftassignment8.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tatvasoft.tatvasoftassignment8.Activity.MainActivity;
import com.tatvasoft.tatvasoftassignment8.Activity.NotificationActivity;
import com.tatvasoft.tatvasoftassignment8.R;

public class TimeAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MainActivity.setNotification(context,
                R.drawable.ic_baseline_access_alarm_24,
                context.getResources().getString(R.string.timeAlarmTitle),
                context.getResources().getString(R.string.timeAlarmShortText),
                context.getResources().getString(R.string.timeAlarmLongText),
                6,
                NotificationActivity.class);

    }
}