package com.example.profarma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.profarma.databinding.ActivityAdminHomeBinding;

public class AdminHome extends AppCompatActivity{
    private ActivityAdminHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.layoutAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminHome.this);
                builder.setView(R.layout.add_product_layout);
                builder.show();
            }
        });

        binding.layoutSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
                startActivity(new Intent(AdminHome.this, EmployeeHome.class));
            }
        });
    }
}