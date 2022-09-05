package com.edward.myapplication.ui.course;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.edward.myapplication.dao.DataAccsesObject;
import com.edward.myapplication.databinding.FragmentCourseBinding;

public class CourseFragment extends Fragment {

    private FragmentCourseBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCourseBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        binding.regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "hehe", Toast.LENGTH_SHORT).show();
            }
        });
        binding.courseRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "hehe1", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }


}