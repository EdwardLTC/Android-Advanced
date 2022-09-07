package com.edward.myapplication.service;

import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_ID;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_ID;
import static com.edward.myapplication.config.CONFIG.INTENT_HANDLE_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_HANDLE_KEY_ALLCOURSE;
import static com.edward.myapplication.config.CONFIG.INTENT_HANDLE_KEY_ALLCOURSEREGISTERED;
import static com.edward.myapplication.config.CONFIG.INTENT_HANDLE_KEY_ISREGISTERED;
import static com.edward.myapplication.config.CONFIG.SERVICE_ACTION;
import static com.edward.myapplication.config.CONFIG.SERVICE_HANDLE_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_RESULT;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.edward.myapplication.dao.DataAccessObject;
import com.edward.myapplication.modal.Course;

import java.util.ArrayList;

public class HandleCourseService extends IntentService {
    // TODO: 9/5/2022
    // TODO: 9/6/2022  
    public HandleCourseService() {
        super(SERVICE_HANDLE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            DataAccessObject dataAccessObject = new DataAccessObject(getApplicationContext());

            Intent i = new Intent(INTENT_HANDLE_ACTION);    //result intent

            String action = intent.getAction();
            String userID = intent.getStringExtra(DATABASE_KEY_USER_ID);
            int courseID = intent.getIntExtra(DATABASE_KEY_COURSE_ID, -1);
            boolean isRegister = intent.getBooleanExtra(INTENT_HANDLE_KEY_ISREGISTERED, false);

            if (isRegister) {
                if (!dataAccessObject.handleRegisterCourse(userID, courseID)){
                    Log.e("Err handle register", "onHandleIntent: " );
                }
            } else {

                if (!dataAccessObject.handleUnRegisterCourse(userID, courseID)){
                    Log.e("Err handle UnRegister", "onHandleIntent: " );
                }
            }

            ArrayList<Course> allCourse = dataAccessObject.getAllCourse();
            ArrayList<Course> allCourseRegistered = dataAccessObject.getAllRegisteredCourses(userID);
            dataAccessObject.close();
            i.putExtra(INTENT_HANDLE_KEY_ALLCOURSE, allCourse);
            i.putExtra(INTENT_HANDLE_KEY_ALLCOURSEREGISTERED, allCourseRegistered);
            i.putExtra(SERVICE_ACTION, action);
            i.putExtra(SERVICE_RESULT, Activity.RESULT_OK);
            LocalBroadcastManager.getInstance(this).sendBroadcast(i); //https://viblo.asia/p/local-broadcast-trong-android-1VgZvxy75Aw

        }
    }
}
