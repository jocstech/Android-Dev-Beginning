/**
 * Coded By Yulong Fang #100471536
 */


package csci4100.uoit.ca.a2pricebrowsing;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ProductDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILENAME = "products.db";

    private static final String CREATE_STATEMENT = "" +
            "create table if not exists products("+
            "productId integer primary key,"+
            "name varchar(100) not null,"+
            "description varchar(600) not null,"+
            "price decimal not null)";

    private static final String DROP_STATEMENT = "" +
            "drop table products";

    public ProductDBHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_STATEMENT);
        db.execSQL(CREATE_STATEMENT);
    }

    public int getCounts(){
        String countQuery = "SELECT * FROM products";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void deleteAllProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("products", "", new String[] {});
    }


    public void deleteProductById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("products", "productId = ?", new String[] { ""+id });
    }

    public Product addNewProduct(int productId, String name, String description,float price) {
        // insert the contact data into the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("productId", productId);
        values.put("name", name);
        values.put("description",description);
        values.put("price", price);

        db.insert("products", null, values);

        // create a new contact object
        Product grade = new Product(productId, name, description, price);
        return grade;
    }

    public ArrayList<Product> getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Product> results = new ArrayList<>();

        String[] columns = new String[] {
                "productId",
                "name",
                "description",
                "price"};
        String where = "";  // all Products
        String[] whereArgs = new String[] {};
        String groupBy = "";  // no grouping
        String groupArgs = "";
        String orderBy = "";

        Cursor cursor = db.query("products", columns, where, whereArgs,
                groupBy, groupArgs, orderBy);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int productId = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            float price = cursor.getFloat(3);

            results.add(new Product(productId,name,description,price ));

            cursor.moveToNext();
        }

        return results;
    }

    public boolean updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("productId", product.getProductId());
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());


        int numRows = db.update("products",
                values,
                "productId = ?",
                new String[] {""+product.getProductId()});
        return (numRows == 1);
    }

}
