package com.example.profarma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.profarma.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    private ActivityLoginBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        context = binding.getRoot().getContext();
        setContentView(binding.getRoot());

        Toast.makeText(context, "Just Click On Login Button To Continue", Toast.LENGTH_LONG).show();

        binding.edUserName.setOnFocusChangeListener(this);
        binding.edPsw.setOnFocusChangeListener(this);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show();
                if(binding.chkAdmin.isChecked())
                    startActivity(new Intent(context, AdminHome.class));
                else
                    startActivity(new Intent(context, EmployeeHome.class));
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText ed = (EditText) v;
        if (hasFocus) {
            ed.setBackground(context.getDrawable(R.drawable.edittext_shape));
            ed.setTextColor(context.getColor(R.color.dark_blue));
        } else {
            ed.setBackground(context.getDrawable(R.drawable.textbox_shape));
            ed.setTextColor(context.getColor(R.color.font_color));
        }
    }
}