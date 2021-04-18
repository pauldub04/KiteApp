package com.example.foodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.db.DbManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class FoodStateAdapter extends RecyclerView.Adapter<FoodStateAdapter.ViewHolder> {

    private final List<ProductState> stateList;
    private final LayoutInflater layoutInflater;
    private final Context context;
    private final RecyclerView recyclerView;

    public FoodStateAdapter(Context context, List<ProductState> stateList, LayoutInflater layoutInflater, RecyclerView recyclerView) {
        this.context = context;
        this.stateList = stateList;
        this.layoutInflater = layoutInflater;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public FoodStateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.product_main_block, parent, false);
        return new FoodStateAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodStateAdapter.ViewHolder holder, int position) {
        ProductState curState = stateList.get(position);
        holder.name.setText(curState.getName());
//        holder.time.setText("Белки: " + curState.getProteins());
        holder.calories.setText(Math.round(curState.getCalories()) + "ккал");
        holder.grams.setText(Math.round(curState.getGrams()) + "г");

        holder.settings.setOnClickListener(v -> {


            TextInputEditText e = new TextInputEditText(context);
            e.setInputType(InputType.TYPE_CLASS_NUMBER);
            e.setText(Math.round(curState.getGrams()) + "");

            int id = curState.getId();

            new MaterialAlertDialogBuilder(context)
                    .setTitle(holder.name.getText().toString())
                    .setMessage("Масса в граммах: ")
                    .setView(e)
                    .setNeutralButton("Отмена", null)
                    .setNegativeButton("Удалить", (dialogInterface, i) -> {
                        DbManager dbManager = new DbManager(context);
                        dbManager.openDb();
                        dbManager.deleteProduct(id);

                        stateList.clear();
                        dbManager.getFood((ArrayList<ProductState>) stateList);
                        recyclerView.setAdapter(new FoodStateAdapter(context, stateList, layoutInflater, recyclerView));

                        dbManager.closeDb();
                    })
                    .setPositiveButton("Изменить", (dialogInterface, i) -> {

                        float olg_g = curState.getGrams();
                        float new_g = Float.parseFloat(String.valueOf(e.getText()));

                        String name = holder.name.getText().toString();
                        float cal = curState.getCalories() / olg_g * new_g;
                        float pr = curState.getProteins() / olg_g * new_g;
                        float ft = curState.getFats() / olg_g * new_g;
                        float ch = curState.getCarbohydrates() / olg_g * new_g;

                        DbManager dbManager = new DbManager(context);
                        dbManager.openDb();
                        dbManager.updateProduct(id, name, cal, pr, ft, ch, new_g);

                        stateList.clear();
                        dbManager.getFood((ArrayList<ProductState>) stateList);
                        recyclerView.setAdapter(new FoodStateAdapter(context, stateList, layoutInflater, recyclerView));

                        dbManager.closeDb();
                    })
                    .show();

        });
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name, calories, time, grams;
        final Button settings;

        ViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.textFoodName);
            this.calories = view.findViewById(R.id.textFoodCalories);
            this.time = view.findViewById(R.id.textFoodTime);
            this.grams = view.findViewById(R.id.textFoodGrams);

            this.settings = view.findViewById(R.id.buttonSettings);
        }
    }

}
