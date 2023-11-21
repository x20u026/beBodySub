package com.example.bebodysub.front;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bebodysub.R;
import com.example.bebodysub.back.BebodyDBHelper;

import java.text.DecimalFormat;

public class Goal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal);

        //少数フォーマットを定義
        DecimalFormat decimalFormat = new DecimalFormat("#.#");

        //身長と体重を取得しBMIを計算する
        String SettingId = "3";
        String height = GetSettingValue(SettingId);
        Double height_double = Double.parseDouble(height)/100.0;

        SettingId = "4";
        String weight = GetSettingValue(SettingId);;
        Double weight_double = Double.parseDouble(weight);

        Double bmi = weight_double/(Math.pow(height_double,2));

        //体重とBMIを表示する
        TextView weight_txt = findViewById(R.id.weight_txt);
        weight_txt.setText(weight);

        TextView bmi_txt = findViewById(R.id.bmi_txt);
        bmi_txt.setText(String.valueOf(decimalFormat.format(bmi)));

        //ボタンイベント
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //入力データを取得する
//                EditText goalweight_ext = findViewById(R.id.goalweight_ext);
//                Double goalweight = Double.parseDouble(String.valueOf(goalweight_ext.getText()));
//
//                EditText tri_num_ext = findViewById(R.id.tri_num_ext);
//                Integer tri_num  = Integer.valueOf(String.valueOf(tri_num_ext.getText()));

                //データから計算する



            }
        });

    }

    //データベースからデータを取得する
    public String GetSettingValue(String SettingId) {
        BebodyDBHelper dbHelper = new BebodyDBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String value = null;

        // 取得したい列を定義します
        String[] projection = {
                "setting_value",
        };

        // 必要に応じて選択基準を定義します
        String selection = "setting_id = ?";
        String[] selectionArgs = {SettingId};

        // クエリを実行し、結果を取得します
        Cursor cursor = db.query(
                "Setting",   // クエリするテーブル
                projection,          // 取得する列の配列
                selection,           // WHERE 句の列
                selectionArgs,       // WHERE 句の値
                null,                // 行をグループ化しない
                null,                // 行をグループでフィルタリングしない
                null                 // ソートの順序を指定しない
        );
        while (cursor.moveToNext()){
            value = cursor.getString(cursor.getColumnIndexOrThrow("setting_value"));
        }
        // カーソルとデータベースを閉じます
        cursor.close();
        db.close();

        return value;
    }
}