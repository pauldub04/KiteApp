package com.example.foodapp.db;

public class DbConstants {
    public static final String TABLE_NAME = "eaten";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_CALORIES = "calories";
    public static final String COLUMN_NAME_PROTEINS = "proteins";
    public static final String COLUMN_NAME_FATS = "fats";
    public static final String COLUMN_NAME_CARBOHYDRATES = "carbohydrates";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String _ID = "_id";

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "my_db.db";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_NAME + " TEXT," +
                    COLUMN_NAME_CALORIES + " INTEGER," +
                    COLUMN_NAME_PROTEINS + " INTEGER," +
                    COLUMN_NAME_FATS + " INTEGER," +
                    COLUMN_NAME_CARBOHYDRATES + " INTEGER," +
                    COLUMN_NAME_DATE + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


}
