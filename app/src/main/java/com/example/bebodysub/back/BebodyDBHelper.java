package com.example.bebodysub.back;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BebodyDBHelper extends SQLiteOpenHelper{

    //データベースの定義
    private static final String DATABASE_NAME = "Be-body";
    private static final int DATABASE_VERSION = 1;

    //食品テーブルの定義
    public static final String FOODTABLE_NAME = "Food";
    public static final String FOOD_ID = "food_id";
    public static final String FOOD_NAME = "food_name";
    public  static final String FOOD_KRL = "food_krl";
    public static final String FOOD_URL = "food_url";

    //設定テーブルの定義
    public static final String SETTINGTABLE_NAME = "Setting";
    public static final String SETTING_ID = "setting_id";
    public static final String SETTING_NAME = "setting_name";
    public static final String SETTING_VALUE = "setting_value";

    //他テーブルの定義

    public BebodyDBHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //食品テーブルのSQL
        String createTableSQL = "CREATE TABLE " + FOODTABLE_NAME + " (" +
                FOOD_ID + " TEXT PRIMARY KEY, " +
                FOOD_NAME + " TEXT, " +
                FOOD_KRL + " REAL, " +
                FOOD_URL + " TEXT)";
        System.out.println(createTableSQL);
        db.execSQL(createTableSQL);

        //設定テーブルのSQL
        createTableSQL = "CREATE TABLE " + SETTINGTABLE_NAME + " (" +
                SETTING_ID + " TEXT PRIMARY KEY, " +
                SETTING_NAME + " TEXT, " +
                SETTING_VALUE + " TEXT)";
        System.out.println(createTableSQL);
        db.execSQL(createTableSQL);

        //設定テーブルの初期データ
        ContentValues values = new ContentValues();

        //氏名
        values.put(SETTING_ID,"1");
        values.put(SETTING_NAME,"氏名");
        values.put(SETTING_VALUE,"");
        db.insert(SETTINGTABLE_NAME,null,values);

        //生年月日
        values.put(SETTING_ID,"2");
        values.put(SETTING_NAME,"生年月日");
        values.put(SETTING_VALUE,"");
        db.insert(SETTINGTABLE_NAME,null,values);

        //身長
        values.put(SETTING_ID,"3");
        values.put(SETTING_NAME,"身長");
        values.put(SETTING_VALUE,"");
        db.insert(SETTINGTABLE_NAME,null,values);

        //体重
        values.put(SETTING_ID,"4");
        values.put(SETTING_NAME,"体重");
        values.put(SETTING_VALUE,"");
        db.insert(SETTINGTABLE_NAME,null,values);

        //性別
        values.put(SETTING_ID,"5");
        values.put(SETTING_NAME,"性別");
        values.put(SETTING_VALUE,"");
        db.insert(SETTINGTABLE_NAME,null,values);



        //他テーブルのSQL
    }

    //データの挿入
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


