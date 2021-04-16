package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.foodapp.db.DbStaticManager;
import com.google.android.material.textfield.TextInputEditText;

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
        updateProducts("");

        TextInputEditText product = findViewById(R.id.editTextProduct);
        product.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateProducts(String.valueOf(s));
            }
        });

    }

    public void updateProducts(String s) {
        states.clear();
        dbManager.getProducts(states, s);
        updateAdapter();
    }

    public void updateAdapter() {
        recyclerView.setAdapter(new ProductStateAdapter(this, states, getLayoutInflater()));
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