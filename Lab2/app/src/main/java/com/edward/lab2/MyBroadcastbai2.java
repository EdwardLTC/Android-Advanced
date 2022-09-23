package com.edward.lab2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MyBroadcastbai2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String data = bundle.getString("data");
        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
    }

}
