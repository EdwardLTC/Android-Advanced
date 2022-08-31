package com.edward.TestService.service;

import static com.edward.TestService.MainActivity.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.edward.TestService.R;
import com.edward.TestService.modal.song;

public class service1 extends Service {
    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Bundle bundle = intent.getExtras();
        song Song = (song) bundle.get("object");

        if (Song!=null){
            if (mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(getApplicationContext(),Song.getResource());
            }
            mediaPlayer.start();
        }

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.music_notification);
        if (Song!=null){
            remoteViews.setTextViewText(R.id.title, Song.getTitle());
            remoteViews.setTextViewText(R.id.author,Song.getAuthor());
        }

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID).setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent).setCustomContentView(remoteViews).build();
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
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
