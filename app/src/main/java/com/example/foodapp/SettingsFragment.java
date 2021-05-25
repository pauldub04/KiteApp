package com.example.foodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodapp.db.DbManager;


public class SettingsFragment extends Fragment {

    private SharedPreferences prefs;
    private DbManager dbManager;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        dbManager = new DbManager(getActivity());
    }

    @SuppressLint("SetTextI18n")
    private void setValues() {
        TextView name = rootView.findViewById(R.id.textViewName);
        TextView weight = rootView.findViewById(R.id.textViewWeight);
        TextView height = rootView.findViewById(R.id.textViewHeight);
        TextView age = rootView.findViewById(R.id.textViewAge);
        TextView sex = rootView.findViewById(R.id.textViewSex);
        TextView cal = rootView.findViewById(R.id.textViewCal);

        name.setText(prefs.getString("name", ""));
        weight.setText(String.valueOf(prefs.getInt("weight", 0)) + " кг");
        height.setText(String.valueOf(prefs.getInt("height", 0)) + " см");
        age.setText("Возраст: " + String.valueOf(prefs.getInt("age", 0)));
        String sexText = "мужской";
        if (prefs.getString("sex", "").equals("female"))
            sexText = "женский";
        sex.setText("Пол " + sexText);
        cal.setText("Норма калорий: " + String.valueOf(prefs.getInt("cal", 0)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        setValues();

        // logout
        Button btnLogOut = rootView.findViewById(R.id.buttonLogOut);
        btnLogOut.setOnClickListener(v -> {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit();
            editor.putBoolean("isAuth", false);
            editor.apply();

            dbManager.clearDatabase();

            Intent refresh = new Intent(getActivity(), MainActivity.class);
            startActivity(refresh);
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbManager.openDb();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }
}
