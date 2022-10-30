package com.edward.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.edward.myapplication.adapter.DialogCallback;
import com.edward.myapplication.adapter.DialogCustom;
import com.edward.myapplication.adapter.RCAdapter;
import com.edward.myapplication.adapter.RecycleCallBack;
import com.edward.myapplication.dao.DataAccessObject;
import com.edward.myapplication.databinding.ActivityMainBinding;
import com.edward.myapplication.models.User;

public class MainActivity extends AppCompatActivity implements RecycleCallBack {

    private RCAdapter adapter;
    private MainViewModel accountViewModel;
    private DataAccessObject dataAccessObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        RecyclerView recyclerView = binding.rcv;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        dataAccessObject = new DataAccessObject(this);
        accountViewModel = new MainViewModel(this);

        accountViewModel.getListMutableLiveData().observe(this, users -> {
            adapter = new RCAdapter(MainActivity.this, users,this);
            recyclerView.setAdapter(adapter);
        });


    }

    @Override
    public void onClickComputer(User user) {

    }
}