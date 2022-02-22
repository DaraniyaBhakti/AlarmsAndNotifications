package com.tatvasoft.tatvasoftassignment8.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.tatvasoft.tatvasoftassignment8.Activity.MainActivity;
import com.tatvasoft.tatvasoftassignment8.Activity.NotificationActivity;
import com.tatvasoft.tatvasoftassignment8.R;

public class GPSLocationReceiver extends BroadcastReceiver {

    String text,title,longText;
    int smallIcon;
    @Override
    public void onReceive(Context context, Intent intent) {

        LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager!=null) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                smallIcon = R.drawable.location_on;
                title = context.getResources().getString(R.string.gpsEnableTitle);
                text = context.getResources().getString(R.string.gpsEnableShortText);
                longText = context.getResources().getString(R.string.gpsEnableLongText);
            } else {
                smallIcon = R.drawable.ic_baseline_location_off_24;
                title = context.getResources().getString(R.string.gpsDisableTitle);
                text = context.getResources().getString(R.string.gpsDisableShortText);
                longText = context.getResources().getString(R.string.gpsDisableLongText);
            }
        }
        MainActivity.setNotification(context,
                smallIcon,
                title,
                text,
                longText,
                4,
                NotificationActivity.class,
                4);
    }
}