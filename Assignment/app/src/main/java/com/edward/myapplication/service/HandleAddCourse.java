package com.edward.myapplication.service;

import static com.edward.myapplication.config.CONFIG.ACTION_ADD_COURSE;
import static com.edward.myapplication.config.CONFIG.ACTION_REMOVE_KEY_REMOVECOURSE;
import static com.edward.myapplication.config.CONFIG.ACTION_REMOVE_KEY_REMOVEUSER;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_ID;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_NAME;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_SCHEDULE;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_TESTSCHEDULE;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_ID;
import static com.edward.myapplication.config.CONFIG.INTENT_ADD_COURSE_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_REMOVE_ACTION;
import static com.edward.myapplication.config.CONFIG.SERVICE_ACTION;
import static com.edward.myapplication.config.CONFIG.SERVICE_ADD_COURSE_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_RESULT;
import static com.edward.myapplication.config.CONFIG.SERVICE_RESULT_ADD_COURSE;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.edward.myapplication.dao.DataAccessObject;
import com.edward.myapplication.modal.Course;

import java.util.Objects;

public class HandleAddCourse extends IntentService {

    public HandleAddCourse() {
        super(SERVICE_ADD_COURSE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            DataAccessObject dataAccessObject = new DataAccessObject(getApplicationContext());

            Intent i = new Intent(INTENT_ADD_COURSE_ACTION);    //result intent

            String action = intent.getAction();

            int courseID = intent.getIntExtra(DATABASE_KEY_COURSE_ID, -1);
            String courseName = intent.getStringExtra((DATABASE_KEY_COURSE_NAME));
            String courseSchedule = intent.getStringExtra(DATABASE_KEY_COURSE_SCHEDULE);
            String courseTestSchedule = intent.getStringExtra(DATABASE_KEY_COURSE_TESTSCHEDULE);

            boolean resultService = false;

            resultService = dataAccessObject.handleAddCourse(new Course(courseID, courseName, courseSchedule, courseTestSchedule));
            Log.e(String.valueOf(resultService), "onHandleIntent: ");


            i.putExtra(SERVICE_ACTION, action);
            i.putExtra(SERVICE_RESULT_ADD_COURSE, resultService);
            i.putExtra(SERVICE_RESULT, Activity.RESULT_OK);
            LocalBroadcastManager.getInstance(this).sendBroadcast(i);
        }
    }
}
