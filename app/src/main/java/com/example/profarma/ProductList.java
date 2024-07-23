package com.example.profarma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.profarma.R;
import com.example.profarma.adapter.ProductListAdapter;
import com.example.profarma.databinding.ActivityProductListBinding;
import com.example.profarma.dbHandler.DbHandler;
import com.example.profarma.fragment.Cart;
import com.example.profarma.model.Category;
import com.example.profarma.model.OrderCart;
import com.example.profarma.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ProductList extends AppCompatActivity {

    private ActivityProductListBinding binding;
    private String parentCategory;
    private ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DbHandler dbHandler = new DbHandler(this);

        // Get the category object from the intent
        if (getIntent().hasExtra("Category")) {
            parentCategory = (String) getIntent().getSerializableExtra("Category");
        }

        ArrayList<String> subCategories = new ArrayList<String>() {{
            add("All");
            add("Large");
            add("Medium");
            add("Small");
        }};

        binding.spFilter.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subCategories));

        // Set the title of the activity
        setTitle(parentCategory);
        binding.txtTitleProductList.setText(parentCategory);
        binding.txtCartCount.setText(String.valueOf(Params.getArrCart().size()));

        // set recycler view
        ArrayList<String> arrProducts = new ArrayList<>(Params.getMapProductCategory().get(parentCategory));
        if (arrProducts.isEmpty()) {
            Toast.makeText(this, "No Products Available, Contact Admin To Add Products", Toast.LENGTH_SHORT).show();
        }

        productListAdapter = new ProductListAdapter(arrProducts, this, binding);
        binding.recyclerViewProduct.setAdapter(productListAdapter);
        binding.recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this));

        binding.edProductSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<String> searchList = new ArrayList<>();
                for (String product : arrProducts) {
                    Product productObj = Params.getMapProduct().get(product);
                    if (productObj.getProductName().toLowerCase().contains(query.toLowerCase())) {
                        searchList.add(product);
                    }
                }
                productListAdapter.setLocalDataSet(searchList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> searchList = new ArrayList<>();
                for (String product : arrProducts) {
                    Product productObj = Params.getMapProduct().get(product);
                    if (productObj.getProductName().toLowerCase().contains(newText.toLowerCase())) {
                        searchList.add(product);
                    }
                }
                productListAdapter.setLocalDataSet(searchList);
                return true;
            }
        });

        binding.imgCart.setOnClickListener(v -> {
            Intent intent = new Intent(this, EmployeeHome.class);
            intent.putExtra("OpenCart", "open Cart");
            startActivity(intent);
        });

        binding.spFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    productListAdapter.setLocalDataSet(arrProducts);
                    return;
                }
                ArrayList<String> filterList = new ArrayList<>();
                for (String product : arrProducts) {
                    Product productObj = Params.getMapProduct().get(product);
                    assert productObj != null;
                    if (productObj.getCategory().equals(parentCategory) && productObj.getSubCategory().equals(subCategories.get(position))) {
                        filterList.add(product);
                    }
                }
                productListAdapter.setLocalDataSet(filterList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                productListAdapter.setLocalDataSet(arrProducts);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, EmployeeHome.class);
        intent.putExtra("OpenHome", "open Home");
        startActivity(intent);
        super.onBackPressed();
    }
}