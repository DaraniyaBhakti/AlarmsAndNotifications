package com.tatvasoft.tatvasoftassignment8.Activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.tatvasoft.tatvasoftassignment8.BroadcastReceiver.DelayAlarmReceiver;
import com.tatvasoft.tatvasoftassignment8.BroadcastReceiver.TimeAlarmReceiver;
import com.tatvasoft.tatvasoftassignment8.R;
import com.tatvasoft.tatvasoftassignment8.Services.BatteryLowService;
import com.tatvasoft.tatvasoftassignment8.Services.ChargingService;
import com.tatvasoft.tatvasoftassignment8.Services.GPSLocationService;
import com.tatvasoft.tatvasoftassignment8.Services.IdleModeService;
import com.tatvasoft.tatvasoftassignment8.Services.NetworkStateService;
import com.tatvasoft.tatvasoftassignment8.Utils.Constant;
import com.tatvasoft.tatvasoftassignment8.databinding.ActivityMainBinding;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private long timeInMillisecond = 0;
    private int timeInHours = 0;
    private int timeInMin = 0;

    Calendar calendar = Calendar.getInstance();

    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    TimePickerDialog.OnTimeSetListener timeSetListener;
    TimePickerDialog timePickerDialog;

    private ActivityMainBinding binding;
    @SuppressLint("BatteryLife")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notificationChannel);
            String description = getString(R.string.notificationDescription);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(Constant.NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Intent intent = new Intent();
            String packageName = getPackageName();
            PowerManager powerManager = (PowerManager)getSystemService(POWER_SERVICE);
            if(!powerManager.isIgnoringBatteryOptimizations(packageName)){
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:"+packageName));
                startActivity(intent);
            }
        }

        binding.switchBattery.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Intent intent = new Intent(MainActivity.this, BatteryLowService.class);
            if(isChecked){
                startService(intent);
            }else{
                stopService(intent);
            }
        });
        binding.switchCharging.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Intent intent = new Intent(MainActivity.this, ChargingService.class);
            if(isChecked){
                startService(intent);
            }
            else {
                stopService(intent);
            }
        });
        binding.switchNetworkState.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Intent intent = new Intent(MainActivity.this, NetworkStateService.class);
            if(isChecked){
                startService(intent);
            }else {
                stopService(intent);
            }
        });
        binding.switchGPS.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Intent intent = new Intent(MainActivity.this, GPSLocationService.class);
            if(isChecked){
                startService(intent);
            }else {
                stopService(intent);
            }
        });
        binding.switchIdle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Intent intent = new Intent(MainActivity.this, IdleModeService.class);
            if(isChecked){
                startService(intent);
            }else {
                stopService(intent);
            }
        });

        binding.switchDelayAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            final AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            PendingIntent pendingIntent;
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (!binding.etSetDelayTime.getText().toString().isEmpty()){
                        int delay = Integer.parseInt(binding.etSetDelayTime.getText().toString());
                        if(delay>0) {
                            Intent intent = new Intent(MainActivity.this, DelayAlarmReceiver.class);
                            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (delay * 1000L), pendingIntent);
                        }
                    }else {
                        binding.etSetDelayTime.setError(getString(R.string.err_delay));
                        binding.switchDelayAlarm.setChecked(false);
                    }
                }else if (pendingIntent != null){
                    alarmManager.cancel(pendingIntent);
                    binding.etSetDelayTime.setText("0");
                }
            }
        });

        timeSetListener = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            timeInMillisecond = calendar.getTimeInMillis();
            timeInHours = calendar.get(Calendar.HOUR_OF_DAY);
            timeInMin = calendar.get(Calendar.MINUTE);
            binding.etSetAlarm.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()));
        };

        binding.etSetAlarm.setOnClickListener(v1 -> {
            timePickerDialog = new TimePickerDialog(MainActivity.this,
                    timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),false);
            timePickerDialog.show();

        });

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        binding.switchTimeAlarm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                Calendar c = Calendar.getInstance();
                long currentTimeMilliSecond = c.getTimeInMillis();
                if(!binding.etSetAlarm.getText().toString().isEmpty() && timeInMillisecond != 0 && currentTimeMilliSecond<timeInMillisecond){
                    setAlarm(timeInMillisecond);
                }else if(binding.etSetAlarm.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, getString( R.string.toast_setTime),Toast.LENGTH_SHORT).show();
                    binding.switchTimeAlarm.setChecked(false);
                }
            }else if (pendingIntent != null && alarmManager != null){
                alarmManager.cancel(pendingIntent);
                binding.etSetAlarm.getText().clear();
            }
        });
        binding.alarmClearButton.setOnClickListener(v -> {
            binding.etSetAlarm.getText().clear();
            binding.switchTimeAlarm.setChecked(false);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.NOTIFICATION,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(Constant.SWITCH_BATTERY, binding.switchBattery.isChecked());
        editor.putBoolean(Constant.SWITCH_CHARGING, binding.switchCharging.isChecked());
        editor.putBoolean(Constant.SWITCH_GPS, binding.switchGPS.isChecked());
        editor.putBoolean(Constant.SWITCH_NETWORK_STATE, binding.switchNetworkState.isChecked());
        editor.putBoolean(Constant.SWITCH_IDLE, binding.switchIdle.isChecked());
        editor.putBoolean(Constant.SWITCH_TIME_ALARM, binding.switchTimeAlarm.isChecked());

        if(binding.switchTimeAlarm.isChecked()){
            editor.putString(Constant.ALARM_TIME,binding.etSetAlarm.getText().toString());
            editor.putLong(Constant.ALARM_TIME_IN_MILLI_SEC,timeInMillisecond);
            editor.putInt(Constant.TIME_IN_HOURS,timeInHours);
            editor.putInt(Constant.TIME_IN_MIN,timeInMin);
        }

        editor.putInt(Constant.ALARM_DELAY, Integer.parseInt(binding.etSetDelayTime.getText().toString()));
        editor.putBoolean(Constant.SWITCH_DELAY_ALARM, binding.switchDelayAlarm.isChecked());

        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.NOTIFICATION,MODE_PRIVATE);

        binding.switchBattery.setChecked(sharedPreferences.getBoolean(Constant.SWITCH_BATTERY, false));
        binding.switchCharging.setChecked(sharedPreferences.getBoolean(Constant.SWITCH_CHARGING, false));
        binding.switchGPS.setChecked(sharedPreferences.getBoolean(Constant.SWITCH_GPS, false));
        binding.switchNetworkState.setChecked(sharedPreferences.getBoolean(Constant.SWITCH_NETWORK_STATE, false));
        binding.switchIdle.setChecked(sharedPreferences.getBoolean(Constant.SWITCH_IDLE, false));


        if(sharedPreferences.getBoolean(Constant.SWITCH_TIME_ALARM, false)) {
            binding.etSetAlarm.setText(sharedPreferences.getString(Constant.ALARM_TIME, ""));
            timeInMillisecond = sharedPreferences.getLong(Constant.ALARM_TIME_IN_MILLI_SEC,0);
            timeInHours = sharedPreferences.getInt(Constant.TIME_IN_HOURS,0);
            timeInMin = sharedPreferences.getInt(Constant.TIME_IN_MIN,0);

            binding.etSetAlarm.setOnClickListener(v1 -> {
                binding.switchTimeAlarm.setChecked(false);
                timePickerDialog = new TimePickerDialog(MainActivity.this,
                        timeSetListener,
                        timeInHours,
                        timeInMin,false);
                timePickerDialog.show();
            });

        }
        binding.switchTimeAlarm.setChecked(sharedPreferences.getBoolean(Constant.SWITCH_TIME_ALARM, false));

        binding.etSetDelayTime.setText(String.valueOf(sharedPreferences.getInt(Constant.ALARM_DELAY,0)));
        binding.switchDelayAlarm.setChecked(sharedPreferences.getBoolean(Constant.SWITCH_DELAY_ALARM, false));
    }

    public void setAlarm(long time)
    {
        Intent intent = new Intent(MainActivity.this, TimeAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,time,pendingIntent);
        }else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }

    }
    public static void setNotification(Context context,
                                       int smallIcon,
                                       String title,
                                       String text,
                                       String longText,
                                       int id,
                                       Class activity)
    {

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context, Constant.NOTIFICATION_CHANNEL_ID);
        notificationCompat.setSmallIcon(smallIcon);
        notificationCompat.setContentTitle(title);
        notificationCompat.setContentText(text);
        notificationCompat.setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent intent = new Intent(context, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constant.NOTIFICATION_SMALL_ICON,smallIcon);
        intent.putExtra(Constant.NOTIFICATION_TITLE,title);
        intent.putExtra(Constant.NOTIFICATION_TEXT,text);
        intent.putExtra(Constant.NOTIFICATION_LONG_TEXT,longText);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationCompat.setContentIntent(pendingIntent);
        notificationCompat.setAutoCancel(true);
        notificationManagerCompat.notify(id, notificationCompat.build());
    }
}