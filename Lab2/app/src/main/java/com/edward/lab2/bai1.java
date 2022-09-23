package com.edward.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class bai1 extends AppCompatActivity {
    MyBroadcastbai2 myBroadcastbai2 = new MyBroadcastbai2();
    IntentFilter  intentFilter = new IntentFilter("dulieumoi");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1);
        EditText edtInput = findViewById(R.id.edtInput);
        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = edtInput.getText().toString();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("data", data);
                intent.putExtras(bundle);
                intent.setAction("dulieumoi");
                sendBroadcast(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myBroadcastbai2, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBroadcastbai2);
    }
}