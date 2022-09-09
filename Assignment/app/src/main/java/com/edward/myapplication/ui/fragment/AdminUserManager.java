package com.edward.myapplication.ui.fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_ID;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_KEY_ALLCOURSE;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLCOURSE_KEY_REGISTERED;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLUSER_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_GETREGISTERINFO_KEY_REGISTINFO;
import static com.edward.myapplication.config.CONFIG.SERVICE_ACTION;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETALLCOURSE_KEY;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETALLCOURSE_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETALLUSER_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_HANDLE_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_RESULT;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edward.myapplication.R;
import com.edward.myapplication.adapter.CourseAdapter;
import com.edward.myapplication.adapter.SwipeHelper;
import com.edward.myapplication.adapter.UserAdapter;
import com.edward.myapplication.modal.Course;
import com.edward.myapplication.modal.User;
import com.edward.myapplication.service.GetAllUserService;

import java.util.ArrayList;
import java.util.List;

public class AdminUserManager extends Fragment {
    // TODO: 9/9/2022
    ArrayList<User> listUser = new ArrayList<>();
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_user, container, false);
        recyclerView = view.findViewById(R.id.view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        SwipeHelper swipeHelper = new SwipeHelper(requireContext(), recyclerView) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        R.drawable.list,
                        Color.parseColor("#FF3C30"),
                        requireContext(),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(requireContext(), "Delete", Toast.LENGTH_SHORT).show();
                                //handle remove User
//                                viewHolder.getAdapterPosition()
                            }
                        }
                ));

                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Transfer",
                        R.drawable.list,
                        Color.parseColor("#FF9502"),
                        requireContext(),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(requireContext(), "Course", Toast.LENGTH_SHORT).show();
                                //handle get all course
                            }
                        }
                ));

            }
        };
        swipeHelper.attachSwipe();
        IntentFilter filterGetAllUser = new IntentFilter(INTENT_GETALLUSER_ACTION);
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(getAllUserReceiver, filterGetAllUser);
        Intent intent = new Intent(getContext(), GetAllUserService.class);
        intent.setAction(SERVICE_GETALLUSER_NAME);
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
                    case SERVICE_GETALLUSER_NAME:
                        listUser.clear();
                        ArrayList<User> m_allUser = new ArrayList<>();
                        if (intent.getSerializableExtra(INTENT_GETREGISTERINFO_KEY_REGISTINFO) != null) {
                            m_allUser.addAll((ArrayList<User>) intent.getSerializableExtra(INTENT_GETREGISTERINFO_KEY_REGISTINFO));
                        }
                        System.out.println(m_allUser);
                        listUser.addAll(m_allUser);
                        UserAdapter userAdapter = new UserAdapter(requireContext(), listUser);
                        recyclerView.setAdapter(userAdapter);
                        userAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }

        }
    };
}
