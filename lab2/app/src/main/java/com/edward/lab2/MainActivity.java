package com.edward.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmTimePicker = findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            long time;
            @Override
            public void onClick(View view) {
                if (toggleButton.isChecked()){
                    Toast.makeText(MainActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                    calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());

//                    time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                    time =System.currentTimeMillis()+5000;
//                    if (System.currentTimeMillis() > time) {
//                        if (Calendar.AM_PM == 0)
//                            time = time + (1000 * 60 * 60 * 12);
//                        else
//                            time = time + (1000 * 60 * 60 * 24);
//                    }
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                }
                else {
                    alarmManager.cancel(pendingIntent);
                    pendingIntent.cancel();
                    Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}