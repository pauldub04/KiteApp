package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodapp.db.DbManager;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    boolean isAuth;

    private DbManager dbManager;


    private void checkAuth() {
        isAuth = prefs.getBoolean("isAuth", false);

        if (!isAuth) {
            Intent toAuth = new Intent(MainActivity.this, Authorization.class);
            startActivity(toAuth);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setValues() {
        TextView weight = (TextView) findViewById(R.id.textViewWeight);
        TextView height = (TextView) findViewById(R.id.textViewHeight);
        TextView age = (TextView) findViewById(R.id.textViewAge);
        TextView sex = (TextView) findViewById(R.id.textViewSex);
        TextView cal = (TextView) findViewById(R.id.textViewCal);

        weight.setText("Вес: " + String.valueOf(prefs.getInt("weight", 0)));
        height.setText("Рост: " + String.valueOf(prefs.getInt("height", 0)));
        age.setText("Возраст: " + String.valueOf(prefs.getInt("age", 0)));
        sex.setText("Пол: " + String.valueOf(prefs.getString("sex", "")));
        cal.setText("Калории: " + String.valueOf(prefs.getInt("cal", 0)));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent test = new Intent(MainActivity.this, DbTest.class);
//        startActivity(test);

        prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        dbManager = new DbManager(this);

        checkAuth();
        setContentView(R.layout.activity_main);

        setValues();

        // logout
        Button btnLogOut = findViewById(R.id.buttonLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
                editor.putBoolean("isAuth", false);
                editor.apply();

                dbManager.clearDatabase();

                Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                startActivity(refresh);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();

        TextView t = findViewById(R.id.textViewMain);
        t.setText("");
        for (String str : dbManager.getFromDb()) {
            t.append(str);
            t.append("\n");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }

    public void onClickAdd(View view) {
        Intent toAdd = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(toAdd);
    }
}



