package com.example.foodapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public void insert(String name, int calories, int proteins, int fats, int carbohydrates) {
        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_NAME, name);
        values.put(DbConstants.COLUMN_CALORIES, calories);
        values.put(DbConstants.COLUMN_PROTEINS, proteins);
        values.put(DbConstants.COLUMN_FATS, fats);
        values.put(DbConstants.COLUMN_CARBOHYDRATES, carbohydrates);
//        values.put(DbConstants.COLUMN_NAME_DATE, date);

//        long newRowId =
        db.insert(DbConstants.TABLE_MAIN_NAME, null, values);
    }

    public List<String> getFromDb() {
        Cursor cursor = db.query(
                DbConstants.TABLE_MAIN_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<String> tmp = new ArrayList<>();
        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_NAME));
            String cl = cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_CALORIES));
            String pr = cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_PROTEINS));
            String ft = cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_FATS));
            String ch = cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_CARBOHYDRATES));

            tmp.add(name + " к: " + cl + " б: " + pr + " ж: " + ft + " у: " + ch);
        }
        cursor.close();

        return tmp;
    }

    public void closeDb() {
        dbHelper.close();
    }

    public void clearDatabase() {
        String clearDBQuery = "DELETE FROM " + DbConstants.TABLE_MAIN_NAME;
        db.execSQL(clearDBQuery);
    }

}
