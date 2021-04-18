package com.example.foodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapp.db.DbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class FoodFragment extends Fragment {

    boolean isAuth;

    private SharedPreferences prefs;
    private DbManager dbManager;
    private View rootView;

    ArrayList<ProductState> states = new ArrayList<>();
    RecyclerView recyclerView;

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

    public void updateFood() {
        states.clear();
        dbManager.getFood(states);
        updateAdapter();
    }

    public void updateAdapter() {
        recyclerView.setAdapter(new FoodStateAdapter(getContext(), states, getLayoutInflater(), recyclerView));
    }

    @Override
    public void onResume() {
        super.onResume();
        dbManager.openDb();

        recyclerView = rootView.findViewById(R.id.recycleFood);
        updateFood();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }

}