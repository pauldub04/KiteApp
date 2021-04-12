package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodapp.db.DbManager;

public class MainActivity2 extends AppCompatActivity {

    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dbManager = new DbManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }

    public void addFood(View view) {
        EditText name = findViewById(R.id.editTextName);
        EditText cal = findViewById(R.id.editTextCalories);
        EditText pr = findViewById(R.id.editTextProteins);
        EditText ft = findViewById(R.id.editTextFats);
        EditText ch = findViewById(R.id.editTextCarbohydrates);

        dbManager.insert(name.getText().toString(), Integer.parseInt(String.valueOf(cal.getText())),
                Integer.parseInt(String.valueOf(pr.getText())), Integer.parseInt(String.valueOf(ft.getText())),
                Integer.parseInt(String.valueOf(ch.getText())));

        Intent toMain = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(toMain);

    }
}