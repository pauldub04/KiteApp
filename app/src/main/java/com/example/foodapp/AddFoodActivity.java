package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;

import com.example.foodapp.db.DbManager;
import com.example.foodapp.db.DbStaticManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddFoodActivity extends AppCompatActivity {

    private DbStaticManager dbManager;

    ArrayList<ProductState> states = new ArrayList<>();
    RecyclerView recyclerView;

    ProductStateAdapter.OnStateClickListener onStateClickListener;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        onStateClickListener = (state, pos, holder) -> {
            TextInputEditText e = new TextInputEditText(this);
            e.setInputType(InputType.TYPE_CLASS_NUMBER);
            e.setText("100");

            new MaterialAlertDialogBuilder(this)
                    .setTitle("Добавление продукта")
                    .setMessage("Введите массу в граммах: ")
                    .setView(e)
                    .setNeutralButton("Отмена", null)
                    .setPositiveButton("Добавить", (dialogInterface, i) -> {
                        DbManager dbManager2 = new DbManager(this);
                        dbManager2.openDb();

                        float g = Float.parseFloat(String.valueOf(e.getText()));

                        String name = holder.name.getText().toString();
                        float cal = state.getCalories() / 100.0f * g;
                        float pr = state.getProteins() / 100.0f * g;
                        float ft = state.getFats() / 100.0f * g;
                        float ch = state.getCarbohydrates() / 100.0f * g;

                        dbManager2.insertProduct(name, cal, pr, ft, ch, g, FoodFragment.chosenDateString);
                        dbManager2.closeDb();

                        Intent toMain = new Intent(this, MainActivity.class);
                        startActivity(toMain);
                    })
                    .show();
        };

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
        recyclerView.setAdapter(new ProductStateAdapter(this, states, getLayoutInflater(), onStateClickListener));
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