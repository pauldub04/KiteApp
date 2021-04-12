package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.foodapp.db.DbManager;

public class DbTest extends AppCompatActivity {

    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_test);

        dbManager = new DbManager(this);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        dbManager.openDb();
//
//        TextView t = findViewById(R.id.textView);
//        t.setText("");
//        for (String title : dbManager.getFromDb()) {
//            t.append(title);
//            t.append("\n");
//        }
//    }

//    public void onClickSave(View view) {
//        dbManager.insert("asd", "123");
//
//        TextView t = findViewById(R.id.textView);
//        t.setText("");
//        for (String title : dbManager.getFromDb()) {
//            t.append(title);
//            t.append("\n");
//        }
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        dbManager.closeDb();
//    }
}