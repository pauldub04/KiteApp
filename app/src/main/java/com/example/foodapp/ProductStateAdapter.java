package com.example.foodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import java.util.List;

public class ProductStateAdapter extends RecyclerView.Adapter<ProductStateAdapter.ViewHolder> {

    private final List<ProductState> stateList;
    private final LayoutInflater layoutInflater;
    private final Context context;

    interface OnStateClickListener {
        void onClickAdd(ProductState state, int pos, ProductStateAdapter.ViewHolder holder);
    }
    private final OnStateClickListener onStateClickListener;

    public ProductStateAdapter(Context context, List<ProductState> stateList,
                               LayoutInflater layoutInflater, OnStateClickListener onStateClickListener) {
        this.context = context;
        this.stateList = stateList;
        this.layoutInflater = layoutInflater;
        this.onStateClickListener = onStateClickListener;
    }

    @NonNull
    @Override
    public ProductStateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.product_add_block, parent, false);
        return new ProductStateAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductStateAdapter.ViewHolder holder, int position) {
        ProductState curState = stateList.get(position);
        holder.name.setText(curState.getName());
        holder.calories.setText(Math.round(curState.getCalories()) + "ккал");
        holder.proteins.setText("Белки: " + Math.round(curState.getProteins()) + "г");
        holder.fats.setText("Жиры: " + Math.round(curState.getFats()) + "г");
        holder.carbohydrates.setText("Углеводы: " + Math.round(curState.getCarbohydrates()) + "г");

        holder.add.setOnClickListener(v -> {
            onStateClickListener.onClickAdd(curState, position, holder);
        });
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name, calories, proteins, fats, carbohydrates;
        final Button add;

        ViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.textProductName);
            this.calories = view.findViewById(R.id.textProductCalories);
            this.proteins = view.findViewById(R.id.textProductProteins);
            this.fats = view.findViewById(R.id.textProductFats);
            this.carbohydrates = view.findViewById(R.id.textProductCarbohydrates);

            this.add = view.findViewById(R.id.buttonAdd);
        }
    }
}
