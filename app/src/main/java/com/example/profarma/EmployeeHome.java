package com.example.profarma;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.profarma.databinding.ActivityEmpHomeBinding;
import com.example.profarma.fragment.Cart;
import com.example.profarma.fragment.History;
import com.example.profarma.fragment.Home;
import com.example.profarma.fragment.Product;
import com.google.android.material.navigation.NavigationView;

public class EmployeeHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityEmpHomeBinding bind;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private int current_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bind = ActivityEmpHomeBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

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

        // setting drawer and custom toolbar
        setSupportActionBar(bind.toolbar); // toolbar setup
        bind.navDrawerView.setNavigationItemSelectedListener(this); // drawer navigation item select listener setup

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, bind.mainDrawerLayout, bind.toolbar, R.string.open_nav, R.string.close_nav);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white)); // setting color of 3 line icon to open drawer

        bind.mainDrawerLayout.addDrawerListener(toggle); // adding listener to drawer
        toggle.syncState(); // syncing the drawer

//        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            int id = item.getItemId();
//
//            if(id == R.id.menuHome){
//                changeFragment(new EmpHome());
//                return true;
//            } else if(id == R.id.menuCart){
//                changeFragment(new Cart());
//                return true;
//            } else if(id == R.id.menuHistory){
//                changeFragment(new History());
//                return true;
//            } else if(id == R.id.menuProfile){
//                Toast.makeText(this, "Profile Will Display Later...", Toast.LENGTH_SHORT).show();
//                return true;
//            } else if (id == R.id.menuAllProduct) {
//                Toast.makeText(this, "Products List and Analysis Will Display Later...", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//            return false;
//        });

//        if(getIntent().hasExtra("OpenCart")){
//            binding.bottomNavigationView.setSelectedItemId(R.id.menuCart);
//            changeFragment(new Cart());
//        }else{
//            binding.bottomNavigationView.setSelectedItemId(R.id.menuHome);
//            changeFragment(new EmpHome());
//        }

//        binding.imgCart.setOnClickListener(v -> {
//            binding.bottomNavigationView.setSelectedItemId(R.id.menuCart);
//            changeFragment(new Cart());
//        });
//
//        binding.txtCartCount.setText(String.valueOf(Params.getArrCart().size()));
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

    // on drawer navigating item select listener
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = current_page = item.getItemId(); // storing currently selected id

        if (id == R.id.nav_create_order) { // home fragment selected
            changeFragment(new Cart(), "Cart");
        } else if (id == R.id.nav_add_product) { // profile fragment selected
            changeFragment(new Product(), "Products");
        } else if (id == R.id.nav_add_category) { // profile fragment selected
            changeFragment(new Product(), "Products");
        } else if (id == R.id.nav_new_customer) { // Products fragment selected

        } else if (id == R.id.nav_show_history) { // category fragment
            changeFragment(new History(), "History");
        } else if (id == R.id.nav_logout) { // user click on settings
            logOut();
        } else if (id == R.id.nav_home) {
            changeFragment(new Home(), "Home");
        }
        bind.mainDrawerLayout.closeDrawer(GravityCompat.START); // when any item is click after that close the drawer
        return true;
    }

    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(bind.getRoot().getContext()); // Create an AlertDialog.Builder instance
        builder.setTitle("ProFarms"); // Set the title of the dialog
        builder.setMessage("Are you sure you want to log out?"); // Set the message of the dialog

        // Set the positive button of the dialog and its click listener
        builder.setPositiveButton("Yes", (dialog, which) -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE); // Get the SharedPreferences instance
            SharedPreferences.Editor editor = sharedPreferences.edit(); // Get the SharedPreferences.Editor instance
            editor.putBoolean("isLoggedIn", false); // Set the isLoggedIn flag to false
            editor.putString("userNameString", ""); // Set the userNameString to an empty string
            editor.apply(); // Apply the changes
            startActivity(new Intent(this, LoginActivity.class)); // Start the Login activity
            finish(); // Finish the current activity
        });

        // Set the negative button of the dialog and its click listener
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss(); // Dismiss the dialog
        });
        builder.show(); // Show the dialog

    }

    // change the fragment method
    private void changeFragment(Fragment fragment, String title) {
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_view, fragment).commit(); // changing the fragment that is given in argument
            bind.toolbar.setTitle(title); // setting title of the screen in action bar
        } catch (Exception e) {
            Log.d("ErrorMsg", "changeFragment: " + e.getMessage());
        }
    }
}