package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

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

        // dropdown menu
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteText);
        String[] physOption = {"Нет физической нагрузки", "3 раза в неделю", "5 раз в неделю",
                "Каждый день", "Каждый день + физическая работа"};
        autoCompleteTextView.setAdapter(new ArrayAdapter<>(this, R.layout.list_item, physOption));

        TextInputLayout nameLayout = findViewById(R.id.textFieldName);
        TextInputLayout weightLayout = findViewById(R.id.textFieldWeight);
        TextInputLayout heightLayout = findViewById(R.id.textFieldHeight);
        TextInputLayout ageLayout = findViewById(R.id.textFieldAge);

        TextInputEditText nameInput = findViewById(R.id.editTextName);
        TextInputEditText weightInput = findViewById(R.id.editTextWeight);
        TextInputEditText heightInput = findViewById(R.id.editTextHeight);
        TextInputEditText ageInput = findViewById(R.id.editTextAge);

        RadioButton sex1Input = findViewById(R.id.radioButtonSex1);
        RadioButton sex2Input = findViewById(R.id.radioButtonSex2);


        Button btn = findViewById(R.id.buttonAuth);
        btn.setOnClickListener(v -> {
            SharedPreferences.Editor editor = getSharedPreferences("UserInfo", MODE_PRIVATE).edit();

            int weightValue = 0, heightValue = 0, ageValue = 0;
            String nameValue = "";
            boolean isValid = true;

            if (Objects.requireNonNull(nameInput.getText()).toString().isEmpty()) {
                nameLayout.setError("Введите своё имя");
                isValid = false;
            } else {
                nameValue = Objects.requireNonNull(nameInput.getText()).toString();
                nameLayout.setError(null);
                nameLayout.setErrorEnabled(false);
                isValid = false;
            }

            if (Objects.requireNonNull(weightInput.getText()).toString().isEmpty()) {
                weightLayout.setError("Введите свой вес");
                isValid = false;
            }
            else {
                weightValue = Integer.parseInt(Objects.requireNonNull(weightInput.getText()).toString());
                weightLayout.setError(null);
                weightLayout.setErrorEnabled(false);
                isValid = true;
            }

            if (Objects.requireNonNull(heightInput.getText()).toString().isEmpty()) {
                heightLayout.setError("Введите свой рост");
                isValid = false;
            }
            else {
                heightValue = Integer.parseInt(Objects.requireNonNull(heightInput.getText()).toString());
                heightLayout.setError(null);
                heightLayout.setErrorEnabled(false);
                isValid = true;
            }

            if (Objects.requireNonNull(ageInput.getText()).toString().isEmpty()) {
                ageLayout.setError("Введите свой возраст");
                isValid = false;
            }
            else {
                ageValue = Integer.parseInt(Objects.requireNonNull(ageInput.getText()).toString());
                ageLayout.setError(null);
                ageLayout.setErrorEnabled(false);
                isValid = true;
            }


            String sexValue;
            if (sex1Input.isChecked())
                sexValue = "male";
            else if (sex2Input.isChecked())
                sexValue = "female";
            else {
                isValid = false;
            }

            String s = autoCompleteTextView.getEditableText().toString();
            if (s.isEmpty()) {
                isValid = false;
            }

            if (isValid)
                Log.d("KEK", "login");

//            int weightValue = Integer.parseInt(Objects.requireNonNull(weightInput.getText()).toString());
//            Log.d("KEK", Objects.requireNonNull(weightInput.getText()).toString());

//            double sport = 1.2;
//            if (sport1.isChecked())
//                sport = 1.2;
//            else if (sport2.isChecked())
//                sport = 1.375;
//            else if (sport3.isChecked())
//                sport = 1.55;
//            else if (sport4.isChecked())
//                sport = 1.725;
//            else if (sport5.isChecked())
//                sport = 1.9;


//            editor.putFloat("sport", (float) sport);
//
//            float cal;
//            if (sex1.isChecked()) {
//                cal = (float) ((float) (10 * w + 6.25 * h - 5 * a + 5) * sport);
//            } else {
//                cal = (float) ((float) (10 * w + 6.25 * h - 5 * a - 161) * sport);
//            }

//            editor.putInt("weight", weightValue);
//            editor.putInt("height", heightValue);
//            editor.putInt("age", ageValue);
//            editor.putString("sex", sex);
//            editor.putInt("cal", ((int) cal));
//            editor.putBoolean("isAuth", true);
//            editor.apply();


//            Intent toMain = new Intent(Authorization.this, MainActivity.class);
//            startActivity(toMain);
        });

    }
}