package com.example.foodapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DbStaticHelper extends SQLiteOpenHelper {
    private final Context context;

    public DbStaticHelper(Context context) {
        super(context, DbConstants.DB_STATIC_NAME, null, DbConstants.DB_STATIC_VERSION);
        this.context = context;
    }

    public void copyDb() throws IOException {
        InputStream ip = context.getAssets().open(DbConstants.DB_STATIC_NAME + ".db");
        String op = DbConstants.DB_PATH + DbConstants.DB_STATIC_NAME;
        OutputStream output = new FileOutputStream(op);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = ip.read(buffer)) > 0){
            output.write(buffer, 0, length);
        }
        output.flush();
        output.close();
        ip.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
