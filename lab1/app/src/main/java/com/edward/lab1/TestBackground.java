package com.edward.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.edward.lab1.service.BackGroundService;

public class TestBackground extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_background);
        findViewById(R.id.clickme).setOnClickListener(view -> {
            Log.e("ASasd", "onClick: " );
            Intent intent = new Intent(TestBackground.this, BackGroundService.class);
            startService(intent);
        });
    }
}