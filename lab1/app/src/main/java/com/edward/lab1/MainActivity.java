package com.edward.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.edward.lab1.modal.Song;
import com.edward.lab1.service.MusicService;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "MyChannel";
    private static final String CHANNEL_NAME = "Channel Service";
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitChannelNotification();
        intent = new Intent(MainActivity.this, MusicService.class);
        findViewById(R.id.start).setOnClickListener(view -> {
            Song song = new Song("Nhac nay do lam :>", "Edward", R.raw.song);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object", song);
            intent.putExtras(bundle);
            startService(intent);
        });
        findViewById(R.id.destroy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intent);
            }
        });
    }

    private void InitChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(null, null);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
    }

}