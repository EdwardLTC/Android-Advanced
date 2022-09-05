package com.edward.myapplication.ui.news;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edward.myapplication.R;
import com.edward.myapplication.databinding.FragmentNewsBinding;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        return root;
    }

}