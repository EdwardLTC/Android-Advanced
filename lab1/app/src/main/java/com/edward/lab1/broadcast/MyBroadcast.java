package com.edward.lab1.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.edward.lab1.service.MusicService;

public class MyBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int action = intent.getIntExtra("Action_Music",0);
        Intent intent1 = new Intent(context, MusicService.class);
        intent1.putExtra("Action_Music",action);
        context.startService(intent1);
    }

}
