package com.edward.myapplication.ui.fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.edward.myapplication.config.CONFIG.ACTION_REMOVE_KEY_REMOVEUSER;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_ID;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_KEY_ALLCOURSE;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_KEY_REGISTERED;
import static com.edward.myapplication.config.CONFIG.SERVICE_ACTION;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETALLCOURSE_KEY;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETALLCOURSE_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETALLUSER_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_HANDLE_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_RESULT;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.edward.myapplication.R;
import com.edward.myapplication.adapter.CourseAdapter;
import com.edward.myapplication.adapter.CourseAdapterLv;
import com.edward.myapplication.adapter.UserAdapter;
import com.edward.myapplication.modal.Course;
import com.edward.myapplication.modal.User;
import com.edward.myapplication.service.GetAllCourseServices;
import com.edward.myapplication.service.GetAllUserService;

import java.util.ArrayList;

public class AdminCourseManager extends Fragment {
    // TODO: 9/9/2022
    ArrayList<Course> listCourse = new ArrayList<>();
    SwipeMenuListView listView;
    CourseAdapterLv courseAdapterLv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_course,container,false);
        listView = view.findViewById(R.id.view);
        courseAdapterLv = new CourseAdapterLv(requireContext(), listCourse);

        IntentFilter filterGetAllCourse = new IntentFilter(INTENT_GETALLCOURSE_ACTION);
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(getAllCourseReceiver, filterGetAllCourse);

        Intent intent = new Intent(getContext(), GetAllCourseServices.class);
        intent.setAction(SERVICE_GETALLCOURSE_NAME);
//        intent.putExtra(DATABASE_KEY_USER_ID, "ps01");
        intent.putExtra(SERVICE_GETALLCOURSE_KEY, false);

        SwipeMenuCreator creator = menu -> {
            SwipeMenuItem openItem = new SwipeMenuItem(requireContext());
            openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
            openItem.setWidth(180);
            openItem.setTitle("Edit");
            openItem.setTitleSize(14);
            openItem.setTitleColor(Color.WHITE);
            menu.addMenuItem(openItem);

            SwipeMenuItem deleteItem = new SwipeMenuItem(requireContext());
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
            deleteItem.setWidth(180);
            deleteItem.setTitle("Remove");
            deleteItem.setTitleSize(14);
            deleteItem.setTitleColor(Color.WHITE);
            menu.addMenuItem(deleteItem);
        };
        listView.setMenuCreator(creator);
        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                listView.smoothOpenMenu(position);
            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });
        listView.smoothCloseMenu();
        listView.setOnMenuItemClickListener((position, menu, index) -> {
            Course value = (Course) courseAdapterLv.getItem(position);
            switch (index) {
                case 0:
                    Toast.makeText(requireContext(), "Action 1 for " + value, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(requireContext(), "Action 2 for " + value, Toast.LENGTH_SHORT).show();
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
//                    dialog.setTitle("Hello")
//                            .setMessage("co chac la xoa khum :>")
//                            .setNegativeButton("Cancel", (dialoginterface, i) -> dialoginterface.cancel())
//                            .setPositiveButton("Ok", (dialoginterface, i) -> {
//                                intent2.putExtra(DATABASE_KEY_USER_ID, value.get_ID());
//                                intent2.setAction(ACTION_REMOVE_KEY_REMOVEUSER);
//                                requireActivity().startService(intent2);
//                                requireActivity().startService(intent);
//                                dialoginterface.cancel();
//                            }).show();

                    break;
            }
            return false;
        });

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
                    case SERVICE_GETALLCOURSE_NAME:
                        new ProcessInBackground(intent).execute();
                        break;
                    default:
                        break;
                }
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
            listCourse.clear();
            if (intent.getSerializableExtra(INTENT_GETALLCOURSE_KEY_ALLCOURSE) != null) {
                listCourse.addAll((ArrayList<Course>) intent.getSerializableExtra(INTENT_GETALLCOURSE_KEY_ALLCOURSE));
                Log.e(String.valueOf(listCourse.size()), "doInBackground: ");
            }
            return exception;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            courseAdapterLv = new CourseAdapterLv(requireContext(), listCourse);
            listView.setAdapter(courseAdapterLv);
            courseAdapterLv.notifyDataSetChanged();
            progressDialog.dismiss();
        }
    }
}
