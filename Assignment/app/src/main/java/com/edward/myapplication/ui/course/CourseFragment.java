package com.edward.myapplication.ui.course;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edward.myapplication.R;
import com.edward.myapplication.dao.DataAccsesObject;
import com.edward.myapplication.databinding.FragmentCourseBinding;

public class CourseFragment extends Fragment {

    private FragmentCourseBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCourseBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        DataAccsesObject dataAccsesObject = new DataAccsesObject(getContext());
        System.out.println(dataAccsesObject.getAllCourse());
        return root;
    }


}