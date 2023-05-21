package com.example.foodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DetectedProductStateAdapter extends RecyclerView.Adapter<DetectedProductStateAdapter.ViewHolder> {

    private final List<ProductState> stateList;
    private final LayoutInflater layoutInflater;
    private final Context context;

    public DetectedProductStateAdapter(Context context, List<ProductState> stateList,
                               LayoutInflater layoutInflater) {
        this.context = context;
        this.stateList = stateList;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public DetectedProductStateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.product_detected_block, parent, false);
        return new DetectedProductStateAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DetectedProductStateAdapter.ViewHolder holder, int position) {
        ProductState curState = stateList.get(position);
        holder.name.setText(curState.getName());
        holder.grams.setText("100");
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final EditText grams;

        ViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.textDetectedProductName);
            this.grams = view.findViewById(R.id.textDetectedGrams);
        }
    }
}
