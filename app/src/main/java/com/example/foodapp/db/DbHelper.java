package com.example.foodapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, DbConstants.DB_MAIN_NAME, null, DbConstants.DB_MAIN_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbConstants.SQL_CREATE_ENTRIES_MAIN);
        db.execSQL(DbConstants.SQL_CREATE_ENTRIES_USER);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbConstants.SQL_DELETE_ENTRIES_MAIN);
        db.execSQL(DbConstants.SQL_DELETE_ENTRIES_USER);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}