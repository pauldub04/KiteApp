package com.example.foodapp.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.foodapp.ProductState;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

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

    @SuppressLint("SetTextI18n")
    public void updateProgress(ProgressBar p, TextView t, String field, String text, String max, String date) {
        checkDay(date);

        @SuppressLint("Recycle") Cursor cursor = db.query(
                DbConstants.TABLE_USER_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                "date LIKE ?",              // The columns for the WHERE clause
                new String[] {date},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        cursor.moveToFirst();
        float val = cursor.getFloat(cursor.getColumnIndex(field));
//        float val = 0;
        cursor.close();

        p.setProgress((int) val);
        t.setText(text + "   " + ((int) val) + max);
    }

    @SuppressLint("SetTextI18n")
    public ArrayList<Float> updateProgress(String date) {
        checkDay(date);

        @SuppressLint("Recycle") Cursor cursor = db.query(
                DbConstants.TABLE_USER_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                "date LIKE ?",              // The columns for the WHERE clause
                new String[] {date},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        cursor.moveToFirst();
        float c = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_CALORIES));
        float p = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_PROTEINS));
        float f = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_FATS));
        float ch = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_CARBOHYDRATES));
        cursor.close();

        ArrayList<Float> x = new ArrayList<>();
        x.add(c);
        x.add(p);
        x.add(f);
        x.add(ch);

        return x;
    }

    public void insertProduct(String name, float calories, float proteins, float fats, float carbohydrates, float grams, String date) {
        checkDay(date);
        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_NAME, name);
        values.put(DbConstants.COLUMN_CALORIES, calories);
        values.put(DbConstants.COLUMN_PROTEINS, proteins);
        values.put(DbConstants.COLUMN_FATS, fats);
        values.put(DbConstants.COLUMN_CARBOHYDRATES, carbohydrates);
        values.put(DbConstants.COLUMN_GRAMS, grams);
        values.put(DbConstants.COLUMN_DATE, date);

        Log.d("KEK", date);
        @SuppressLint("Recycle") Cursor cursor = db.query(
                DbConstants.TABLE_USER_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                "date LIKE ?",              // The columns for the WHERE clause
                new String[] {date},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        cursor.moveToFirst();
        float c = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_CALORIES));
        float p = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_PROTEINS));
        float f = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_FATS));
        float ch = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_CARBOHYDRATES));
        cursor.close();

        c += calories;
        p += proteins;
        f += fats;
        ch += carbohydrates;

        ContentValues values2 = new ContentValues();
        values2.put(DbConstants.COLUMN_CALORIES, c);
        values2.put(DbConstants.COLUMN_PROTEINS, p);
        values2.put(DbConstants.COLUMN_FATS, f);
        values2.put(DbConstants.COLUMN_CARBOHYDRATES, ch);
        db.update(DbConstants.TABLE_USER_NAME, values2, "date LIKE ?", new String[] {date});

        db.insert(DbConstants.TABLE_MAIN_NAME, null, values);
    }

    public void updateProduct(int id, String name, float old_calories, float old_proteins, float old_fats, float old_carbohydrates,
                              float calories, float proteins, float fats, float carbohydrates, float grams, String date) {
        checkDay(date);
        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_NAME, name);
        values.put(DbConstants.COLUMN_CALORIES, calories);
        values.put(DbConstants.COLUMN_PROTEINS, proteins);
        values.put(DbConstants.COLUMN_FATS, fats);
        values.put(DbConstants.COLUMN_CARBOHYDRATES, carbohydrates);
        values.put(DbConstants.COLUMN_GRAMS, grams);

        @SuppressLint("Recycle") Cursor cursor = db.query(
                DbConstants.TABLE_USER_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                "date LIKE ?",              // The columns for the WHERE clause
                new String[] {date},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        cursor.moveToFirst();
        float c = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_CALORIES));
        float p = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_PROTEINS));
        float f = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_FATS));
        float ch = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_CARBOHYDRATES));
        cursor.close();

        c = c - old_calories + calories;
        p = p - old_proteins + proteins;
        f = f - old_fats + fats;
        ch = ch - old_carbohydrates + carbohydrates;

        ContentValues values2 = new ContentValues();
        values2.put(DbConstants.COLUMN_CALORIES, c);
        values2.put(DbConstants.COLUMN_PROTEINS, p);
        values2.put(DbConstants.COLUMN_FATS, f);
        values2.put(DbConstants.COLUMN_CARBOHYDRATES, ch);
        db.update(DbConstants.TABLE_USER_NAME, values2, "date LIKE ?", new String[] {date});

        db.update(DbConstants.TABLE_MAIN_NAME, values, "_id=?", new String[]{id + ""});
    }

    public void deleteProduct(int id, float old_calories, float old_proteins, float old_fats, float old_carbohydrates, String date) {
        checkDay(date);
        @SuppressLint("Recycle") Cursor cursor = db.query(
                DbConstants.TABLE_USER_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                "date LIKE ?",              // The columns for the WHERE clause
                new String[] {date},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        cursor.moveToFirst();
        float c = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_CALORIES));
        float p = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_PROTEINS));
        float f = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_FATS));
        float ch = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_CARBOHYDRATES));
        cursor.close();

        Log.d("KEK", old_calories+" "+old_proteins+" "+old_fats+" "+old_carbohydrates);

        c = c - old_calories;
        p = p - old_proteins;
        f = f - old_fats;
        ch = ch - old_carbohydrates;

        ContentValues values2 = new ContentValues();
        values2.put(DbConstants.COLUMN_CALORIES, c);
        values2.put(DbConstants.COLUMN_PROTEINS, p);
        values2.put(DbConstants.COLUMN_FATS, f);
        values2.put(DbConstants.COLUMN_CARBOHYDRATES, ch);
        db.update(DbConstants.TABLE_USER_NAME, values2, "date LIKE ?", new String[] {date});

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
            float p = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_PROTEINS));
            float f = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_FATS));
            float ch = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_CARBOHYDRATES));
            float g = cursor.getFloat(cursor.getColumnIndex(DbConstants.COLUMN_GRAMS));
            int id = cursor.getInt(cursor.getColumnIndex(DbConstants._ID));

            states.add(new ProductState(id, name, cl, p, f, ch, g));
        }
        cursor.close();

    }

    @SuppressLint("SetTextI18n")
    public void getDrink(TextView t, String date) {
        checkDay(date);
        @SuppressLint("Recycle") Cursor cursor = db.query(
                DbConstants.TABLE_USER_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                "date LIKE ?",              // The columns for the WHERE clause
                new String[] {date},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        cursor.moveToFirst();
        int d = cursor.getInt(cursor.getColumnIndex(DbConstants.COLUMN_DRINKS));
        cursor.close();

        t.setText(d+"");

        Log.d("KEK", d+"");
    }

    public void changeDrink(int delta, String date) {
        checkDay(date);
        @SuppressLint("Recycle") Cursor cursor = db.query(
                DbConstants.TABLE_USER_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                "date LIKE ?",              // The columns for the WHERE clause
                new String[] {date},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        cursor.moveToFirst();
        int d = cursor.getInt(cursor.getColumnIndex(DbConstants.COLUMN_DRINKS));
        cursor.close();

        d += delta;
        d = max(d, 0);

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_DRINKS, d);
        db.update(DbConstants.TABLE_USER_NAME, values, "date LIKE ?", new String[] {date});
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
        String clearDBQuery2 = "DELETE FROM " + DbConstants.TABLE_USER_NAME;
        db.execSQL(clearDBQuery);
        db.execSQL(clearDBQuery2);
    }

}
