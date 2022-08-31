package com.edward.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.edward.test1.service.service1;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "channel_service";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createChannelNotification();

        EditText text = findViewById(R.id.intentData);

        Intent intent = new Intent(this, service1.class);

        findViewById(R.id.clickStart).setOnClickListener(view -> {

            intent.putExtra("key_Data_intent",text.getText().toString().trim());
            startService(intent);
        });

        findViewById(R.id.clickStop).setOnClickListener(view -> {
            stopService(intent);
        });
    }

    public void createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel Service",
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(channel);
        }
    }
}