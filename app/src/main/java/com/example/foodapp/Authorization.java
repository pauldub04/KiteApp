package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Authorization extends AppCompatActivity {

//    EditText weight;
//    EditText height;
//    EditText age;
//    RadioButton sex1;
//    RadioButton sex2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        EditText weight = findViewById(R.id.editTextWeight);
        EditText height = findViewById(R.id.editTextHeight);
        EditText age = findViewById(R.id.editTextAge);
        RadioButton sex1 = findViewById(R.id.radioButtonSex1);
        RadioButton sex2 = findViewById(R.id.radioButtonSex2);

        RadioButton sport1 = findViewById(R.id.radioButtonSport1);
        RadioButton sport2 = findViewById(R.id.radioButtonSport2);
        RadioButton sport3 = findViewById(R.id.radioButtonSport3);
        RadioButton sport4 = findViewById(R.id.radioButtonSport4);
        RadioButton sport5 = findViewById(R.id.radioButtonSport5);

        Button btn = findViewById(R.id.buttonAuth);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myLog", String.valueOf(weight.getText()));
                Log.d("myLog", String.valueOf(height.getText()));
                Log.d("myLog", String.valueOf(age.getText()));
                Log.d("myLog", String.valueOf(sex1.isChecked()));
                Log.d("myLog", String.valueOf(sex2.isChecked()));

                SharedPreferences.Editor editor = getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
                int w = Integer.parseInt(weight.getText().toString());
                int h = Integer.parseInt(height.getText().toString());
                int a = Integer.parseInt(age.getText().toString());
                editor.putInt("weight", w);
                editor.putInt("height", h);
                editor.putInt("age", a);

                String sex;
                if (sex1.isChecked())
                    sex = "male";
                else
                    sex = "female";

                editor.putString("sex", sex);

                double sport = 1.2;
                if (sport1.isChecked())
                    sport = 1.2;
                else if (sport2.isChecked())
                    sport = 1.375;
                else if (sport3.isChecked())
                    sport = 1.55;
                else if (sport4.isChecked())
                    sport = 1.725;
                else if (sport5.isChecked())
                    sport = 1.9;


                editor.putFloat("sport", (float) sport);

                float cal;
                if (sex1.isChecked()) {
                    cal = (float) ((float) (10 * w + 6.25 * h - 5 * a + 5) * sport);
                } else {
                    cal = (float) ((float) (10 * w + 6.25 * h - 5 * a - 161) * sport);
                }

                editor.putInt("cal", ((int) cal));
                editor.putBoolean("isAuth", true);
                editor.apply();


                Intent toMain = new Intent(Authorization.this, MainActivity.class);
                startActivity(toMain);
            }
        });

    }
}