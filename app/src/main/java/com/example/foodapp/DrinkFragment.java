package com.example.foodapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodapp.db.DbManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DrinkFragment extends Fragment {

    private DbManager dbManager;
    private View rootView;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dayFormat = new SimpleDateFormat("dd MMMM yyyy");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    Date realDate = null, chosenDate = null;
    public static String realDateString, chosenDateString;
    Calendar calendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DbManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_drink, container, false);

        Button btnMoveLeft = rootView.findViewById(R.id.buttonMoveDayLeft);
        btnMoveLeft.setOnClickListener(v -> {
            moveDay(-1);
        });

        Button btnMoveRight = rootView.findViewById(R.id.buttonMoveDayRight);
        btnMoveRight.setOnClickListener(v -> {
            moveDay(1);
        });


        Button btnMinus = rootView.findViewById(R.id.buttonMinus);
        btnMinus.setOnClickListener(v -> {
            changeDrink(-1);
        });

        Button btnPlus = rootView.findViewById(R.id.buttonPlus);
        btnPlus.setOnClickListener(v -> {
            changeDrink(1);
        });

        return rootView;
    }

    public void updateDrink() {
        dbManager.getDrink(rootView.findViewById(R.id.textViewDrink) ,chosenDateString);
    }

    public void changeDrink(int delta) {
        dbManager.changeDrink(delta, chosenDateString);
        updateDrink();
    }

    public void updateDate() {
        realDateString = dayFormat.format(realDate);
        chosenDateString = dayFormat.format(chosenDate);

        TextView t = rootView.findViewById(R.id.textChosenDay);
        t.setText(chosenDateString.substring(0, chosenDateString.length()-5));

        updateDrink();
//        updateFood();
    }

    public void moveDay(int delta) {
        calendar.setTime(chosenDate);
        calendar.add(Calendar.DATE, delta);
        chosenDate = calendar.getTime();
        updateDate();
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onResume() {
        super.onResume();
        dbManager.openDb();

        calendar = Calendar.getInstance();
        if (realDate == null)
            realDate = new Date();
        if (chosenDate == null)
            chosenDate = new Date();

        updateDate();
        dbManager.checkDay(chosenDateString);

        updateDrink();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }
}
