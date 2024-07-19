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
                if (isDetailsFilled()) {
                    if (binding.edUserName.getText().toString().equals("admin") && binding.edPsw.getText().toString().equals("admin") && (binding.chkAdmin.isChecked())) {
                        startActivity(new Intent(context, AdminHome.class));
                        Toast.makeText(context, "Login Successful, Welcome Admin", Toast.LENGTH_LONG).show();
                        finish();
                    } else if ((binding.edUserName.getText().toString().equals("user")) && (binding.edPsw.getText().toString().equals("user"))) {
                        if (binding.chkAdmin.isChecked()) {
                            Toast.makeText(context, "Invalid User Name or Password for Admin!!", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(context, EmployeeHome.class));
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    } else {
                        Toast.makeText(context, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean isDetailsFilled() {
        if (binding.edUserName.getText().toString().isEmpty()) {
            binding.edUserName.setError("Please Enter Username");
            binding.edUserName.requestFocus();
            return false;
        } else if (binding.edPsw.getText().toString().isEmpty()) {
            binding.edPsw.setError("Please Enter Password");
            binding.edPsw.requestFocus();
            return false;
        }
        return true;
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