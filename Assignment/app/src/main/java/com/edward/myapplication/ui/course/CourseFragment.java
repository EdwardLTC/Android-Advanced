package com.edward.myapplication.ui.course;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.edward.myapplication.R;
import com.edward.myapplication.databinding.FragmentCourseBinding;
import com.edward.myapplication.ui.fragment.ListCourseFragment;

import java.util.Objects;

public class CourseFragment extends Fragment {

    private FragmentCourseBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCourseBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        binding.regis.setOnClickListener(view -> changeFragment(new ListCourseFragment()));
        binding.courseRegistered.setOnClickListener(view -> changeFragment(new ListCourseFragment()));
        return root;
    }

    private void changeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, Objects.requireNonNull(fragment), null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}