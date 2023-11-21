package com.example.bebodysub.front;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.bebodysub.R;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Syokaitouroku extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syokaitouroku);

        //yearの定義
        ArrayAdapter<String> year = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        year.setDropDownViewResource(android.R.layout.simple_spinner_item);

        //年の取得
        LocalDateTime now = LocalDateTime.now();
        Log.d("now",String.valueOf(now));
        DateTimeFormatter datef1 = DateTimeFormatter.ofPattern("yyyy");
        int nowYear = Integer.parseInt(datef1.format(now));

        for (int i= nowYear; i > nowYear-100; i--){
            year.add(String.valueOf(i));
        }
        Spinner year_spn = (Spinner) findViewById(R.id.year_spn);
        year_spn.setAdapter(year);

        //monthの定義
        ArrayAdapter<String> month = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        year.setDropDownViewResource(android.R.layout.simple_spinner_item);

        //月の取得
        for (int i = 1; i<=12; i++){
            month.add(String.valueOf(i));
        }
        Spinner month_spn = (Spinner) findViewById(R.id.month_spn);
        month_spn.setAdapter(month);

        //日の取得
        ArrayAdapter<String> day = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        year.setDropDownViewResource(android.R.layout.simple_spinner_item);
        for (int i = 1; i<=31; i++){
            day.add(String.valueOf(i));
        }
        Spinner day_spn = (Spinner) findViewById(R.id.day_spn);
        day_spn.setAdapter(day);

        //性別の定義
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapter.add("男");
        adapter.add("女");
        Spinner sex_spn = (Spinner) findViewById(R.id.sex_spn);
        sex_spn.setAdapter(adapter);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリック時のアクション
                Intent intent = new Intent(Syokaitouroku.this, Syokaikakunin.class);
                //EditTextを取得する
                EditText extname = findViewById(R.id.name_ext);
                String name = extname.getText().toString();

                DecimalFormat decimalFormat = new DecimalFormat("00");
                String year_txt = (String) year_spn.getSelectedItem();
                String month_txt = decimalFormat.format(Integer.parseInt((String) month_spn.getSelectedItem()));
                String day_txt = decimalFormat.format(Integer.parseInt((String) day_spn.getSelectedItem()));
                String birthday = year_txt+month_txt+day_txt;

                EditText extheight = findViewById(R.id.height_ext);
                String height = extheight.getText().toString();

                EditText extweight = findViewById(R.id.weight_ext);
                String weight = extweight.getText().toString();

                String sex = (String) sex_spn.getSelectedItem();

                //取得したデータをintentにセットする
                intent.putExtra("name",name);
                intent.putExtra("birthday",birthday);
                intent.putExtra("height",height);
                intent.putExtra("weight",weight);
                intent.putExtra("sex",sex);

                startActivity(intent);

            }
        });



    }
}