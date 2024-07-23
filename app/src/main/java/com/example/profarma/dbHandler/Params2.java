package com.example.profarma.dbHandler;

public class Params2 {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dbProduct";
    public static final String TABLE_NAME = "tblProduct";

    // key id
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_PRICE = "price";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_SUBCATEGORY = "subCategory";


    // create query
    public static final String CREATE_ENTRY = "CREATE TABLE " + TABLE_NAME + "( "+
            KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            KEY_NAME + " TEXT, "+
            KEY_QUANTITY + " TEXT, "+
            KEY_PRICE + " TEXT, "+
            KEY_CATEGORY + " TEXT, "+
            KEY_SUBCATEGORY + " TEXT);";
}
