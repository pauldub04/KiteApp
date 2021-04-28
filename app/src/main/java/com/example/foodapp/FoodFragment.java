package com.example.foodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodapp.db.DbManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class FoodFragment extends Fragment {

    boolean isAuth;

    private SharedPreferences prefs;
    private DbManager dbManager;
    private View rootView;

    ArrayList<ProductState> states = new ArrayList<>();
    RecyclerView recyclerView;

    FoodStateAdapter.OnStateClickListener onStateClickListener;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dayFormat = new SimpleDateFormat("dd MMMM yyyy");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    Date realDate = null, chosenDate = null;
    public static String realDateString, chosenDateString;
    Calendar calendar;

    private void checkAuth() {
        isAuth = prefs.getBoolean("isAuth", false);

        if (!isAuth) {
            Intent toAuth = new Intent(getActivity(), Authorization.class);
            startActivity(toAuth);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        dbManager = new DbManager(getActivity());

        checkAuth();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_food, container, false);

        onStateClickListener = (state, pos, holder) -> {
            TextInputEditText e = new TextInputEditText(Objects.requireNonNull(getContext()));
            e.setInputType(InputType.TYPE_CLASS_NUMBER);
            e.setText(Math.round(state.getGrams()) + "");

            int id = state.getId();

            new MaterialAlertDialogBuilder(getContext())
                    .setTitle(holder.name.getText().toString())
                    .setMessage("Масса в граммах: ")
                    .setView(e)
                    .setNeutralButton("Отмена", null)
                    .setNegativeButton("Удалить", (dialogInterface, i) -> {
                        dbManager.deleteProduct(id);
                        updateFood();
                    })
                    .setPositiveButton("Изменить", (dialogInterface, i) -> {
                        float olg_g = state.getGrams();
                        float new_g = Float.parseFloat(String.valueOf(e.getText()));

                        String name = holder.name.getText().toString();
                        float cal = state.getCalories() / olg_g * new_g;
                        float pr = state.getProteins() / olg_g * new_g;
                        float ft = state.getFats() / olg_g * new_g;
                        float ch = state.getCarbohydrates() / olg_g * new_g;

                        dbManager.updateProduct(id, name, cal, pr, ft, ch, new_g);
                        updateFood();
                    })
                    .show();
        };

        // add food
        FloatingActionButton btnAdd = rootView.findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(v -> {
            Intent toAdd = new Intent(getActivity(), AddFoodActivity.class);
            startActivity(toAdd);
        });

        Button btnMoveLeft = rootView.findViewById(R.id.buttonMoveDayLeft);
        btnMoveLeft.setOnClickListener(v -> {
            moveDay(-1);
        });

        Button btnMoveRight = rootView.findViewById(R.id.buttonMoveDayRight);
        btnMoveRight.setOnClickListener(v -> {
            moveDay(1);
        });

        return rootView;
    }

    public void updateFood() {
        states.clear();
        dbManager.getFood(states, chosenDateString);
        updateAdapter();
    }

    public void updateAdapter() {
        recyclerView.setAdapter(new FoodStateAdapter(getContext(), states, getLayoutInflater(), recyclerView, onStateClickListener));
    }

    public void updateDate() {
        realDateString = dayFormat.format(realDate);
        chosenDateString = dayFormat.format(chosenDate);

        TextView t = rootView.findViewById(R.id.textChosenDay);
        t.setText(chosenDateString.substring(0, chosenDateString.length()-5));

        updateFood();
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

        recyclerView = rootView.findViewById(R.id.recycleFood);

        calendar = Calendar.getInstance();
        if (realDate == null)
            realDate = new Date();
        if (chosenDate == null)
            chosenDate = new Date();

        updateDate();
        dbManager.checkDay(chosenDateString);
//        updateFood();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }

}