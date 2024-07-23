package com.example.profarma.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.profarma.Billing;
import com.example.profarma.Params;
import com.example.profarma.PdfUtils;
import com.example.profarma.R;
import com.example.profarma.adapter.CartAdapter;
import com.example.profarma.databinding.FragmentCartBinding;
import com.example.profarma.model.OrderCart;
import com.example.profarma.model.OrderProduct;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Cart extends Fragment {
    private FragmentCartBinding binding;
    private Context context;
    private OrderCart orderCart;

    public Cart() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        context = binding.getRoot().getContext();

        orderCart = new OrderCart();
        orderCart.setLsProduct(new ArrayList<>());

        binding.recyclerViewProduct.setAdapter(new CartAdapter(Params.getArrCart(), getContext(), orderCart));

        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());

                builder.setTitle("Enter Customer Details :");
                View view = LayoutInflater.from(context).inflate(R.layout.person_details_layput, null);
                Button btnGeneratePdf = view.findViewById(R.id.btnGenerateBill);
                EditText etName = view.findViewById(R.id.edtName);
                EditText etPhone = view.findViewById(R.id.edtNum);
                EditText etAddress = view.findViewById(R.id.edtAddress);

                btnGeneratePdf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etName.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty() || etAddress.getText().toString().isEmpty()) {
                            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(context, "Generating PDF", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Billing.class);
                        orderCart.setCompanyName(etName.getText().toString());
                        orderCart.setCompanyPhone(etPhone.getText().toString());
                        orderCart.setCompanyAddress(etAddress.getText().toString());

                        addToDb();
                        intent.putExtra("orderCart", orderCart);
                        startActivity(intent);
                    }
                });

                builder.setView(view);
                builder.create().show();
            }
        });

        return binding.getRoot();
    }

    private void addToDb(){
        float total = 0;
        orderCart.setOrderId(System.currentTimeMillis() + "");
        for(OrderProduct product : orderCart.getLsProduct()){
            total += Float.parseFloat(product.getProductPrice()) * Float.parseFloat(product.getProductQty());
        }
        orderCart.setTotalAmt(String.valueOf(total));

        Params.getDbReference().child("order").child(orderCart.getOrderId()).setValue(orderCart)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                    Params.getArrCart().clear();
                    binding.recyclerViewProduct.getAdapter().notifyDataSetChanged();
                    Params.getDbReference().child("cart").removeValue();

                    TextView txtCartCount = getActivity().findViewById(R.id.txtCartCount);
                    txtCartCount.setText("0");
                });
    }
}