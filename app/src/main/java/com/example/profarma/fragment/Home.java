package com.example.profarma.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.profarma.R;
import com.example.profarma.databinding.FragmentHomeBinding;
import com.google.android.material.appbar.MaterialToolbar;

public class Home extends Fragment {
    private FragmentHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.bind(inflater.inflate(R.layout.fragment_home, container, false));

        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AddProduct fragment
                MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
                toolbar.setTitle("Products");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_view, new Product()).commit();
            }
        });

        binding.btnGenerateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to GenerateBill fragment
                MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
                toolbar.setTitle("Generate Bill");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_view, new Cart()).commit();
            }
        });

        binding.btnShowMoreBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ViewBill fragment
                MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
                toolbar.setTitle("View Bill");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_view, new History()).commit();
            }
        });

        binding.btnShowMoreProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ViewProduct fragment
                MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
                toolbar.setTitle("View Product");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_view, new Product()).commit();
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}