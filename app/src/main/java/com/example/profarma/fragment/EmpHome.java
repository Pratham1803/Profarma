package com.example.profarma.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.profarma.Params;
import com.example.profarma.R;
import com.example.profarma.adapter.CategoryAdapter;
import com.example.profarma.databinding.FragmentEmpHomeBinding;
import com.example.profarma.model.Category;

import java.util.ArrayList;

public class EmpHome extends Fragment {
    private FragmentEmpHomeBinding binding;
    private CategoryAdapter categoryAdapter;
    private ArrayList<String> categoryList;

    public EmpHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmpHomeBinding.inflate(inflater, container, false);
        categoryList = new ArrayList<>(Params.getMapProductCategory().keySet());

        categoryAdapter = new CategoryAdapter(categoryList, binding.getRoot().getContext());
        binding.recyclerViewCategory.setAdapter(categoryAdapter);
        binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));

//        binding.editTextSearch.setQueryHint("Search Category");
        binding.editTextSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<String> searchList = new ArrayList<>();
                for(String category: categoryList){
                    if(category.toLowerCase().contains(query.toLowerCase())){
                        searchList.add(category);
                    }
                }
                categoryAdapter.setLocalDataSet(searchList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> searchList = new ArrayList<>();
                for(String category: categoryList){
                    if(category.toLowerCase().contains(newText.toLowerCase())){
                        searchList.add(category);
                    }
                }
                categoryAdapter.setLocalDataSet(searchList);
                return true;
            }
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}