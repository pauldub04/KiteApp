package com.example.foodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodapp.db.DbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FoodFragment extends Fragment {

    boolean isAuth;

    private SharedPreferences prefs;
    private DbManager dbManager;
    private View rootView;

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
        prefs = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        dbManager = new DbManager(getActivity());

        checkAuth();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_food, container, false);

        // add food
        FloatingActionButton btnAdd = rootView.findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(v -> {
            Intent toAdd = new Intent(getActivity(), AddFoodActivity.class);
            startActivity(toAdd);
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbManager.openDb();

        TextView t = rootView.findViewById(R.id.textViewMain);
        t.setText("");
        for (String str : dbManager.getFromDb()) {
            t.append(str);
            t.append("\n");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }

}