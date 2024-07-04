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
import com.example.profarma.fragment.Cart;
import com.example.profarma.model.Category;
import com.example.profarma.model.OrderCart;
import com.example.profarma.model.Product;

import java.util.ArrayList;

public class ProductList extends AppCompatActivity {

    private ActivityProductListBinding binding;
    private Category parentCategory;
    private ProductListAdapter productListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the category object from the intent
        if(getIntent().hasExtra("Category")) {
            parentCategory = (Category) getIntent().getSerializableExtra("Category");
        }

        Toast.makeText(this, parentCategory.getCategoryName()+parentCategory.getSubCategory(), Toast.LENGTH_SHORT).show();

        // Set the title of the activity
        setTitle(parentCategory.getCategoryName()+" "+parentCategory.getSubCategory());
        binding.txtTitleProductList.setText(parentCategory.getCategoryName()+" "+parentCategory.getSubCategory());
        binding.txtCartCount.setText(String.valueOf(OrderCart.getLsProduct().size()));

        // set recycler view
        ArrayList<Product> arrProducts = new ArrayList<>();
        if(parentCategory.getCategoryName().equals("New Arrival Products")){
            arrProducts.add(new Product("JUSS01", "Juss Sour Cheery Drink", "10", "100", R.drawable.n2,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("JUSS02", "Juss Orange Drink", "10", "100", R.drawable.n1,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("JUSS03", "Juss Pineapple Drink", "10", "100", R.drawable.n3,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("JUSS04", "Juss Mango Drink", "10", "100", R.drawable.n4,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("JUSS05", "Juss Grape Drink", "10", "100", R.drawable.n5,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
        } else if(parentCategory.getCategoryName().equals("Dairy")){
            arrProducts.add(new Product("1","Elmlea Double Cream","10","100",R.drawable.d1,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Elmlea Single Cream","10","100",R.drawable.d2,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Elmlea Squirty","10","100",R.drawable.d3,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Nature All UHT Oat Based Drink","10","100",R.drawable.d4,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Alpro Soya Drink Light","10","100",R.drawable.d5,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
        } else if(parentCategory.getCategoryName().equals("Cookies")){
            arrProducts.add(new Product("1","Meryem Hanim Cennet Sweet Cookies","10","100",R.drawable.c1,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Meryem Hanim Citri Sweet Cookies","10","100",R.drawable.c2,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Meryem Hanim Damla Sweet Cookies","10","100",R.drawable.c3,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Meryem Hanim Cookies W/Apple Filling","10","100",R.drawable.c4,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Meryem Hanim Cookies W/Cacao And Peanut","10","100",R.drawable.c5,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
        } else if(parentCategory.getCategoryName().equals("Cheese")){
            arrProducts.add(new Product("1","Yayla Clotted Cream / Taze Kaymak","10","100",R.drawable.ch1,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Yayla Koy Kaymagi","10","100",R.drawable.ch2,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Yayla White Cheese %60 Fat / Ciftlik Peynir","10","100",R.drawable.ch3,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Yayla White Cheese %55 Fat / Ciftlik Peynir","10","100",R.drawable.ch4,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Yayla White Cheese %45 Fat / Ciftlik Peynir","10","100",R.drawable.ch5,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
        }else if(parentCategory.getCategoryName().equals("Butter & Oil")){
            arrProducts.add(new Product("1","Country Life Unsalted Butter","10","100",R.drawable.b1,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Country Life Salted Butter","10","100",R.drawable.b2,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Willow Butter","10","100",R.drawable.b3,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Yayla Salted Butter","10","100",R.drawable.b4,parentCategory.getCategoryName(),parentCategory.getSubCategory()));
            arrProducts.add(new Product("1","Yayla Unsalted Butter","10","100",R.drawable.b5,parentCategory.getCategoryName(),parentCategory.getSubCategory()));

        }

        productListAdapter = new ProductListAdapter(arrProducts,this,binding);
        binding.recyclerViewProduct.setAdapter(productListAdapter);
        binding.recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this));

        binding.edProductSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Product> searchList = new ArrayList<>();
                for(Product product: arrProducts){
                    if(product.getProductName().toLowerCase().contains(query.toLowerCase())){
                        searchList.add(product);
                    }
                }
                productListAdapter.setLocalDataSet(searchList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Product> searchList = new ArrayList<>();
                for(Product product: arrProducts){
                    if(product.getProductName().toLowerCase().contains(newText.toLowerCase())){
                        searchList.add(product);
                    }
                }
                productListAdapter.setLocalDataSet(searchList);
                return true;
            }
        });

        binding.imgCart.setOnClickListener(v -> {
            Intent intent = new Intent(this, EmployeeHome.class);
            intent.putExtra("OpenCart","open Cart");
            startActivity(intent);
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