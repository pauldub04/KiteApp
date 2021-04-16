package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.foodapp.db.DbStaticManager;

import java.io.IOException;
import java.util.ArrayList;

public class AddFoodNew extends AppCompatActivity {

    private DbStaticManager dbManager;

    ArrayList<ProductState> states = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_new);

        dbManager = new DbStaticManager(this);
        try {
            dbManager.createDb();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbManager.openDb();


        recyclerView = findViewById(R.id.recycleProduct);
        dbManager.getProducts(states);
        updateAdapter();
    }

    public void updateAdapter() {
        recyclerView.setAdapter(new ProductStateAdapter(states, getLayoutInflater()));
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

}