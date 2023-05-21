package com.example.foodapp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.foodapp.ProductState;

import java.io.IOException;
import java.util.ArrayList;

public class DbStaticManager {
    private Context context;
    private DbStaticHelper dbHelper;
    private SQLiteDatabase db;

    public DbStaticManager(Context ctx) {
        this.context = ctx;
        dbHelper = new DbStaticHelper(context);
    }

    public void createDb() throws IOException {
        dbHelper.getReadableDatabase();
        dbHelper.copyDb();
    }

    public void getProducts(ArrayList<ProductState> states, String s) {
        Cursor cursor;
        if (s.isEmpty())
            cursor = db.query(
                    DbConstants.TABLE_STATIC_NAME,   // The table to query
                    null,             // The array of columns to return (pass null to get all)
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null              // The sort order
            );
        else
            cursor = db.query(
                    DbConstants.TABLE_STATIC_NAME,   // The table to query
                    null,             // The array of columns to return (pass null to get all)
                    "name LIKE ?",              // The columns for the WHERE clause
                    new String[] {s + "%"},          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null              // The sort order
            );

        //DbConstants.COLUMN_NAME

        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_NAME));
            name = name.substring(0, Math.min(name.length(), 30));
            float cl = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_CALORIES));
            float pr = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_PROTEINS));
            float ft = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_FATS));
            float ch = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_CARBOHYDRATES));
            int id = cursor.getInt(cursor.getColumnIndex(DbConstants._ID));

            states.add(new ProductState(id, name, cl, pr, ft, ch, 0));
        }
        cursor.close();
    }

    public ProductState getProductById(ArrayList<ProductState> states, long _id) {
        Cursor cursor = db.query(
                DbConstants.TABLE_STATIC_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                "_id = ?",              // The columns for the WHERE clause
                new String[] {String.valueOf(_id)},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_NAME));
            name = name.substring(0, Math.min(name.length(), 30));
            float cl = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_CALORIES));
            float pr = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_PROTEINS));
            float ft = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_FATS));
            float ch = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_CARBOHYDRATES));
            int id = cursor.getInt(cursor.getColumnIndex(DbConstants._ID));

            return new ProductState(id, name, cl, pr, ft, ch, 100);
        }
        cursor.close();
        return null;
    }

    public void openDb() throws SQLException {
        String myPath = DbConstants.DB_PATH + DbConstants.DB_STATIC_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public void closeDb() {
        dbHelper.close();
    }
}
