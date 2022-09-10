package com.edward.myapplication.ui.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edward.myapplication.R;
import com.edward.myapplication.databinding.FragmentAdminBinding;
import com.edward.myapplication.databinding.FragmentCourseBinding;
import com.edward.myapplication.ui.course.CourseFragment;
import com.edward.myapplication.ui.fragment.AdminCourseManager;
import com.edward.myapplication.ui.fragment.AdminUserManager;

import java.util.Objects;

public class AdminFragment extends Fragment {

    private FragmentAdminBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        binding.courseManagement.setOnClickListener(view -> changeFragment(new AdminCourseManager()));
        binding.userManagement.setOnClickListener(view -> changeFragment(new AdminUserManager()));
        return root;
    }
    private void changeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.nav_host_fragment_content_main, Objects.requireNonNull(fragment), null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}