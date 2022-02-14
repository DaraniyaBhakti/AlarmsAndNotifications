package com.tatvasoft.tatvasoftassignment8.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tatvasoft.tatvasoftassignment8.R;
import com.tatvasoft.tatvasoftassignment8.Utils.Constant;
import com.tatvasoft.tatvasoftassignment8.databinding.ActivityNotificationBinding;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ActivityNotificationBinding binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int smallIcon = getIntent().getIntExtra(Constant.NOTIFICATION_SMALL_ICON,0);
        String title = getIntent().getExtras().getString(Constant.NOTIFICATION_TITLE);
        String text = getIntent().getExtras().getString(Constant.NOTIFICATION_TEXT);
        String longText = getIntent().getExtras().getString(Constant.NOTIFICATION_LONG_TEXT);

        binding.smallIconImg.setImageResource(smallIcon);
        binding.title.setText(title);
        binding.text.setText(text);
        binding.longtext.setText(longText);

    }
}