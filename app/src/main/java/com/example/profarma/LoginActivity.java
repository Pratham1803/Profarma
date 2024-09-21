package com.example.profarma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
                    if (binding.edUserName.getText().toString().equals("admin") && binding.edPsw.getText().toString().equals("admin")) {
                        startActivity(new Intent(context, EmployeeHome.class));
                        Toast.makeText(context, "Login Successful, Welcome Admin", Toast.LENGTH_LONG).show();
                        saveLoginStatus(true);
                        finish();
                    } else {
                        Toast.makeText(context, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                        saveLoginStatus(false);
                    }
                }else {
                    saveLoginStatus(false);
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

    private void saveLoginStatus(Boolean isLoggedIn) {
        SharedPreferences sharedPreferences =
                getSharedPreferences("MySharedPref", MODE_PRIVATE); // Get the SharedPreferences instance
        SharedPreferences.Editor editor = sharedPreferences.edit(); // Get the SharedPreferences editor instance
        editor.putBoolean("isLoggedIn", isLoggedIn); // Save the login status to SharedPreferences
        editor.putString(
                "userNameString",
                binding.edUserName.getText().toString()
        ); // Save the user name to SharedPreferences
        editor.putString(
                "userPswString",
                binding.edPsw.getText().toString()
        ); // Save the user Password to SharedPreferences
        editor.apply(); // Apply the changes to SharedPreferences
    }
}