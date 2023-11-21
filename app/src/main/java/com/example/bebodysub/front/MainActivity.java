package com.example.bebodysub.front;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.bebodysub.R;
import com.example.bebodysub.back.BebodyDBHelper;
import com.example.bebodysub.front.Goal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // データベースのファイルパスを指定
        String databasePath = this.getDatabasePath("Be-body").getAbsolutePath();

        try {
            // データベースファイルが存在するか確認
            if (!checkDatabaseExists(databasePath)) {
                // データベースファイルが存在しない場合の処理
                createDatabase(); // データベースを作成
            }

            // データベースファイルが存在する場合の処理
            SQLiteDatabase db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
            Log.d("テーブル存在確認", "データベースファイルが存在します");
            setContentView(R.layout.activity_main); //HOME画面

            //HOME画面の処理
            Button goal_btn = findViewById(R.id.goal_btn);
            Button sendButton = findViewById(R.id.send_button);
            Button planButton = findViewById(R.id.plan_button);

            //目標ボタンをクリックイベント
            goal_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("目標ボタン","目標ボタンおしゅいｔ");
                    Intent intent = new Intent(MainActivity.this, Goal.class);
                    startActivity(intent);
                }
            });
            sendButton.setOnClickListener(v -> {
                Intent intent = new Intent(getApplication(), Diary.class);
                startActivity(intent);
            });
            planButton.setOnClickListener(v -> {
                Intent intent = new Intent(getApplication(), Plan.class);
                startActivity(intent);
            });



        } catch (SQLiteCantOpenDatabaseException e) {
            // データベースが開けない例外の処理（初回登録のためデータベースが存在しない）
            Log.d("初回アクセス","データベースが存在しません。");
            createDatabase(); //データベース作成
            setContentView(R.layout.riyoukiyaku); //利用規約画面

            //利用規約画面の処理
            Button btn = findViewById(R.id.btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("利用規約ボタン","りようきやくボタンおしゅいｔ");
                    //アクティビティをintentにセットする
                    Intent intent = new Intent(MainActivity.this,Syokaitouroku.class);

                    //次のアクティビティに移動する(初回登録)
                    startActivity(intent);

                }
            });


        } catch (SQLException e) {
            // その他エラー
            Log.d("タグ","データベースにエラーでした。");
        }
    }

    //--------------------------------------メソッド-----------------------------------------------------------------

    // データベースファイルが存在するかを確認
    private boolean checkDatabaseExists(String databasePath) {
        return SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE) != null;
    }

    // データベースを作成
    private void createDatabase() {
        BebodyDBHelper bebodyDBHelper = new BebodyDBHelper(this);
        SQLiteDatabase db = bebodyDBHelper.getWritableDatabase();
        Log.d("データベース作成", "新しいデータベースを作成しました");
    }

    //--------------------------------------メソッド-----------------------------------------------------------------
}
