package com.example.foodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputType;
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

    interface OnStateClickListener {
        void onClickSettings(ProductState state, int pos, FoodStateAdapter.ViewHolder holder);
    }
    private final OnStateClickListener onStateClickListener;

    public FoodStateAdapter(Context context, List<ProductState> stateList,
                            LayoutInflater layoutInflater, RecyclerView recyclerView, OnStateClickListener onStateClickListener) {
        this.context = context;
        this.stateList = stateList;
        this.layoutInflater = layoutInflater;
        this.recyclerView = recyclerView;
        this.onStateClickListener = onStateClickListener;
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
        holder.calories.setText(Math.round(curState.getCalories()) + " ккал");
        holder.grams.setText(Math.round(curState.getGrams()) + "г");

        holder.settings.setOnClickListener(v -> {
            onStateClickListener.onClickSettings(curState, position, holder);
        });
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name, calories, grams;
        final Button settings;

        ViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.textFoodName);
            this.calories = view.findViewById(R.id.textFoodCalories);
//            this.time = view.findViewById(R.id.textFoodTime);
            this.grams = view.findViewById(R.id.textFoodGrams);

            this.settings = view.findViewById(R.id.buttonSettings);
        }
    }

}
