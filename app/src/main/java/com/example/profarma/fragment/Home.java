package com.example.profarma.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.profarma.R;
import com.example.profarma.databinding.FragmentHomeBinding;

public class Home extends Fragment {
    private FragmentHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.bind(inflater.inflate(R.layout.fragment_home, container, false));
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}