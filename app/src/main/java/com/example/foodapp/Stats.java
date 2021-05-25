package com.example.foodapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.db.DbConstants;
import com.example.foodapp.db.DbManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Stats extends AppCompatActivity {

    private DbManager dbManager;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dayFormat = new SimpleDateFormat("dd MMMM yyyy");
    Date realDate = null;
    public static String realDateString;
    Calendar calendar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        dbManager = new DbManager(this);
        dbManager.openDb();

        TextView week = findViewById(R.id.textViewWeek);
        TextView month = findViewById(R.id.textViewMonth);

        week.setText("week");
        month.setText("month");

        calendar = Calendar.getInstance();
        if (realDate == null)
            realDate = new Date();
        realDateString = dayFormat.format(realDate);

        stats(week, 7);
        stats(month, 30);
    }

    public void stats(TextView t, int days) {
        float sum_c = 0, sum_p = 0, sum_f = 0, sum_ch = 0;
        int cnt = 0;
        for (int i = 0; i < days; i++) {
            ArrayList<Float> x = dbManager.updateProgress(realDateString);

            float c = x.get(0), p = x.get(1), f = x.get(2), ch = x.get(3);
            if (c + p + f + ch != 0.0) {
                Log.d("KEK", realDateString + " " + c + " " + p + " " + f + " " + ch);
                sum_c += c;
                sum_p += p;
                sum_f += f;
                sum_ch += ch;
                cnt++;
            }
            realDate = moveDay(realDate, -1);
            realDateString = dayFormat.format(realDate);
        }

        String text = "Калории: " + (int) sum_c + "\n" +
                "Белки: " + (int) sum_p + "г\n" +
                "Жиры: " + (int) sum_f + "г\n" +
                "Углеводы: " + (int) sum_ch + "г\n";

        if (cnt != 0) {
            int avg_c = (int) (sum_c / cnt);
            int avg_p = (int) (sum_p / cnt);
            int avg_f = (int) (sum_f / cnt);
            int avg_ch = (int) (sum_ch / cnt);

            text += "\nСреднее\n" + "Калории: " + (int) avg_c + "\n" +
                    "Белки: " + (int) avg_p + "г\n" +
                    "Жиры: " + (int) avg_f + "г\n" +
                    "Углеводы: " + (int) avg_ch + "г";
        }

        t.setText(text);
        realDate = new Date();
        realDateString = dayFormat.format(realDate);
    }

    @Override
    public void onResume() {
        super.onResume();
        dbManager.openDb();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }

    public Date moveDay(Date date, int delta) {
        calendar.setTime(date);
        calendar.add(Calendar.DATE, delta);
        date = calendar.getTime();
        return date;
    }

}