package com.example.foodapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductStateAdapter extends RecyclerView.Adapter<ProductStateAdapter.ViewHolder> {

    private final List<ProductState> stateList;
    private final LayoutInflater layoutInflater;

    public ProductStateAdapter(List<ProductState> stateList, LayoutInflater layoutInflater) {
        this.stateList = stateList;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public ProductStateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.product_block, parent, false);
        return new ProductStateAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductStateAdapter.ViewHolder holder, int position) {
        ProductState curState = stateList.get(position);
        holder.name.setText(curState.getName());
        holder.calories.setText("Калории: " + curState.getCalories());
        holder.proteins.setText("Белки: " + curState.getProteins());
        holder.fats.setText("Жиры: " + curState.getFats());
        holder.carbohydrates.setText("Углеводы: " + curState.getCarbohydrates());
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name, calories, proteins, fats, carbohydrates;

        ViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.textProductName);
            this.calories = view.findViewById(R.id.textProductCalories);
            this.proteins = view.findViewById(R.id.textProductProteins);
            this.fats = view.findViewById(R.id.textProductFats);
            this.carbohydrates = view.findViewById(R.id.textProductCarbohydrates);
        }
    }
}
