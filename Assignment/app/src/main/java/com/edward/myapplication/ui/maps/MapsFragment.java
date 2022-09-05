package com.edward.myapplication.ui.maps;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edward.myapplication.R;
import com.edward.myapplication.databinding.FragmentMapsBinding;

public class MapsFragment extends Fragment {

    private FragmentMapsBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        return root;
    }

}