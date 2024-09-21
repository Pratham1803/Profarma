package com.example.profarma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.profarma.databinding.ActivitySplashScreenBinding;
import com.example.profarma.fragment.Product;
import com.example.profarma.model.ProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;
    private Context context;
    private Handler handler;
    // Request code for write permission
    private static final int REQUEST_WRITE_PERMISSION = 786;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbLoadProducts();
        loadCart();
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

                if(isLoggedIn()){
                    startActivity(new Intent(context, EmployeeHome.class));
                }else{
                startActivity(new Intent(context, LoginActivity.class));
                }
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

    // Function to check if the user is already logged in
    private Boolean isLoggedIn(){
        SharedPreferences sharedPreferences =
                getSharedPreferences("MySharedPref", MODE_PRIVATE); // Get the SharedPreferences instance
        Boolean isLoggedIn = sharedPreferences.getBoolean(
                "isLoggedIn",
                false
        ); // Get the login status from SharedPreferences
        String uName = sharedPreferences.getString("userNameString", ""); // Get the user name from SharedPreferences

        return isLoggedIn; // Return the login status
    }

    // Function to load the products from the database
    private void dbLoadProducts() {
        // Add a ValueEventListener to the database reference

        Params.getDbReference().child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Params.getMapProduct().clear();
                    Params.getMapProductCategory().clear();

                    for (DataSnapshot category : snapshot.getChildren()) {
                        ArrayList<String> productList = new ArrayList<>();
                        for (DataSnapshot product : category.getChildren()) {
                            ProductModel productData = product.getValue(ProductModel.class);
                            Params.getMapProduct().put(productData.getProductId(), productData);
                            productList.add(productData.getProductId());
                        }
                        Params.getMapProductCategory().put(category.getKey(), productList);
                        Log.d("ProductLog", "onDataChange: " + Params.getMapProduct());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ProductLog", "dbGetAllProduct: " + error.getMessage());
            }
        });
    }

    private void loadCart() {
        Params.getDbReference().child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Params.getArrCart().clear();
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Params.getArrCart().add(productSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ErrorMsg", "onCancelled: " + error.getMessage());
            }
        });
    }
}