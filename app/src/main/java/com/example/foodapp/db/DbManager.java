package com.example.foodapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.foodapp.ProductState;

import java.util.ArrayList;
import java.util.List;

public class DbManager {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DbManager(Context ctx) {
        this.context = ctx;
        dbHelper = new DbHelper(context);
    }

    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    public void insertProduct(String name, float calories, float proteins, float fats, float carbohydrates, float grams) {
        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_NAME, name);
        values.put(DbConstants.COLUMN_CALORIES, calories);
        values.put(DbConstants.COLUMN_PROTEINS, proteins);
        values.put(DbConstants.COLUMN_FATS, fats);
        values.put(DbConstants.COLUMN_CARBOHYDRATES, carbohydrates);
        values.put(DbConstants.COLUMN_GRAMS, grams);

        db.insert(DbConstants.TABLE_MAIN_NAME, null, values);
    }

    public void getFood(ArrayList<ProductState> states) {
        Cursor cursor = db.query(
                DbConstants.TABLE_MAIN_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_NAME));
//            name = name.substring(0, Math.min(name.length(), 30));
            float cl = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_CALORIES));
            float g = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_GRAMS));

            states.add(new ProductState(name, cl, 0, 0, 0, g));
        }
        cursor.close();

    }

    public void closeDb() {
        dbHelper.close();
    }

    public void clearDatabase() {
        String clearDBQuery = "DELETE FROM " + DbConstants.TABLE_MAIN_NAME;
        db.execSQL(clearDBQuery);
    }

}
