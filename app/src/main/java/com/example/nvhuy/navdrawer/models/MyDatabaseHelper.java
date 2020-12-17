package com.example.nvhuy.navdrawer.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.nvhuy.navdrawer.R;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private  SQLiteDatabase db;
    private static final String DATABASE_NAME = "FRUITS.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCT = "PRODUCT";
    private static final String COLUMN_PRODUCT_ID = "ID";
    private static final String COLUMN_PRODUCT_NAME = "NAME";
    private static final String COLUMN_PRODUCT_COST ="COST" ;
    private static final String COLUMN_PRODUCT_COUNT ="COUNT" ;
    private static final String COLUMN_PRODUCT_IMAGE ="IMAGE" ;
    private static final String COLUMN_PRODUCT_UNIT ="UNIT" ;
    private static final String COLUMN_PRODUCT_COMPANY ="COMPANY" ;
    private static final String COLUMN_PRODUCT_DESCRIPTION = "DESCRIPTION" ;
    private static final String TAG = "SQLITEHELPER";

    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // Script to create table.
        String productTable = "CREATE TABLE " + TABLE_PRODUCT + "("
                + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_PRODUCT_NAME + " TEXT," + COLUMN_PRODUCT_COUNT + " INTEGER,"
                + COLUMN_PRODUCT_UNIT + " TEXT," + COLUMN_PRODUCT_COST + " TEXT,"
                + COLUMN_PRODUCT_COMPANY + " TEXT,"  + COLUMN_PRODUCT_DESCRIPTION + " TEXT,"
                + COLUMN_PRODUCT_IMAGE + " INTEGER " + ")";
        // Execute script.
        String imageTable = "CREATE TABLE " + TABLE_IMAGE+ "("
                + COLUMN_IMAGE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_PRODUCT_NAME + " TEXT," + COLUMN_PRODUCT_COUNT + " INTEGER,"
                + COLUMN_PRODUCT_UNIT + " TEXT," + COLUMN_PRODUCT_COST + " TEXT,"
                + COLUMN_PRODUCT_COMPANY + " TEXT,"  + COLUMN_PRODUCT_DESCRIPTION + " TEXT,"
                + COLUMN_PRODUCT_IMAGE + " INTEGER " + ")";
        db.execSQL(productTable);
        db.execSQL(imageTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);


        // Recreate
        onCreate(db);
    }
    public void insertProduct(Fruit fruit) {

        Log.i(TAG, "MyDatabaseHelper.insert ... "  );
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID, fruit.getId());
        values.put(COLUMN_PRODUCT_NAME, fruit.getName());
        values.put(COLUMN_PRODUCT_COUNT, fruit.getCount());
        values.put(COLUMN_PRODUCT_UNIT, fruit.getUnit());
        values.put(COLUMN_PRODUCT_COST, fruit.getCost());
        values.put(COLUMN_PRODUCT_COMPANY, fruit.getCompany());
        values.put(COLUMN_PRODUCT_DESCRIPTION, fruit.getDescription());
        values.put(COLUMN_PRODUCT_IMAGE, fruit.getImg());
        // Inserting Row
        db.insert(TABLE_PRODUCT, null, values);

        // Closing database connection
        db.close();
    }

    public int updateProduct(Fruit fruit) {


        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID, fruit.getId());
        values.put(COLUMN_PRODUCT_NAME, fruit.getName());
        values.put(COLUMN_PRODUCT_COUNT, fruit.getCount());
        values.put(COLUMN_PRODUCT_UNIT, fruit.getUnit());
        values.put(COLUMN_PRODUCT_COST, fruit.getCost());
        values.put(COLUMN_PRODUCT_COMPANY, fruit.getCompany());
        values.put(COLUMN_PRODUCT_DESCRIPTION, fruit.getDescription());
        values.put(COLUMN_PRODUCT_IMAGE, fruit.getImg());

        return db.update(TABLE_PRODUCT, values, COLUMN_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(fruit.getId())});

    }

    public void deleteProduct(Fruit fruit) {
        Log.i(TAG, "MyDatabaseHelper.update ... " + fruit.getName() );

        db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, COLUMN_PRODUCT_ID + " = ?",
                new String[] { String.valueOf(fruit.getId()) });
        db.close();
    }
    public ArrayList<Fruit> getAllProduct( ){
        db = this.getWritableDatabase();

        ArrayList<Fruit>mList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT;
        Cursor cursor = db.rawQuery(selectQuery, null);
        //Cursor cursor = myDB.query(true, TABLE_PRODUCT, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int count = cursor.getInt(2);
            String unit = cursor.getString(3);
            String cost = cursor.getString(4);
            String company = cursor.getString(5);
            String description = cursor.getString(6);
            int image = cursor.getInt(7);

            mList.add(new Fruit(id, name, count, unit, cost, company, description, image));
        }
        return mList;
    }
    public int getProductCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_PRODUCT;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public void createDefaultProductsIfNeed()  {
        int count = this.getProductCount();
        if(count ==0 ) {
            Fruit fruit1 = new Fruit(1, "apple", 10, "kg", "10000", "HaAnhFruits", "No", R.mipmap.fruit1);
            Fruit fruit2 = new Fruit(2, "apple", 10, "kg", "10000", "HaAnhFruits", "No", R.mipmap.fruit2);
            Fruit fruit3 = new Fruit(3, "apple", 10, "kg", "10000", "HaAnhFruits", "No", R.mipmap.fruit3);
            Fruit fruit4 = new Fruit(4, "apple", 10, "kg", "10000", "HaAnhFruits", "No", R.mipmap.fruit3);
            Fruit fruit5 = new Fruit(5, "apple", 10, "kg", "10000", "HaAnhFruits", "No", R.mipmap.fruit3);
            Fruit fruit6 = new Fruit(6, "apple", 10, "kg", "10000", "HaAnhFruits", "No", R.mipmap.fruit3);

            this.insertProduct(fruit1);
            this.insertProduct(fruit2);
            this.insertProduct(fruit3);
            this.insertProduct(fruit4);
            this.insertProduct(fruit5);
            this.insertProduct(fruit6);

        }
    }

}