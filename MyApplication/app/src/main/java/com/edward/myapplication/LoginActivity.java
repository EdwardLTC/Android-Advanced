package com.edward.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.edward.myapplication.dao.DataAccessObject;
import com.edward.myapplication.models.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DataAccessObject dataAccessObject = new DataAccessObject(this).getInstance(this);
        findViewById(R.id.btn).setOnClickListener(view -> {
            EditText usn = findViewById(R.id.username);
            EditText psw = findViewById(R.id.password);
            User user = dataAccessObject.handleLogin(usn.getText().toString(),psw.getText().toString());
            if (user!=null){
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }else {
                Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}