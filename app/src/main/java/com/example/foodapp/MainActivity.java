package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView nav;

    Fragment f_food;
    Fragment f_drink;
    Fragment f_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        f_food = new FoodFragment();
        f_drink = new DrinkFragment();
        f_settings = new SettingsFragment();

        nav = findViewById(R.id.bottom_navigation);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fm = getSupportFragmentManager();
                switch (item.getItemId()) {
                    case R.id.page_food:
                        fm.beginTransaction().replace(R.id.frame_layout, f_food).commit();
                        return true;
                    case R.id.page_drink:
                        fm.beginTransaction().replace(R.id.frame_layout, f_drink).commit();
                        return true;
                    case R.id.page_settings:
                        fm.beginTransaction().replace(R.id.frame_layout, f_settings).commit();
                        return true;
                }
                return false;
            }
        });
        nav.setSelectedItemId(R.id.page_food);

    }

}





