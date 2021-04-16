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

//    public void copyDb() throws IOException{
//        InputStream ip = context.getAssets().open(DbConstants.DB_NAME + ".db");
//        String op = DbConstants.DB_PATH + DbConstants.DB_NAME;
//        OutputStream output = new FileOutputStream(op);
//        byte[] buffer = new byte[1024];
//        int length;
//        while ((length = ip.read(buffer)) > 0){
//            output.write(buffer, 0, length);
//        }
//        output.flush();
//        output.close();
//        ip.close();
//    }

    public void getProducts(ArrayList<ProductState> states) {
        Cursor cursor = db.query(
            DbConstants.TABLE_STATIC_NAME,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
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

            states.add(new ProductState(name, cl, pr, ft, ch));
        }
        cursor.close();
    }

    public void openDb() throws SQLException {
        String myPath = DbConstants.DB_PATH + DbConstants.DB_STATIC_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public void closeDb() {
        dbHelper.close();
    }
}
