package com.example.bebodysub.front;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bebodysub.R;
import com.example.bebodysub.back.DiaryDBHelper;

public class PlanMake extends AppCompatActivity {
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_make);

        DiaryDBHelper dbHelper = new DiaryDBHelper(this);
        db = dbHelper.getWritableDatabase();
    }

}
