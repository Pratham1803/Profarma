package com.example.profarma.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.profarma.Params;
import com.example.profarma.R;
import com.example.profarma.adapter.HistoryAdapter;
import com.example.profarma.databinding.FragmentHistoryBinding;
import com.example.profarma.model.OrderCart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class History extends Fragment {
    private FragmentHistoryBinding binding;
    private ArrayList<OrderCart> lsOrderCart;
    private HistoryAdapter historyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        lsOrderCart = new ArrayList<>();
        loadHistory();

        historyAdapter = new HistoryAdapter(lsOrderCart, getContext());
        binding.recyclerViewHistory.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.recyclerViewHistory.setAdapter(historyAdapter);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void loadHistory() {
        Params.getDbReference().child("order").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        lsOrderCart.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            OrderCart orderCart = dataSnapshot.getValue(OrderCart.class);
                            lsOrderCart.add(0,orderCart);
                        }
                        historyAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}