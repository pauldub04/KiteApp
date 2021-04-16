package com.example.foodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

    public ProductStateAdapter(Context context, List<ProductState> stateList, LayoutInflater layoutInflater) {
        this.context = context;
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

        holder.add.setOnClickListener(v -> {

            TextInputEditText e = new TextInputEditText(context);
            e.setInputType(InputType.TYPE_CLASS_NUMBER);
            e.setText("100");

            new MaterialAlertDialogBuilder(context)
                    .setTitle("Добавление продукта")
                    .setMessage("Введите вес в граммах: ")
                    .setView(e)
                    .setNeutralButton("Отмена", null)
                    .setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            DbManager dbManager = new DbManager(context);
                            dbManager.openDb();

                            float g = Float.parseFloat(String.valueOf(e.getText()));

                            String name = holder.name.getText().toString();
                            float cal = curState.getCalories() / 100.0f * g;
                            float pr = curState.getProteins() / 100.0f * g;
                            float ft = curState.getFats() / 100.0f * g;
                            float ch = curState.getCarbohydrates() / 100.0f * g;

                            dbManager.insertProduct(name, cal, pr, ft, ch);

                            dbManager.closeDb();

                            Intent toMain = new Intent(context, MainActivity.class);
                            context.startActivity(toMain);
                        }
                    })
                .show();
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
