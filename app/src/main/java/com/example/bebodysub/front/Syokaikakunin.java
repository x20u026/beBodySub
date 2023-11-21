package com.example.bebodysub.front;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bebodysub.R;
import com.example.bebodysub.back.BebodyDBHelper;
import com.example.bebodysub.front.MainActivity;
import com.example.bebodysub.front.Syokaitouroku;

import java.text.SimpleDateFormat;

public class Syokaikakunin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syokaikakunin);

        Intent intent = getIntent();

        //intentからデータを取得する
        String name = intent.getStringExtra("name");
        String birthday = intent.getStringExtra("birthday");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String birthday_out = (String) outputFormat.format(Integer.parseInt(birthday));
        String height = intent.getStringExtra("height");
        String weight = intent.getStringExtra("weight");
        String sex = intent.getStringExtra("sex");

        //画面表示
        TextView name_txt = findViewById(R.id.name_txt);
        name_txt.setText(name);

        TextView birthday_txt = findViewById(R.id.birthday_txt);
        birthday_txt.setText(birthday_out);

        TextView height_txt = findViewById(R.id.height_txt);
        height_txt.setText(height);

        TextView weight_txt = findViewById(R.id.weight_txt);
        weight_txt.setText(weight);

        TextView sex_txt = findViewById(R.id.sex_txt);
        sex_txt.setText(sex);

        //設定テーブル
        String settingId = null;
        String settingValue = null;

        settingId = "1";
        settingValue = name;
        updateSettingValue(settingId, settingValue);

        settingId = "2";
        settingValue = birthday;
        updateSettingValue(settingId, settingValue);

        settingId = "3";
        settingValue = height;
        updateSettingValue(settingId, settingValue);

        settingId = "4";
        settingValue = weight;
        updateSettingValue(settingId,settingValue);

        settingId = "5";
        settingValue = sex;
        updateSettingValue(settingId,settingValue);


        //ボタンを取得する
        Button confirm_btn = findViewById(R.id.confirm_btn);
        Button change_btn = findViewById(R.id.change_btn);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //アクティビティをintentにセットする
                Intent intent = new Intent(Syokaikakunin.this, MainActivity.class);

                //次のアクティビティに移動する(HOME画面)
                startActivity(intent);

            }
        });

        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //アクティビティをintentにセットする
                Intent intent = new Intent(Syokaikakunin.this, Syokaitouroku.class);

                //次のアクティビティに移動する(初回登録)
                startActivity(intent);
            }
        });

    }

    //設定テーブルを更新する
    public void updateSettingValue(String settingId, String settingValue) {
        BebodyDBHelper dbHelper = new BebodyDBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BebodyDBHelper.SETTING_VALUE,settingValue);

        String whereClause = BebodyDBHelper.SETTING_ID + "=?";
        String[] whereArgs = new String[]{settingId};

        db.update(BebodyDBHelper.SETTINGTABLE_NAME, values, whereClause, whereArgs);

        db.close();


    }
}