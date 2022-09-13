package com.edward.lab1.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.edward.lab1.MainActivity;
import com.edward.lab1.R;
import com.edward.lab1.broadcast.MyBroadcast;
import com.edward.lab1.modal.Song;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;

    private static final String CHANNEL_ID = "MyChannel";
    private static final String CHANNEL_NAME = "Channel Service";
    private static final int ACTION_PAUSE = 1;
    private static final int ACTION_RESUME = 2;
    private static final int ACTION_CLEAR = 3;
    private boolean isPlaying;
    private Song mSong;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
           Song song = (Song) bundle.get("object");
            if (song != null) {
                mSong = song;
                StartMusic(song);
                SendNotification(song);
            }

        }
        int action = intent.getIntExtra("Action_Music",0);
        HandleMusic(action);
        return START_NOT_STICKY;
    }

    private void StartMusic(Song song) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), song.getResource());
        }
        mediaPlayer.setOnCompletionListener(mediaPlayer1 -> {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), song.getResource());
            mediaPlayer.start();
            isPlaying = true;
        });
        mediaPlayer.start();
        isPlaying = true;
    }

    private void HandleMusic(int action) {
        switch (action) {
            case ACTION_PAUSE:
                if (mediaPlayer != null && isPlaying) {
                    mediaPlayer.pause();
                    isPlaying = false;
                    SendNotification(mSong);
                }
                break;
            case ACTION_RESUME:
                if (mediaPlayer != null && !isPlaying) {
                    mediaPlayer.start();
                    isPlaying = true;
                    SendNotification(mSong);
                }
                break;
            case ACTION_CLEAR:
                stopSelf();
                break;
        }
    }

    private void SendNotification(Song song) {
        Intent intent = new Intent(this, MainActivity.class);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.title, song.getTitle());
        remoteViews.setTextViewText(R.id.author, song.getAuthor());

        if (isPlaying){
            remoteViews.setOnClickPendingIntent(R.id.play_or_pause,getPending(getApplicationContext(),ACTION_PAUSE));
            remoteViews.setImageViewResource(R.id.play_or_pause,R.drawable.ic_baseline_play_arrow_24);
        }
        else {
            remoteViews.setOnClickPendingIntent(R.id.play_or_pause,getPending(getApplicationContext(),ACTION_RESUME));
            remoteViews.setImageViewResource(R.id.play_or_pause,R.drawable.ic_baseline_pause_24);
        }

        remoteViews.setOnClickPendingIntent(R.id.clear,getPending(getApplicationContext(),ACTION_CLEAR));

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setSound(null)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews).build();
        startForeground(1, notification);
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private PendingIntent getPending(Context context, int action){
        Intent  intent = new Intent(context, MyBroadcast.class);
        intent.putExtra("Action_Music",action);
        return PendingIntent.getBroadcast(getApplicationContext(),action,intent,PendingIntent.FLAG_UPDATE_CURRENT);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        }
    }
}
