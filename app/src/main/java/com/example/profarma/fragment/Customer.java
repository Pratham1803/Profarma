package com.example.profarma.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.profarma.Params;
import com.example.profarma.R;
import com.example.profarma.adapter.CustomerAdapter;
import com.example.profarma.databinding.AddContactDialogBinding;
import com.example.profarma.databinding.FragmentCustomerBinding;
import com.example.profarma.model.CustomerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Customer extends Fragment {
    private FragmentCustomerBinding binding;
    private Context context;
    private AlertDialog dialog;
    private ArrayList<CustomerModel> arrCustomer = new ArrayList<>();
    private CustomerAdapter adapter;

    public Customer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCustomerBinding.inflate(inflater, container, false);
        context = binding.getRoot().getContext();

        binding.txtTotalUsers.setText("Total Users: " + Params.getArrCustomer().size());

        adapter = new CustomerAdapter(Params.getArrCustomer(), context);
        binding.recyclerViewCustomer.setAdapter(adapter);

        binding.btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                AddContactDialogBinding dialogBinding = AddContactDialogBinding.inflate(getLayoutInflater());
                builder.setView(dialogBinding.getRoot());

                dialogBinding.btnCustomer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomerModel customer = new CustomerModel(
                                dialogBinding.edContactName.getText().toString(),
                                dialogBinding.editTextPhone.getText().toString(),
                                dialogBinding.editTextAddress.getText().toString()
                        );

                        if (customer.getName().isEmpty() || customer.getContact().isEmpty()) {
                            dialogBinding.edContactName.setError("Enter Data");
                            dialogBinding.editTextPhone.setError("Enter Data");
                        } else {
                            Params.getDbReference().child("Customer").push().setValue(customer)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(context, "User Added", Toast.LENGTH_SHORT).show();
                                        adapter.notifyItemInserted(Params.getArrCustomer().size());
                                        binding.txtTotalUsers.setText("Total Users: " + Params.getArrCustomer().size());
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                                        Log.d("ErrorMsg", e.getMessage());
                                    });
                            dialog.dismiss();
                        }
                    }
                });

                dialog = builder.create();
                dialog.show();
            }
        });
        return binding.getRoot();
    }
}