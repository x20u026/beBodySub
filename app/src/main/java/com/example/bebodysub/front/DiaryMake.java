package com.example.bebodysub.front;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bebodysub.R;
import com.example.bebodysub.back.DiaryDBHelper;

public class DiaryMake extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView selectedImageView;
    private  EditText heightEditText,weightEditText,inKrlEditText,outKrlEditText,commentEditText;
    private SQLiteDatabase db;
    private Uri selectedImageUri;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_make);

        //DiaryDBHelperからDB取得
        DiaryDBHelper dbHelper = new DiaryDBHelper(this);
        db = dbHelper.getWritableDatabase();

        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);
        inKrlEditText = findViewById(R.id.inKrlEditText);
        outKrlEditText = findViewById(R.id.outKrlEditText);
        commentEditText = findViewById(R.id.commentEditText);

        selectedImageView = findViewById(R.id.myImageView);
        Button addButton = findViewById(R.id.addDBButton);

        //保存ボタン→画面遷移
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String heightStr = heightEditText.getText().toString();
                String weightStr = weightEditText.getText().toString();
                String inKrlStr = inKrlEditText.getText().toString();
                String outKrlStr = outKrlEditText.getText().toString();
                String commentStr = commentEditText.getText().toString();

                //  必須項目のnullチェック
                if(heightStr.isEmpty() || weightStr.isEmpty() || inKrlStr.isEmpty() || outKrlStr.isEmpty()){
                    Toast.makeText(DiaryMake.this, "必須項目が未入力です。", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Intentから日付データを受け取ってDB保存
                Intent reciveIntent = getIntent();
                if(reciveIntent != null && reciveIntent.hasExtra("SEND_DATE")){
                    String receiveDate = reciveIntent.getStringExtra("SEND_DATE");
                    Log.d("DBに入れる日付", receiveDate);
                    try {
                        double height = Double.parseDouble(heightStr);
                        double weight = Double.parseDouble(weightStr);
                        double inKrl = Double.parseDouble(inKrlStr);
                        double outKrl = Double.parseDouble(outKrlStr);

                        // ここでデータベースへの挿入処理を行う
                        addDB(receiveDate,height,weight,inKrl,outKrl,commentStr);
                    } catch (NumberFormatException e) {
                        // 有効な double 値に変換できなかった場合の処理
                        Log.e("NumberFormatException", "Invalid double value");
                        e.printStackTrace();
                    }

                }
                // 画面遷移のためのインテントを作成
                Intent intent = new Intent(DiaryMake.this, Diary.class);
                startActivity(intent);
            }
        });
    }

    //画像取るコード1
    public void onSelectImageClick(View view) {
        // ギャラリーアプリを開くIntentを作成
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        // ギャラリーアプリを開く
        startActivityForResult(Intent.createChooser(intent, "画像を選択"), PICK_IMAGE_REQUEST);
    }

    //画像取るコード2
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            selectedImageView.setImageURI(selectedImageUri);
        }
    }

    private void addDB(String date, double height, double weight, double inKrl, double outKrl, String comment){
        ContentValues values = new ContentValues();
        values.put("day", date);
        values.put("height", height);
        values.put("weight", weight);
        values.put("in_Krl", inKrl);
        values.put("out_Krl", outKrl);  // ここで "outKrl" カラムを指定
        values.put("comment", comment);

        // 選択された画像のURIを文字列に変換して保存
        if (selectedImageUri != null) {
            values.put("image", selectedImageUri.toString());
        }

        db.insert("diarydb", null, values);
    }

}
