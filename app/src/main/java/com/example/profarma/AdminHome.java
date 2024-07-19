package com.example.profarma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.profarma.databinding.ActivityAdminHomeBinding;
import com.example.profarma.databinding.AddProductLayoutBinding;
import com.example.profarma.databinding.LayoutProductBinding;
import com.example.profarma.dbHandler.DbHandler;
import com.example.profarma.model.Product;

import java.util.ArrayList;

public class AdminHome extends AppCompatActivity {
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
                AddProductLayoutBinding layoutProductBinding = AddProductLayoutBinding.inflate(getLayoutInflater());
                builder.setView(layoutProductBinding.getRoot());

                ArrayList<String> categories = new ArrayList<String>() {{
                    add("New Arrival Products");
                    add("Dairy");
                    add("Cookies");
                    add("Cheese");
                    add("Butter & Oil");
                }};

                ArrayList<String> subCategories = new ArrayList<String>() {{
                    add("Large");
                    add("Medium");
                    add("Small");
                }};

                layoutProductBinding.cardAddImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
                        Toast.makeText(AdminHome.this, "Coming Soon!", Toast.LENGTH_LONG).show();
                    }
                });

                layoutProductBinding.spCategory.setAdapter(new ArrayAdapter<String>(AdminHome.this, android.R.layout.simple_spinner_dropdown_item, categories));
                layoutProductBinding.spSubCategory.setAdapter(new ArrayAdapter<String>(AdminHome.this, android.R.layout.simple_spinner_dropdown_item, subCategories));

                addProduct(layoutProductBinding);

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

        binding.layoutAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
                Toast.makeText(AdminHome.this, "Admin Can Generate New Employee Details Here Ex. Id, Password and Name!", Toast.LENGTH_LONG).show();
            }
        });

        binding.layoutEmployeeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
                Toast.makeText(AdminHome.this, "Admin Can View And Manage All Employee Details Here!", Toast.LENGTH_LONG).show();
            }
        });

        binding.layoutProductDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
                Toast.makeText(AdminHome.this, "Admin Can View And Manage All Product Details Here!", Toast.LENGTH_LONG).show();
            }
        });

        binding.layoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
                Toast.makeText(AdminHome.this, "Admin Can View And Manage Admin Details Here! Like Update Profile, Password, Names", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addProduct(AddProductLayoutBinding layoutProductBinding) {
        layoutProductBinding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
                DbHandler dbHandler = new DbHandler(AdminHome.this);

                Product product = new Product();
                product.setProductName(layoutProductBinding.edtProductName.getText().toString());
                product.setQuantity(layoutProductBinding.edtProductQuan.getText().toString());
                product.setPrice(layoutProductBinding.edtProductPrice.getText().toString());
                product.setCategory(layoutProductBinding.spCategory.getSelectedItem().toString());
                product.setSubCategory(layoutProductBinding.spSubCategory.getSelectedItem().toString());

                if(isDetailsFilled(product)) {
                    reset(layoutProductBinding);
                    dbHandler.addRecord(product);
                    Toast.makeText(AdminHome.this, "Product Added Successfully!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isDetailsFilled(Product product){
        if(product.getProductName().isEmpty() || product.getQuantity().isEmpty() || product.getPrice().isEmpty()){
            Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void reset(AddProductLayoutBinding layoutProductBinding ){
        layoutProductBinding.edtProductName.setText("");
        layoutProductBinding.edtProductQuan.setText("");
        layoutProductBinding.edtProductPrice.setText("");
    }
}