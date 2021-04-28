package com.example.foodapp.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public void insertProduct(String name, float calories, float proteins, float fats, float carbohydrates, float grams, String date) {
        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_NAME, name);
        values.put(DbConstants.COLUMN_CALORIES, calories);
        values.put(DbConstants.COLUMN_PROTEINS, proteins);
        values.put(DbConstants.COLUMN_FATS, fats);
        values.put(DbConstants.COLUMN_CARBOHYDRATES, carbohydrates);
        values.put(DbConstants.COLUMN_GRAMS, grams);
        values.put(DbConstants.COLUMN_DATE, date);

        db.insert(DbConstants.TABLE_MAIN_NAME, null, values);
    }

    public void updateProduct(int id, String name, float calories, float proteins, float fats, float carbohydrates, float grams) {
        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_NAME, name);
        values.put(DbConstants.COLUMN_CALORIES, calories);
        values.put(DbConstants.COLUMN_PROTEINS, proteins);
        values.put(DbConstants.COLUMN_FATS, fats);
        values.put(DbConstants.COLUMN_CARBOHYDRATES, carbohydrates);
        values.put(DbConstants.COLUMN_GRAMS, grams);

//        db.insert(DbConstants.TABLE_MAIN_NAME, null, values);
        db.update(DbConstants.TABLE_MAIN_NAME, values, "_id=?", new String[]{id + ""});
    }

    public void deleteProduct(int id) {
        db.delete(DbConstants.TABLE_MAIN_NAME, "_id=?", new String[]{id + ""});
    }

    public void getFood(ArrayList<ProductState> states, String date) {
        Cursor cursor = db.query(
                DbConstants.TABLE_MAIN_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                "date=?",              // The columns for the WHERE clause
                new String[]{date},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_NAME));
//            name = name.substring(0, Math.min(name.length(), 30));
            float cl = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_CALORIES));
            float g = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_GRAMS));
            int id = cursor.getInt(cursor.getColumnIndex(DbConstants._ID));

            states.add(new ProductState(id, name, cl, 0, 0, 0, g));
        }
        cursor.close();

    }

    public void checkDay(String date) {
//        Log.d("KEK", date);
        @SuppressLint("Recycle") Cursor cursor = db.query(
                DbConstants.TABLE_USER_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                "date LIKE ?",              // The columns for the WHERE clause
                new String[] {date},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if (((cursor != null) && (cursor.getCount() > 0))) {
//            Log.d("KEK", "day");
            cursor.moveToFirst();
//            Log.d("KEK", cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_CALORIES))+ "sd");
            cursor.close();
        } else {
//            Log.d("KEK", "no day");
            ContentValues values = new ContentValues();
            values.put(DbConstants.COLUMN_DATE, date);
            db.insert(DbConstants.TABLE_USER_NAME, null, values);
        }

    }

    public void closeDb() {
        dbHelper.close();
    }

    public void clearDatabase() {
        String clearDBQuery = "DELETE FROM " + DbConstants.TABLE_MAIN_NAME;
        db.execSQL(clearDBQuery);
    }

}
