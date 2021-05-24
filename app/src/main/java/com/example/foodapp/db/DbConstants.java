package com.example.foodapp.db;

public class DbConstants {
    public static final String DB_PATH = "data/data/com.example.foodapp/databases/";

    public static final String DB_STATIC_NAME = "food";
    public static final String DB_MAIN_NAME = "my_db.db";
    public static final String TABLE_STATIC_NAME = "products";
    public static final String TABLE_MAIN_NAME = "eaten";
    public static final String TABLE_USER_NAME = "stats";

    public static final int DB_STATIC_VERSION = 1;
    public static final int DB_MAIN_VERSION = 6;

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CALORIES = "calories";
    public static final String COLUMN_PROTEINS = "proteins";
    public static final String COLUMN_FATS = "fats";
    public static final String COLUMN_CARBOHYDRATES = "carbohydrates";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_GRAMS = "grams";
    public static final String COLUMN_DRINKS = "drinks";
    public static final String _ID = "_id";

    public static final String SQL_CREATE_ENTRIES_MAIN =
            "CREATE TABLE " + TABLE_MAIN_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_CALORIES + " REAL," +
                    COLUMN_PROTEINS + " REAL," +
                    COLUMN_FATS + " REAL," +
                    COLUMN_CARBOHYDRATES + " REAL," +
                    COLUMN_GRAMS + " REAL," +
                    COLUMN_DATE + " TEXT)";

    public static final String SQL_CREATE_ENTRIES_USER =
            "CREATE TABLE " + TABLE_USER_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_DATE + " TEXT," +
                    COLUMN_CALORIES + " REAL," +
                    COLUMN_PROTEINS + " REAL," +
                    COLUMN_FATS + " REAL," +
                    COLUMN_CARBOHYDRATES + " REAL," +
                    COLUMN_DRINKS + " INTEGER)";


    public static final String SQL_DELETE_ENTRIES_MAIN =
            "DROP TABLE IF EXISTS " + TABLE_MAIN_NAME;

    public static final String SQL_DELETE_ENTRIES_USER =
            "DROP TABLE IF EXISTS " + TABLE_USER_NAME;

}
