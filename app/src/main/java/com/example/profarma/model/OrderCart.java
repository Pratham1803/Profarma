package com.example.profarma.model;

import java.util.ArrayList;

public class OrderCart {
    private static ArrayList<Product> lsProduct = new ArrayList<>();

    public static ArrayList<Product> getLsProduct() {
        return lsProduct;
    }

    public static void setLsProduct(ArrayList<Product> lsProduct) {
        OrderCart.lsProduct = lsProduct;
    }
}
