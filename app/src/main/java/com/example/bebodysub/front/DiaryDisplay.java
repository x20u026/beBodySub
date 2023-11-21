package com.example.bebodysub.front;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bebodysub.R;
import com.example.bebodysub.back.DiaryDBHelper;

public class DiaryDisplay extends AppCompatActivity {

    private SQLiteDatabase db;
    private DiaryDBHelper dbHelper;
    private TextView heightTextView, weightTextView, inKrlTextView, outKrlTextView, commentTextView;
    private ImageView myImageView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_display);

        // dbHelperのインスタンスを生成
        dbHelper = new DiaryDBHelper(this);

        // UI要素の取得
        myImageView = findViewById(R.id.myImageView);
        heightTextView = findViewById(R.id.heightTextView);
        weightTextView = findViewById(R.id.weightTextView);
        inKrlTextView = findViewById(R.id.inKrlTextView);
        outKrlTextView = findViewById(R.id.outKrlTextView);
        commentTextView = findViewById(R.id.commentTextView);

        // データベースからデータを読み込んで表示
        readDB();

        // 戻るボタンのクリックリスナーを設定
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Diary.javaに戻る処理
                Intent intent = new Intent(DiaryDisplay.this, Diary.class);
                startActivity(intent);
                finish(); // 現在のActivityを終了
            }
        });
    }

    // データ表示
    public void readDB() {
        // 読み込み専用のデータベースを取得
        db = dbHelper.getReadableDatabase();

        // Intentから日付を取得
        String selectedDate = getIntent().getStringExtra("SEND_DATE");

        String imagePath = dbHelper.getImagePath(selectedDate);

        // 画像をセット
        if (imagePath != null && !imagePath.isEmpty()) {
            Uri imageUri = Uri.parse(imagePath);
            myImageView.setImageURI(imageUri);
        }

        // データベースからデータを取得
        Cursor cursor = db.rawQuery("SELECT * FROM " + DiaryDBHelper.TABLE_NAME + " WHERE " + DiaryDBHelper.DAY + "=?", new String[]{selectedDate});

        // カーソルがデータを指しているか確認
        if (cursor.moveToFirst()) {

            // インデックスを指定してデータを取得
            int heightIndex = cursor.getColumnIndex(DiaryDBHelper.COLUMN_HEIGHT);
            int weightIndex = cursor.getColumnIndex(DiaryDBHelper.COLUMN_WEIGHT);
            int inKrlIndex = cursor.getColumnIndex(DiaryDBHelper.COLUMN_INKRL);
            int outKrlIndex = cursor.getColumnIndex(DiaryDBHelper.COLUMN_OUTKRL);
            int commentIndex = cursor.getColumnIndex(DiaryDBHelper.COLUMN_COMMENT);

            // インデックスが有効かつ0以上であることを確認
            if (heightIndex >= 0) {
                String heightData = cursor.getString(heightIndex);
                heightTextView.setText(heightData);
            }

            if (weightIndex >= 0) {
                String weightData = cursor.getString(weightIndex);
                weightTextView.setText(weightData);
            }

            if (inKrlIndex >= 0) {
                String inKrlData = cursor.getString(inKrlIndex);
                inKrlTextView.setText(inKrlData);
            }

            if (outKrlIndex >= 0) {
                String outKrlData = cursor.getString(outKrlIndex);
                outKrlTextView.setText(outKrlData);
            }

            if (commentIndex >= 0) {
                String commentData = cursor.getString(commentIndex);
                commentTextView.setText(commentData);
            }
        }

        // カーソルを閉じる
        cursor.close();
    }
}
