package com.edward.myapplication.service;

import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_ID;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_KEY_ALLCOURSE;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_KEY_REGISTERED;
import static com.edward.myapplication.config.CONFIG.SERVICE_ACTION;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETALLCOURSE_KEY;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETALLCOURSE_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETALLCOURSE_USER_ID;
import static com.edward.myapplication.config.CONFIG.SERVICE_RESULT;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.edward.myapplication.dao.DataAccessObject;
import com.edward.myapplication.modal.Course;

import java.util.ArrayList;

public class GetAllCourseServices extends IntentService {
    // TODO: 9/5/2022
    // TODO: 9/6/2022
    public GetAllCourseServices() {
        super(SERVICE_GETALLCOURSE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            DataAccessObject dataAccessObject = new DataAccessObject(getApplicationContext());

            Intent i = new Intent(INTENT_GETALLCOURSE_ACTION);//result intent

            String action = intent.getAction();
            String userID = intent.getStringExtra(DATABASE_KEY_USER_ID);
            boolean isMine = intent.getBooleanExtra(SERVICE_GETALLCOURSE_KEY, false);


            if (isMine) {
                ArrayList<Course> allCourse = dataAccessObject.getAllCourse();
                i.putExtra(INTENT_GETALLCOURSE_KEY_ALLCOURSE, allCourse);
            }

            ArrayList<Course> allCourseRegistered = dataAccessObject.getAllRegisteredCourses(userID);
            dataAccessObject.close();
            i.putExtra(INTENT_GETALLCOURSE_KEY_REGISTERED, allCourseRegistered);
            i.putExtra(SERVICE_ACTION, action);
            i.putExtra(SERVICE_RESULT, Activity.RESULT_OK);
            LocalBroadcastManager.getInstance(this).sendBroadcast(i); //https://viblo.asia/p/local-broadcast-trong-android-1VgZvxy75Aw

        }
    }

}
