package com.example.profarma;

import com.example.profarma.model.ProductModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class Params {
    private static final Params ourInstance = new Params(); // Instance of the Params class
    private static DatabaseReference dbReference; // Firebase database reference to refer the database
    private static StorageReference storageReference; // Firebase storage reference to store the files and images
    private static HashMap<String, ProductModel> mapProduct; // Map of products
    private static HashMap<String, ArrayList<String>> mapProductCategory; // Map of product categories
    private static ArrayList<String> arrCart; // List of product categories

    // Get the instance of the Params class
    private Params() {
        dbReference = FirebaseDatabase.getInstance().getReference(); // Get the firebase database reference
        storageReference = FirebaseStorage.getInstance().getReference(); // Get the firebase storage reference
        mapProduct = new HashMap<>(); // Initialize the map of products
        mapProductCategory = new HashMap<>(); // Initialize the map of product categories
        arrCart = new ArrayList<>(); // Initialize the list of product categories
    }

    public static ArrayList<String> getArrCart() {
        return arrCart;
    }

    public static void setArrCart(ArrayList<String> arrCart) {
        Params.arrCart = arrCart;
    }

    public static DatabaseReference getDbReference() {
        return dbReference;
    }

    public static void setDbReference(DatabaseReference dbReference) {
        Params.dbReference = dbReference;
    }

    public static StorageReference getFirebaseStorage() {
        return storageReference;
    }

    public static void setStorageReference(StorageReference storageReference) {
        Params.storageReference = storageReference;
    }

    public static HashMap<String, ProductModel> getMapProduct() {
        return mapProduct;
    }

    public static void setMapProduct(HashMap<String, ProductModel> mapProduct) {
        Params.mapProduct = mapProduct;
    }

    public static HashMap<String, ArrayList<String>> getMapProductCategory() {
        return mapProductCategory;
    }

    public static void setMapProductCategory(HashMap<String, ArrayList<String>> mapProductCategory) {
        Params.mapProductCategory = mapProductCategory;
    }
}
