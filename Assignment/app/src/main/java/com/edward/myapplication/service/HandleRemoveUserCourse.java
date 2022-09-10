package com.edward.myapplication.service;

import static com.edward.myapplication.config.CONFIG.ACTION_REMOVE_KEY_REMOVECOURSE;
import static com.edward.myapplication.config.CONFIG.ACTION_REMOVE_KEY_REMOVEUSER;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_ID;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_ID;
import static com.edward.myapplication.config.CONFIG.INTENT_REMOVE_ACTION;
import static com.edward.myapplication.config.CONFIG.SERVICE_ACTION;
import static com.edward.myapplication.config.CONFIG.SERVICE_REMOVE_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_RESULT;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Switch;

import androidx.annotation.NavigationRes;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.edward.myapplication.dao.DataAccessObject;

public class HandleRemoveUserCourse extends IntentService {

    public HandleRemoveUserCourse() {
        super(SERVICE_REMOVE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            DataAccessObject dataAccessObject = new DataAccessObject(getApplicationContext());

            Intent i = new Intent(INTENT_REMOVE_ACTION);    //result intent

            String action = intent.getAction();

            String userID = intent.getStringExtra(DATABASE_KEY_USER_ID);
            int courseID = intent.getIntExtra(DATABASE_KEY_COURSE_ID, -1);

            boolean removeUserResult = false;
            boolean removeCourseResult = false;

            switch (action) {
                case ACTION_REMOVE_KEY_REMOVEUSER:
                    removeUserResult = dataAccessObject.handleRemoveUser(userID);
                    i.putExtra(ACTION_REMOVE_KEY_REMOVEUSER, removeUserResult);
                    break;
                case ACTION_REMOVE_KEY_REMOVECOURSE:
                    removeCourseResult = dataAccessObject.handleRemoveCourse(courseID);
                    i.putExtra(ACTION_REMOVE_KEY_REMOVECOURSE,removeCourseResult );
                    break;
                default:
                    Log.e("Err Handle Remove ", "onHandleIntent: " );
                    break;
            }

            i.putExtra(SERVICE_ACTION, action);
            i.putExtra(SERVICE_RESULT, Activity.RESULT_OK);
            LocalBroadcastManager.getInstance(this).sendBroadcast(i);
        }
    }
}
