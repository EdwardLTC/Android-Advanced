package com.edward.myapplication.ui.fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

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

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.edward.myapplication.dao.DataAccessObject;
import com.edward.myapplication.modal.Course;
import com.edward.myapplication.service.GetAllCourseServices;

import java.util.ArrayList;

public class ListCourseFragment extends Fragment {
    ArrayList<Course> allCourse = new ArrayList<>();
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);

        recyclerView = view.findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        IntentFilter filterGetAllCourse = new IntentFilter(INTENT_GETALLCOURSE_ACTION);
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(getAllCourseReceiver, filterGetAllCourse);

        IntentFilter filterRegisterCourse = new IntentFilter(INTENT_HANDLE_ACTION);
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(getAllCourseReceiver, filterRegisterCourse);

        Intent intent = new Intent(getContext(), GetAllCourseServices.class);
        intent.putExtra(DATABASE_KEY_USER_ID, "ps01");
        intent.putExtra(SERVICE_GETALLCOURSE_KEY, false);
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
                        allCourse.clear();

                        ArrayList<Course> m_allCourse = new ArrayList<>();
                        ArrayList<Course> m_allCourseRegistered = new ArrayList<>();
                        if (intent.getSerializableExtra(INTENT_GETALLCOURSE_KEY_ALLCOURSE) != null) {
                            m_allCourse.addAll((ArrayList<Course>) intent.getSerializableExtra(INTENT_GETALLCOURSE_KEY_ALLCOURSE));
                        }
                        if (intent.getSerializableExtra(INTENT_GETALLCOURSE_KEY_REGISTERED) != null) {
                            m_allCourseRegistered.addAll((ArrayList<Course>) intent.getSerializableExtra(INTENT_GETALLCOURSE_KEY_REGISTERED));
                        }

                        for (Course curse : m_allCourse) {  //group arrays
                            for (Course course_registered : m_allCourseRegistered) {
                                if (curse.get_ID() == course_registered.get_ID()) {
                                    curse.setRegister(true);
                                    break;
                                }
                            }
                            allCourse.add(curse);
                        }

                        CourseAdapter adapter = new CourseAdapter(requireContext(), allCourse);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

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
}
