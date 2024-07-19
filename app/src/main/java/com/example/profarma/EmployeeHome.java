package com.example.profarma;

import static java.security.AccessController.getContext;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.profarma.databinding.ActivityEmpHomeBinding;
import com.example.profarma.fragment.Cart;
import com.example.profarma.fragment.EmpHome;
import com.example.profarma.model.OrderCart;

public class EmployeeHome extends AppCompatActivity{
    private ActivityEmpHomeBinding binding;

    private static final int STORAGE_PERMISSION_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmpHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Check if permission is not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_MEDIA_IMAGES,
                            Manifest.permission.READ_MEDIA_VIDEO,
                            Manifest.permission.READ_MEDIA_AUDIO},
                    STORAGE_PERMISSION_CODE);
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if(id == R.id.menuHome){
                changeFragment(new EmpHome());
                return true;
            } else if(id == R.id.menuCart){
                changeFragment(new Cart());
                return true;
            } else if(id == R.id.menuHistory){
                Toast.makeText(this, "History Will Display Later...", Toast.LENGTH_SHORT).show();
                return true;
            } else if(id == R.id.menuProfile){
                Toast.makeText(this, "Profile Will Display Later...", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.menuAllProduct) {
                Toast.makeText(this, "Products List and Analysis Will Display Later...", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        if(getIntent().hasExtra("OpenCart")){
            binding.bottomNavigationView.setSelectedItemId(R.id.menuCart);
            changeFragment(new Cart());
        }else{
            binding.bottomNavigationView.setSelectedItemId(R.id.menuHome);
            changeFragment(new EmpHome());
        }

        binding.txtCartCount.setText(String.valueOf(OrderCart.getLsProduct().size()));
        binding.imgCart.setOnClickListener(v -> {
            binding.bottomNavigationView.setSelectedItemId(R.id.menuCart);
            changeFragment(new Cart());
        });
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("permissionMsg", "onRequestPermissionsResult: Permission Granted");
            } else {
                Log.d("permissionMsg", "onRequestPermissionsResult: Permission Denied");
            }
        }
    }

    private void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, fragment).commit();
    }
}