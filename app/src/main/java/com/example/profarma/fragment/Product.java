package com.example.profarma.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.profarma.Params;
import com.example.profarma.R;
import com.example.profarma.adapter.ProductListAdapter;
import com.example.profarma.databinding.AddProductLayoutBinding;
import com.example.profarma.databinding.FragmentProductBinding;
import com.example.profarma.databinding.LayoutProductBinding;

import com.example.profarma.model.ProductModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

public class Product extends Fragment {
    private FragmentProductBinding binding;
    private Context context;
    private AlertDialog dialog;
    private AddProductLayoutBinding layoutProductBinding;
    private int REQUEST_IMAGE_CAPTURE = 1; // camera access
    private int REQUEST_IMAGE_GALLERY = 2; // gallery access
    private ProductListAdapter productListAdapter;

    public Product() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductBinding.bind(inflater.inflate(R.layout.fragment_product, container, false));
        context = binding.getRoot().getContext();

        binding.spCategory.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, Params.getMapProductCategory().keySet().toArray(new String[0])));

        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                layoutProductBinding = AddProductLayoutBinding.bind(inflater.inflate(R.layout.add_product_layout, container, false));
                builder.setView(layoutProductBinding.getRoot());

                layoutProductBinding.spCategory.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, Params.getMapProductCategory().keySet().toArray(new String[0])));

                layoutProductBinding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addProduct();
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });

        binding.btnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
                toolbar.setTitle("Cart");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_view, new Cart()).commit();
            }
        });

        productListAdapter = new ProductListAdapter(context);
        binding.recyclerProduct.setAdapter(productListAdapter);
        productListAdapter.setLocalDataSet(null, binding.spCategory.getSelectedItem().toString());

        binding.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                productListAdapter.setLocalDataSet(null, binding.spCategory.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void addProduct() {
        ProductModel product = new ProductModel();
        product.setProductName(layoutProductBinding.edtProductName.getText().toString());
        product.setPrice(layoutProductBinding.edtProductPrice.getText().toString());
        product.setCategory(layoutProductBinding.spCategory.getSelectedItem().toString());

        if (product.getProductName().isEmpty() || product.getPrice().isEmpty() || product.getCategory().isEmpty()) {
            Toast.makeText(context, "Enter Details", Toast.LENGTH_SHORT).show();
        } else {
            Params.getDbReference().child("products").child(product.getCategory()).push().setValue(product)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(context, "Product added successfully", Toast.LENGTH_SHORT).show();
                        productListAdapter.setLocalDataSet(null, product.getCategory());
                        reset(layoutProductBinding);
                    }).addOnFailureListener(e -> {
                        Toast.makeText(context, "Failed to add product", Toast.LENGTH_SHORT).show();
                        Log.d("ErrorMsg", "addProduct: " + e.getMessage());
                    });
        }
    }

    private void reset(AddProductLayoutBinding layoutProductBinding) {
        layoutProductBinding.edtProductName.setText("");
        layoutProductBinding.edtProductPrice.setText("");
        layoutProductBinding.spCategory.setSelection(0);
    }
}