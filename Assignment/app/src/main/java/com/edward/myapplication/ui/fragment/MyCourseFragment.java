package com.edward.myapplication.ui.fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.edward.myapplication.config.CONFIG.CHOOSE_FLATFORM;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_ID;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_KEY_ALLCOURSE;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_KEY_REGISTERED;
import static com.edward.myapplication.config.CONFIG.INTENT_HANDLE_ACTION;
import static com.edward.myapplication.config.CONFIG.SERVICE_ACTION;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETALLCOURSE_KEY;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETALLCOURSE_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_HANDLE_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_RESULT;
import static com.edward.myapplication.config.CONFIG.SHARE_TITLE;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edward.myapplication.R;
import com.edward.myapplication.adapter.CourseAdapter;
import com.edward.myapplication.modal.Course;
import com.edward.myapplication.service.GetAllCourseServices;

import java.util.ArrayList;

public class MyCourseFragment extends Fragment {
    ArrayList<Course> allCourse = new ArrayList<>();
    RecyclerView recyclerView;
    private String UID = "ps01";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_of_mine, container, false);

        recyclerView = view.findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        IntentFilter filterGetAllCourse = new IntentFilter(INTENT_GETALLCOURSE_ACTION);
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(getAllCourseReceiver, filterGetAllCourse);

        IntentFilter filterRegisterCourse = new IntentFilter(INTENT_HANDLE_ACTION);
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(getAllCourseReceiver, filterRegisterCourse);

        view.findViewById(R.id.fab).setOnClickListener(view1 -> ShareDetailCourse());
        Intent intent = new Intent(getContext(), GetAllCourseServices.class);
        intent.putExtra(DATABASE_KEY_USER_ID, UID);
        intent.putExtra(SERVICE_GETALLCOURSE_KEY, true);
        intent.setAction(SERVICE_GETALLCOURSE_NAME);
        requireActivity().startService(intent);


        return view;
    }

    private final BroadcastReceiver getAllCourseReceiver = new BroadcastReceiver() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra(SERVICE_RESULT, RESULT_CANCELED);
            if (resultCode == RESULT_OK) {
                String action = intent.getStringExtra(SERVICE_ACTION);
                switch (action) {
                    case SERVICE_HANDLE_NAME:
                    case SERVICE_GETALLCOURSE_NAME:
                        new ProcessInBackground(intent).execute();
                        break;
                    default:
                        break;
                }
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(getAllCourseReceiver);
    }

    @SuppressLint("StaticFieldLeak")
    private class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        Exception exception = null;
        Intent intent;
        ArrayList<Course> m_allCourseRegistered = new ArrayList<>();

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
            allCourse.clear();
            if (intent.getSerializableExtra(INTENT_GETALLCOURSE_KEY_REGISTERED) != null) {
                m_allCourseRegistered.addAll((ArrayList<Course>) intent.getSerializableExtra(INTENT_GETALLCOURSE_KEY_REGISTERED));
            }
            for (Course course : m_allCourseRegistered) {
                course.setRegister(true);
            }
            return exception;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            allCourse.addAll(m_allCourseRegistered);
            CourseAdapter adapter = new CourseAdapter(requireContext(), allCourse);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            progressDialog.dismiss();
        }
    }
    private void ShareDetailCourse() {
        String content = "Edward signed up for these course\n\n";
        for (Course khdk : allCourse) {
            content = content.concat(khdk.get_Name())
                    .concat("\nSchedule: ")
                    .concat(khdk.get_Schedule())
                    .concat("\nTest Schedule: ")
                    .concat(khdk.get_testSchedule())
                    .concat("\n-----------\n");
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_SUBJECT, SHARE_TITLE);
        intent.setType("text/plain");

        startActivity(Intent.createChooser(intent, CHOOSE_FLATFORM));
    }
}
