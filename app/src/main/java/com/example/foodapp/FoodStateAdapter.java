package com.example.foodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodStateAdapter extends RecyclerView.Adapter<FoodStateAdapter.ViewHolder> {

    private final List<ProductState> stateList;
    private final LayoutInflater layoutInflater;
    private final Context context;

    public FoodStateAdapter(Context context, List<ProductState> stateList, LayoutInflater layoutInflater) {
        this.context = context;
        this.stateList = stateList;
        this.layoutInflater = layoutInflater;
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
            Log.d("KEK", "change");
//            TextInputEditText e = new TextInputEditText(context);
//            e.setInputType(InputType.TYPE_CLASS_NUMBER);
//            e.setText("100");
//
//            new MaterialAlertDialogBuilder(context)
//                    .setTitle("Добавление продукта")
//                    .setMessage("Введите вес в граммах: ")
//                    .setView(e)
//                    .setNeutralButton("Отмена", null)
//                    .setPositiveButton("Добавить", (dialogInterface, i) -> {
//                        DbManager dbManager = new DbManager(context);
//                        dbManager.openDb();
//
//                        float g = Float.parseFloat(String.valueOf(e.getText()));
//
//                        String name = holder.name.getText().toString();
//                        float cal = curState.getCalories() / 100.0f * g;
//                        float pr = curState.getProteins() / 100.0f * g;
//                        float ft = curState.getFats() / 100.0f * g;
//                        float ch = curState.getCarbohydrates() / 100.0f * g;
//
//                        dbManager.insertProduct(name, cal, pr, ft, ch);
//
//                        dbManager.closeDb();
//
//                        Intent toMain = new Intent(context, MainActivity.class);
//                        context.startActivity(toMain);
//                    })
//                    .show();
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
