package com.edward.myapplication.service;

import static com.edward.myapplication.config.CONFIG.INTENT_GETALLUSER_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLUSER_KEY_USERLIST;
import static com.edward.myapplication.config.CONFIG.INTENT_GETREGISTERINFO_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_GETREGISTERINFO_KEY_REGISTINFO;
import static com.edward.myapplication.config.CONFIG.SERVICE_ACTION;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETREGISTERINFO_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_RESULT;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.edward.myapplication.dao.DataAccessObject;
import com.edward.myapplication.modal.RegisterInfo;

import java.util.ArrayList;

public class GetAllRegisterInfo extends IntentService {
    // TODO: 9/9/2022
    public GetAllRegisterInfo(){
        super(SERVICE_GETREGISTERINFO_NAME);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            DataAccessObject dataAccessObject = new DataAccessObject(getApplicationContext());

            Intent i = new Intent(INTENT_GETREGISTERINFO_ACTION);//result intent
            String action = intent.getAction();

            ArrayList<RegisterInfo> registerInfo = dataAccessObject.getAllRegisterInfo();
            dataAccessObject.close();
            i.putExtra(INTENT_GETREGISTERINFO_KEY_REGISTINFO, registerInfo);
            i.putExtra(SERVICE_ACTION, action);
            i.putExtra(SERVICE_RESULT, Activity.RESULT_OK);
            LocalBroadcastManager.getInstance(this).sendBroadcast(i);

        }
    }
}
