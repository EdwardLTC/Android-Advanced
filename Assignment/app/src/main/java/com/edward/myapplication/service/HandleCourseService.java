package com.edward.myapplication.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.edward.myapplication.dao.DataAccsesObject;
import com.edward.myapplication.modal.Course;

import java.util.ArrayList;

public class HandleCourseService extends IntentService {
    // TODO: 9/5/2022
    private static final String SERVICE_NAME = "HandleCourseService";
    private static final String SERVICE_USER_ID = "userID";
    private static final String SERVICE_COURSE_ID = "courseID";
    private static final String SERVICE_ALLCOURSE = "allCourse";
    private static final String SERVICE_ALLCOURSEREGISTERED = "allCourseRegistered";
    private static final String SERVICE_ACTION = "action";
    private static final String SERVICE_RESULT ="result";
    private static final String INTENT_ACTION = "HandleCourseService";
    private static final String INTENT_HANDLECOURSE = "handleCourse";


    public HandleCourseService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            DataAccsesObject dataAccsesObject = new DataAccsesObject(getApplicationContext());

            Intent i = new Intent(INTENT_ACTION);
            String action = intent.getAction();
            String userID = intent.getStringExtra(SERVICE_USER_ID);
            int courseID = intent.getIntExtra(SERVICE_COURSE_ID,-1);
            boolean isRegister = intent.getBooleanExtra(INTENT_HANDLECOURSE,false);

            if (isRegister){
                dataAccsesObject.handleRegisterCourse(userID,courseID);
            }
            else {
                dataAccsesObject.handleUnRegisterCourse(userID,courseID);
            }

            ArrayList<Course> allCourse = dataAccsesObject.getAllCourse();
            ArrayList<Course> allCourseRegistered = dataAccsesObject.getAllRegisteredCourses(userID);
            i.putExtra(SERVICE_ALLCOURSE,allCourse);
            i.putExtra(SERVICE_ALLCOURSEREGISTERED,allCourseRegistered);
            i.putExtra(SERVICE_ACTION,action);
            i.putExtra(SERVICE_RESULT,Activity.RESULT_OK);
            LocalBroadcastManager.getInstance(this).sendBroadcast(i); //https://viblo.asia/p/local-broadcast-trong-android-1VgZvxy75Aw

        }
    }
}
