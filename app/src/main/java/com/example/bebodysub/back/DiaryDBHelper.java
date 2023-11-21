package com.example.bebodysub.back;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DiaryDBHelper extends SQLiteOpenHelper {
    // データベースバージョン
    private static final int DATABASE_VERSION = 3;
    // データベース名
    private static final String DATABASE_NAME = "m_diary.db";
    public static final String TABLE_NAME = "diarydb";
    public static final String DAY = "day"; // 日付 PRIMARY KEY
    private static final String COLUMN_IMAGE = "image"; // 画像
    public static final String COLUMN_HEIGHT = "height"; // 身長 NOT NULL
    public static final String COLUMN_WEIGHT = "weight"; // 体重 NOT NULL
    public static final String COLUMN_INKRL = "in_krl"; // 摂取カロリー NOT NULL
    public static final String COLUMN_OUTKRL = "out_krl"; // 消費カロリー NOT NULL
    private static final String COLUMN_BREAKFAST = "breakfast"; // 朝ごはん
    private static final String COLUMN_LUNCH = "lunch"; // 昼ごはん
    private static final String COLUMN_DINNER = "dinner"; // 夜ごはん
    private static final String COLUMN_WORKOUT = "workout"; // ワークアウト
    public static final String COLUMN_COMMENT = "comment"; // コメント

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    DAY + " DATE PRIMARY KEY," +
                    COLUMN_IMAGE + " TEXT," +
                    COLUMN_HEIGHT + " REAL NOT NULL," +
                    COLUMN_WEIGHT + " REAL NOT NULL," +
                    COLUMN_INKRL + " REAL NOT NULL," +
                    COLUMN_OUTKRL + " REAL NOT NULL," +
                    COLUMN_BREAKFAST + " TEXT UNIQUE," +
                    COLUMN_LUNCH + " TEXT UNIQUE," +
                    COLUMN_DINNER + " TEXT UNIQUE," +
                    COLUMN_WORKOUT + " TEXT UNIQUE," +
                    COLUMN_COMMENT + " TEXT)";



//    INTEGER    符号付整数。1, 2, 3, 4, 6, or 8 バイトで格納
//    REAL       浮動小数点数。8バイトで格納
//    TEXT       テキスト。UTF-8, UTF-16BE or UTF-16-LEのいずれかで格納

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DiaryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    // ゲッターメソッド
    public String getTableName() {
        return TABLE_NAME;
    }

    public String getDay() {
        return DAY;
    }
    public String getColumnImage(){
        return COLUMN_IMAGE;
    }

    public String getColumnHeight() {
        return COLUMN_HEIGHT;
    }

    public String getColumnWeight() {
        return COLUMN_WEIGHT;
    }

    public String getColumnInKrl() {
        return COLUMN_INKRL;
    }

    public String getColumnOutKrl() {
        return COLUMN_OUTKRL;
    }

    public String getColumnComment() {
        return COLUMN_COMMENT;
    }


    @SuppressLint("Range")
    //　画像のパス取得
    public String getImagePath(String selectedDate) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_IMAGE};
        String selection = DAY + "=?";
        String[] selectionArgs = {selectedDate};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        String imagePath = null;

        if (cursor.moveToFirst()) {
            imagePath = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
        }

        cursor.close();
        return imagePath;
    }


}

