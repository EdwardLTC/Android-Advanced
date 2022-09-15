package com.edward.lab1.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.edward.lab1.R;

public class BackGroundService extends Service {
    private static final String TAG = "BackGroundService";
    public MediaPlayer mediaPlayer;
    private boolean isPlaying;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: " );
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song_new);
        }
        mediaPlayer.start();
        isPlaying = true;
        int action = intent.getIntExtra("Action_Music",0);
        HandleMusic(action);
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: " );
    }
    private void HandleMusic(int action) {
        switch (action) {
            case 1:
                if (mediaPlayer != null && isPlaying) {
                    mediaPlayer.pause();
                    isPlaying = false;
                }
                break;
            case 2:
                if (mediaPlayer != null && !isPlaying) {
                    mediaPlayer.start();
                    isPlaying = true;
                }
                break;
            case 3:
                stopSelf();
                break;
        }
    }


}
