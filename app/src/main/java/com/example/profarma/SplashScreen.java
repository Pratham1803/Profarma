package com.example.profarma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.profarma.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;
    private Context context;
    private Handler handler;
    // Request code for write permission
    private static final int REQUEST_WRITE_PERMISSION = 786;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        context = binding.getRoot().getContext();
        setContentView(binding.getRoot());

        handler = new Handler();
        startAnimation();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.imgLogo.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade));
                binding.imageView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade));
                binding.txtAppName.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade));

                binding.imgLogo.clearAnimation();
                binding.imageView.clearAnimation();
                binding.txtAppName.clearAnimation();
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        }, 3000);
    }

    private void startAnimation() {
        Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        binding.imageView.startAnimation(slideUp);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.imageView.clearAnimation();
                binding.imgLogo.setVisibility(View.VISIBLE);
                binding.imgLogo.startAnimation(slideUp);

                binding.txtAppName.setVisibility(View.VISIBLE);
                binding.txtAppName.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade));
            }
        }, 600);
    }
}