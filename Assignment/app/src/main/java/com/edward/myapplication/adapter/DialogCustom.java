package com.edward.myapplication.adapter;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.edward.myapplication.config.CONFIG.ACTION_ADD_COURSE;
import static com.edward.myapplication.config.CONFIG.ACTION_REMOVE_KEY_REMOVECOURSE;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_ID;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_NAME;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_SCHEDULE;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_TESTSCHEDULE;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_ID;
import static com.edward.myapplication.config.CONFIG.INTENT_ADD_COURSE_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLUSER_KEY_USERLIST;
import static com.edward.myapplication.config.CONFIG.INTENT_HANDLE_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_HANDLE_KEY_ISREGISTERED;
import static com.edward.myapplication.config.CONFIG.SERVICE_ACTION;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETALLCOURSE_KEY;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETALLCOURSE_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_RESULT;
import static com.edward.myapplication.config.CONFIG.SERVICE_RESULT_ADD_COURSE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.edward.myapplication.R;
import com.edward.myapplication.modal.User;
import com.edward.myapplication.service.GetAllCourseServices;
import com.edward.myapplication.service.HandleAddCourse;
import com.edward.myapplication.service.HandleCourseService;
import com.edward.myapplication.ui.fragment.AdminCourseManager;

import java.util.ArrayList;

public class DialogCustom extends  Dialog {
    private Context context;
    private boolean result;
    public DialogCustom(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_custom);

        EditText courseid = findViewById(R.id.courseId);
        EditText courseName = findViewById(R.id.courseName);
        EditText courseSchedule = findViewById(R.id.Schedule);
        EditText courseTestSchedule = findViewById(R.id.TestSchedule);

        IntentFilter addAllCourse = new IntentFilter(INTENT_ADD_COURSE_ACTION);
        LocalBroadcastManager.getInstance(context).registerReceiver(addCourseReceiver, addAllCourse);

        findViewById(R.id.submit).setOnClickListener(view -> {
            Intent intentAddCourse = new Intent(context, HandleAddCourse.class);
            intentAddCourse.setAction(INTENT_ADD_COURSE_ACTION);
            intentAddCourse.putExtra(DATABASE_KEY_COURSE_ID,Integer.parseInt(courseid.getText().toString()));
            intentAddCourse.putExtra(DATABASE_KEY_COURSE_NAME,courseName.getText().toString());
            intentAddCourse.putExtra(DATABASE_KEY_COURSE_SCHEDULE,courseSchedule.getText().toString());
            intentAddCourse.putExtra(DATABASE_KEY_COURSE_TESTSCHEDULE,courseTestSchedule.getText().toString());
            context.startService(intentAddCourse);



        });

    }

    private final BroadcastReceiver addCourseReceiver = new BroadcastReceiver() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                int resultCode = intent.getIntExtra(SERVICE_RESULT, RESULT_CANCELED);
                if (resultCode == RESULT_OK) {
                    String action = intent.getStringExtra(SERVICE_ACTION);
                    switch (action) {
                        case INTENT_ADD_COURSE_ACTION:
                            new ProcessInBackground(intent).execute();
                            break;
                        default:
                            break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    @SuppressLint("StaticFieldLeak")
    private class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        Exception exception = null;
        Intent intent;

        public ProcessInBackground(Intent intent) {
            this.intent = intent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("loading data");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... integers) {
            result = intent.getBooleanExtra(SERVICE_RESULT_ADD_COURSE,false);
            return exception;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            if (!result){
                Toast.makeText(context, "Error the data has existed", Toast.LENGTH_SHORT).show();
            }else {
                Intent getAllCourse = new Intent(context, GetAllCourseServices.class);
                getAllCourse.putExtra(SERVICE_GETALLCOURSE_KEY, false);
                getAllCourse.setAction(SERVICE_GETALLCOURSE_NAME);
                context.startService(getAllCourse);
                dismiss();
            }
            progressDialog.dismiss();
        }
    }


}
