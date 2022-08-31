package com.edward.test1.service;

import static com.edward.test1.MainActivity.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class service1 extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String textIntent = intent.getStringExtra("key_Data_intent");
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID).setContentTitle("test")
                .setContentText(textIntent).setContentIntent(pendingIntent).build();
        startForeground(1,notification);
        return START_NOT_STICKY;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate() {
        Log.e("Service has been started", "onCreate: ");
        super.onCreate();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onDestroy() {
        Log.e("Service has been stopped", "onDestroy: ");
        super.onDestroy();
    }
}
