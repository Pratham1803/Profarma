package com.example.profarma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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
    private Category parentCategory;
    private ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DbHandler dbHandler = new DbHandler(this);

        // Get the category object from the intent
        if (getIntent().hasExtra("Category")) {
            parentCategory = (Category) getIntent().getSerializableExtra("Category");
        }

        Toast.makeText(this, parentCategory.getCategoryName() + parentCategory.getSubCategory(), Toast.LENGTH_SHORT).show();

        // Set the title of the activity
        setTitle(parentCategory.getCategoryName() + " " + parentCategory.getSubCategory());
        binding.txtTitleProductList.setText(parentCategory.getCategoryName() + " " + parentCategory.getSubCategory());
        binding.txtCartCount.setText(String.valueOf(OrderCart.getLsProduct().size()));

        // set recycler view
        ArrayList<Product> arrProducts = dbHandler.getProducts(parentCategory.getCategoryName(), parentCategory.getSubCategory());
        if (arrProducts.isEmpty()) {
            Toast.makeText(this, "No Products Available, Contact Admin To Add Products", Toast.LENGTH_SHORT).show();
        }

        HashMap<String, ArrayList<Integer>> mapImage = new HashMap<>();
        mapImage.put("New Arrival Products", new ArrayList<Integer>() {{
            add(R.drawable.n1);
            add(R.drawable.n2);
            add(R.drawable.n3);
            add(R.drawable.n4);
            add(R.drawable.n5);
        }});
        mapImage.put("Dairy", new ArrayList<Integer>() {{
            add(R.drawable.d1);
            add(R.drawable.d2);
            add(R.drawable.d3);
            add(R.drawable.d4);
            add(R.drawable.d5);
        }});
        mapImage.put("Cookies", new ArrayList<Integer>() {{
            add(R.drawable.c1);
            add(R.drawable.c2);
            add(R.drawable.c3);
            add(R.drawable.c4);
            add(R.drawable.c5);
        }});
        mapImage.put("Cheese", new ArrayList<Integer>() {{
            add(R.drawable.ch1);
            add(R.drawable.ch2);
            add(R.drawable.ch3);
            add(R.drawable.ch4);
            add(R.drawable.ch5);
        }});
        mapImage.put("Butter & Oil", new ArrayList<Integer>() {{
            add(R.drawable.b1);
            add(R.drawable.b2);
            add(R.drawable.b3);
            add(R.drawable.b4);
            add(R.drawable.b5);
        }});

        for (Product product : arrProducts) {
            if (mapImage.containsKey(parentCategory.getCategoryName())) {
                Random random = new Random();
                int index = random.nextInt(mapImage.get(parentCategory.getCategoryName()).size());
                product.setImg(mapImage.get(parentCategory.getCategoryName()).get(index));
            }
        }

        productListAdapter = new ProductListAdapter(arrProducts, this, binding);
        binding.recyclerViewProduct.setAdapter(productListAdapter);
        binding.recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this));

        binding.edProductSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Product> searchList = new ArrayList<>();
                for (Product product : arrProducts) {
                    if (product.getProductName().toLowerCase().contains(query.toLowerCase())) {
                        searchList.add(product);
                    }
                }
                productListAdapter.setLocalDataSet(searchList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Product> searchList = new ArrayList<>();
                for (Product product : arrProducts) {
                    if (product.getProductName().toLowerCase().contains(newText.toLowerCase())) {
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
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this, EmployeeHome.class);
//        intent.putExtra("OpenHome", "open Home");
//        startActivity(intent);
//        super.onBackPressed();
//    }
}