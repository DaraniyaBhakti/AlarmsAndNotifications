package com.tatvasoft.tatvasoftassignment8.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.tatvasoft.tatvasoftassignment8.Activity.MainActivity;
import com.tatvasoft.tatvasoftassignment8.Activity.NotificationActivity;
import com.tatvasoft.tatvasoftassignment8.R;

public class IdleModeReceiver extends BroadcastReceiver {

    int smallIcon;
    String text,title,longText;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_SCREEN_ON))
        { smallIcon = R.drawable.ic_baseline_phone_android_24;
            title=context.getResources().getString(R.string.idleTitle);
            text=context.getResources().getString(R.string.idleShortText);
            longText=context.getResources().getString(R.string.idleLongText);

        }
        else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            smallIcon = R.drawable.ic_baseline_mobile_off_24;
            title=context.getResources().getString(R.string.notIdleTitle);
            text=context.getResources().getString(R.string.notIdleShortText);
            longText=context.getResources().getString(R.string.notIdleLongText);

        }
        MainActivity.setNotification(context,
                smallIcon,
                title,
                text,
                longText,
                5,
                NotificationActivity.class,
                5);
    }
}