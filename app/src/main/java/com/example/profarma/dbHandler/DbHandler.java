package com.example.profarma.dbHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.profarma.model.Product;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
    Context context;
    public DbHandler(Context context) {
        super(context, Params2.DATABASE_NAME, null, Params2.DATABASE_VERSION);
        this.context = context;
        Log.d("dbQuery", "Database created 1");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Params2.CREATE_ENTRY);
        Log.d("dbQuery", "Table Created: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String cmd = "drop table "+ Params2.TABLE_NAME;
        db.execSQL(cmd);
    }

    public void addRecord(Product p){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(Params2.KEY_NAME, p.getProductName());
            values.put(Params2.KEY_QUANTITY, p.getQuantity());
            values.put(Params2.KEY_PRICE, p.getPrice());
            values.put(Params2.KEY_CATEGORY, p.getCategory());
            values.put(Params2.KEY_SUBCATEGORY, p.getSubCategory());

            long l = db.insert(Params2.TABLE_NAME, null, values);
            Log.d("dbQuery", "Record added" + l);
            db.close();
            Toast.makeText(context, "Data Inserted!!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Product> getProducts(String category, String subCategory){
        ArrayList<Product> products = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String cmd = "SELECT * FROM " + Params2.TABLE_NAME + " WHERE " + Params2.KEY_CATEGORY + " = '" + category + "' AND " + Params2.KEY_SUBCATEGORY + " = '" + subCategory + "'";

            Cursor cursor = db.rawQuery(cmd, null);
            Log.d("dbQuery", "Cursor: " + cursor);
            if (cursor.moveToFirst()) {
                do {
                    Product product = new Product();
                    product.setProductId(cursor.getString(0));
                    product.setProductName(cursor.getString(1));
                    product.setQuantity(cursor.getString(2));
                    product.setPrice(cursor.getString(3));
                    product.setCategory(cursor.getString(4));
                    product.setSubCategory(cursor.getString(5));
                    products.add(product);
                    Log.d("dbQuery", "record : " + product);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
        return products;
    }

//    public void updateDb(Contact c){
//        try {
//            String cmd = String.format("update %s set %s = '%s', %s = '%s' where %s= %s;", Params.TABLE_NAME, Params.KEY_NAME, c.getName(), Params.KEY_CONTACT, c.getContactNum(), Params.KEY_ID, c.getsNo());
//            SQLiteDatabase db = this.getWritableDatabase();
//            db.execSQL(cmd);
//            Toast.makeText(context, "Data Updated!!", Toast.LENGTH_SHORT).show();
//        }catch (Exception e){
//            Toast.makeText(context, "Something Went Wrog!!", Toast.LENGTH_SHORT).show();
//        }
//    }

    public Contact findContact(int id){
        Contact c = new Contact();
        SQLiteDatabase db = this.getReadableDatabase();
        String cmd = "Select * from "+ Params2.TABLE_NAME+" where "+ Params2.KEY_ID+" = "+id;

        Cursor cursor = db.rawQuery(cmd,null);
        cursor.moveToFirst();
        c.setsNo(Integer.parseInt(cursor.getString(0)));
        c.setName(cursor.getString(1));
        c.setContactNum(cursor.getString(2));

        cursor.close();
        Log.d("dbQuery", "ID: "+c.getName());
        return c;
    }

    public List<Integer> getAllId(){
        List<Integer> arrId = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String cmd = "Select "+ Params2.KEY_ID+ " from "+ Params2.TABLE_NAME;

        Cursor cursor = db.rawQuery(cmd,null);

        if(cursor.moveToFirst()){
            do {
                arrId.add(Integer.parseInt(cursor.getString(0)));
            }while (cursor.moveToNext());
        }

        cursor.close();;
        return arrId;
    }

    public void delete(int id){
        try {
            String cmd = String.format("delete from %s where %s= %s;", Params2.TABLE_NAME, Params2.KEY_ID,id);
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(cmd);
            Toast.makeText(context, "Record Removed!!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context, "Something Went Wrog!!", Toast.LENGTH_SHORT).show();
        }
    }
}
