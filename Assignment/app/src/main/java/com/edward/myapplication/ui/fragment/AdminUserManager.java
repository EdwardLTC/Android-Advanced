package com.edward.myapplication.ui.fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.edward.myapplication.config.CONFIG.ACTION_REMOVE_KEY_REMOVECOURSE;
import static com.edward.myapplication.config.CONFIG.ACTION_REMOVE_KEY_REMOVEUSER;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_ID;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_ID;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_KEY_ALLCOURSE;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_KEY_REGISTERED;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLUSER_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_GETREGISTERINFO_KEY_REGISTINFO;
import static com.edward.myapplication.config.CONFIG.INTENT_REMOVE_ACTION;
import static com.edward.myapplication.config.CONFIG.RSS_LINK;
import static com.edward.myapplication.config.CONFIG.RSS_LOADING;
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
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.edward.myapplication.R;
import com.edward.myapplication.adapter.UserAdapter;
import com.edward.myapplication.modal.Course;
import com.edward.myapplication.modal.RSS;
import com.edward.myapplication.modal.User;
import com.edward.myapplication.service.GetAllCourseServices;
import com.edward.myapplication.service.GetAllUserService;
import com.edward.myapplication.service.HandleRemoveUserCourse;
import com.edward.myapplication.ui.news.NewsFragment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdminUserManager extends Fragment {
    // TODO: 9/9/2022
    ArrayList<User> listUser = new ArrayList<>();
    SwipeMenuListView listView;
    UserAdapter userAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_user, container, false);
        listView = view.findViewById(R.id.view);

        IntentFilter filterGetAllUser = new IntentFilter(INTENT_GETALLUSER_ACTION);
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(getAllUserReceiver, filterGetAllUser);

        IntentFilter filterRemove = new IntentFilter(INTENT_REMOVE_ACTION);
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(getAllUserReceiver, filterRemove);

        userAdapter = new UserAdapter(requireContext(), listUser);
        Intent intent = new Intent(getContext(), GetAllUserService.class);
        intent.setAction(SERVICE_GETALLUSER_NAME);

        Intent intent2 = new Intent(getContext(), HandleRemoveUserCourse.class);

        SwipeMenuCreator creator = menu -> {
            SwipeMenuItem openItem = new SwipeMenuItem(requireContext());
            openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
            openItem.setWidth(180);
            openItem.setTitle("Open");
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
            User value = (User) userAdapter.getItem(position);
            switch (index) {
                case 0:
                    Toast.makeText(requireContext(), "Action 1 for " + value, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
                    dialog.setTitle("Hello")
                            .setMessage("co chac la xoa khum :>")
                            .setNegativeButton("Cancel", (dialoginterface, i) -> dialoginterface.cancel())
                            .setPositiveButton("Ok", (dialoginterface, i) -> {
                                intent2.putExtra(DATABASE_KEY_USER_ID, value.get_ID());
                                intent2.setAction(ACTION_REMOVE_KEY_REMOVEUSER);
                                requireActivity().startService(intent2);
                                requireActivity().startService(intent);
                                dialoginterface.cancel();
                            }).show();

                    break;
            }
            return false;
        });
        requireActivity().startService(intent);
        return view;
    }

    private final BroadcastReceiver getAllUserReceiver = new BroadcastReceiver() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra(SERVICE_RESULT, RESULT_CANCELED);
            if (resultCode == RESULT_OK) {
                String action = intent.getStringExtra(SERVICE_ACTION);
                switch (action) {
                    case ACTION_REMOVE_KEY_REMOVEUSER:
                    case SERVICE_GETALLUSER_NAME:
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
            listUser.clear();
            if (intent.getSerializableExtra(INTENT_GETREGISTERINFO_KEY_REGISTINFO) != null) {
                listUser.addAll((ArrayList<User>) intent.getSerializableExtra(INTENT_GETREGISTERINFO_KEY_REGISTINFO));
            }
            return exception;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            userAdapter = new UserAdapter(requireContext(), listUser);
            listView.setAdapter(userAdapter);
            userAdapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(getAllUserReceiver);
    }

}
