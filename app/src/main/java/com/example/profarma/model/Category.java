package com.example.profarma.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private String categoryId;
    private String categoryName;
    private ArrayList<String> arrProducts;
    private String subCategory;

    public Category(String categoryId, String categoryName, ArrayList<String> arrProducts, String subCategory) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.arrProducts = arrProducts;
        this.subCategory = subCategory;
    }

    public Category(String categoryId, String categoryName, ArrayList<String> arrProducts) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.arrProducts = arrProducts;
        this.subCategory = "";
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<String> getArrProducts() {
        return arrProducts;
    }

    public void setArrProducts(ArrayList<String> arrProducts) {
        this.arrProducts = arrProducts;
    }
}
