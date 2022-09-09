package com.edward.myapplication.service;

import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_ID;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_KEY_REGISTERED;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLUSER_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLUSER_KEY_USERLIST;
import static com.edward.myapplication.config.CONFIG.SERVICE_ACTION;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETALLUSER_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_RESULT;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.edward.myapplication.dao.DataAccessObject;
import com.edward.myapplication.modal.User;

import java.util.ArrayList;

public class GetAllUserService extends IntentService {
    // TODO: 9/9/2022
    public GetAllUserService(){
        super(SERVICE_GETALLUSER_NAME);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            DataAccessObject dataAccessObject = new DataAccessObject(getApplicationContext());

            Intent i = new Intent(INTENT_GETALLUSER_ACTION);//result intent
            String action = intent.getAction();

            ArrayList<User> allUser = dataAccessObject.getAllUser();
            dataAccessObject.close();
            i.putExtra(INTENT_GETALLUSER_KEY_USERLIST, allUser);
            i.putExtra(SERVICE_ACTION, action);
            i.putExtra(SERVICE_RESULT, Activity.RESULT_OK);
            LocalBroadcastManager.getInstance(this).sendBroadcast(i);

        }
    }

}
