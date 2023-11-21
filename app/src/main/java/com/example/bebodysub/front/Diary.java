package com.example.bebodysub.front;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bebodysub.R;
import com.example.bebodysub.back.DiaryDBHelper;

public class Diary extends AppCompatActivity {
    //変数宣言
    private CalendarView calendarView;
    private Button popupButton;
    private String selectedDate;
    private DiaryDBHelper dbHelper;
    private SQLiteDatabase db;

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);//diary.xmlの読み込み

        calendarView = findViewById(R.id.calendarView);
        popupButton = findViewById(R.id.popupButton);

        // 日付の取得
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // 日付が選択されたときの処理
                selectedDate = year + "/" + (month + 1) + "/" + dayOfMonth;
                Log.d("カレンダーの日付", selectedDate);
            }
        });

        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ボタンがクリックされたとき
                if(selectedDate != null){
                    checkDay(selectedDate);
                } else{
                    // エラーハンドリング: 日付が選択されていない場合の処理
                    // ここにエラーメッセージを表示したり、ユーザーに通知したりするコードを追加できます
                    Toast.makeText(getApplicationContext(), "日付をクリックしてください", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool);

// アクションバーに戻るボタンを表示
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void checkDay(String day){
        dbHelper = new DiaryDBHelper(this);
        db = dbHelper.getReadableDatabase();

        //日付のデータがあるか確認
        boolean d = checkIfDataExists(db, day);


        if(d){
            //データある
            screenTransition(day);
        } else{
            //データないときはpopupDialog
            popupDialog(day);
        }
    }

    //日付があるか確認
    private boolean checkIfDataExists(SQLiteDatabase db, String day){
        String[] projection = {"day"};
        String selection = "day = ?";
        String[] selectionArgs = {day};

        Cursor cursor = db.query("diarydb", projection, selection, selectionArgs, null, null, null);

        boolean isDataExist = cursor.moveToFirst();

        // カーソルを閉じる
        cursor.close();

        return isDataExist;
    }

    private void screenTransition(String day){
        //画面遷移のためのIntent
        Intent intent = new Intent(this, DiaryDisplay.class);

        //日付をDiaryDisplayに送る
        intent.putExtra("SEND_DATE", day);
        startActivity(intent);
    }

    private void popupDialog(String day) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("日記の作成");
        String date = day; //日付
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.diary_popup_layout, null);
        builder.setView(dialogView);



        DiaryDBHelper dbHelper = new DiaryDBHelper(getBaseContext());

        builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // 画面遷移のためのIntentを作成
                Intent intent = new Intent(getApplicationContext(), DiaryMake.class);

                //日付をDiaryMakeに送る
                intent.putExtra("SEND_DATE", date);
                // 画面遷移を開始
                startActivity(intent);

                // ダイアログを閉じる
                dialog.dismiss();
            }
        });


        builder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //ダイアログを表示
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
