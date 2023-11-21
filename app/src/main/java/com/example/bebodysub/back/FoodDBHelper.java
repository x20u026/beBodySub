package com.example.bebodysub.back;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class FoodDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Be-body";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "Food";
    public static final String FOOD_ID = "food_id";
    public static final String FOOD_NAME = "food_name";
    public  static final String FOOD_KRL = "food_krl";
    public static final String FOOD_URL = "food_url";

    public FoodDBHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE " + TABLE_NAME + " (" +
                FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FOOD_NAME + " TEXT, " +
                FOOD_KRL + " REAL, " +
                FOOD_URL + "TEXT )";
        System.out.println(createTableSQL);
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
