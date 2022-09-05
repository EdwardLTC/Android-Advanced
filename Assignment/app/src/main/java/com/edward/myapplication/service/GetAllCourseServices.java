package com.edward.myapplication.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.edward.myapplication.dao.DataAccsesObject;
import com.edward.myapplication.modal.Course;

import java.util.ArrayList;

public class GetAllCourseServices extends IntentService {
    // TODO: 9/5/2022
    private static final String SERVICE_NAME = "GetAllCourseServices";
    private static final String SERVICE_USER_ID = "userID";
    private static final String SERVICE_IS_MINE ="isMine";
    private static final String SERVICE_ACTION ="action";
    private static final String SERVICE_RESULT ="result";
    private static final String INTENT_ALLCOURSE = "allCourse";
    private static final String INTENT_ALLCOURSEREGISTERED = "allCourseRegistered";
    private static final String INTENT_ACTION = "GetAllCourseServices";

    public GetAllCourseServices(){
        super(SERVICE_NAME);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent !=null){
            DataAccsesObject dataAccsesObject = new DataAccsesObject(getApplicationContext());

            Intent i = new Intent(INTENT_ACTION);
            String action = intent.getAction();
            String userID = intent.getStringExtra(SERVICE_USER_ID);
            boolean isMine = intent.getBooleanExtra(SERVICE_IS_MINE,false);

            if (isMine){
                ArrayList<Course> allCourse = dataAccsesObject.getAllCourse();
                i.putExtra(INTENT_ALLCOURSE,allCourse);
            }

            ArrayList<Course> allCourseRegistered = dataAccsesObject.getAllRegisteredCourses(userID);
            i.putExtra(INTENT_ALLCOURSEREGISTERED,allCourseRegistered);

            i.putExtra(SERVICE_ACTION,action);
            i.putExtra(SERVICE_RESULT, Activity.RESULT_OK);
            LocalBroadcastManager.getInstance(this).sendBroadcast(i); //https://viblo.asia/p/local-broadcast-trong-android-1VgZvxy75Aw

        }
    }

}
