package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioGroup;

public class Authorization extends AppCompatActivity {

    FormBase nameForm;
    FormBase weightForm;
    FormBase heightForm;
    FormBase ageForm;
    FormBase sexForm;
    FormBase physForm;

    FormBase[] forms;

    boolean is_valid() {
        int validCount = 0;
        for (FormBase form : forms) {
            if (form.validate())
                validCount++;
        }

        return validCount == forms.length;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        // dropdown menu
        AutoCompleteTextView menu = findViewById(R.id.autoCompleteText);
        String[] physOptions = {"Нет физической нагрузки", "3 раза в неделю", "5 раз в неделю",
                "Каждый день", "Каждый день + физическая работа"};
        menu.setAdapter(new ArrayAdapter<>(this, R.layout.list_item, physOptions));

        nameForm = new FormBase(this, findViewById(R.id.textFieldName), findViewById(R.id.editTextName));
        weightForm = new FormBase(this, findViewById(R.id.textFieldWeight), findViewById(R.id.editTextWeight));
        heightForm = new FormBase(this, findViewById(R.id.textFieldHeight), findViewById(R.id.editTextHeight));
        ageForm = new FormBase(this, findViewById(R.id.textFieldAge), findViewById(R.id.editTextAge));
        sexForm = new FormBase(this, (RadioGroup) findViewById(R.id.radioGroupSex));
        physForm = new FormBase(this, menu);

        forms = new FormBase[]{nameForm, weightForm, heightForm, ageForm, sexForm, physForm};

        Button btn = findViewById(R.id.buttonAuth);
        btn.setOnClickListener(v -> {
            if (!is_valid())
                return;

            String nameValue = nameForm.getText();
            int weightValue = Integer.parseInt(weightForm.getText());
            int heightValue = Integer.parseInt(heightForm.getText());
            int ageValue = Integer.parseInt(ageForm.getText());

            String sexValue;
            if (R.id.radioButtonSex1 == sexForm.group.getCheckedRadioButtonId())
                sexValue = "male";
            else
                sexValue = "female";

            String physText = physForm.getText();
            int physValue = 1;
            for (int i = 0; i < physOptions.length; i++)
                if (physText.equals(physOptions[i])) {
                    physValue = i + 1;
                    break;
                }

            double sportMult = 1.2;
            if (physValue == 1)
                sportMult = 1.2;
            else if (physValue == 2)
                sportMult = 1.375;
            else if (physValue == 3)
                sportMult = 1.55;
            else if (physValue == 4)
                sportMult = 1.725;
            else if (physValue == 5)
                sportMult = 1.9;


            float calories;
            if (sexValue.equals("male")) {
                calories = (float) ((float) (10 * weightValue + 6.25 * heightValue - 5 * ageValue + 5) * sportMult);
            } else {
                calories = (float) ((float) (10 * weightValue + 6.25 * heightValue - 5 * ageValue - 161) * sportMult);
            }
            float p = calories*0.3f;
            float f = calories*0.3f;
            float ch = calories*0.4f;

            SharedPreferences.Editor editor = getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
            editor.putString("name", nameValue);
            editor.putInt("weight", weightValue);
            editor.putInt("height", heightValue);
            editor.putInt("age", ageValue);
            editor.putString("sex", sexValue);
            editor.putFloat("sport", (float) sportMult);
            editor.putInt("cal", ((int) calories));
            editor.putInt("proteins", ((int) p));
            editor.putInt("fats", ((int) f));
            editor.putInt("carbohydrates", ((int) ch));
            editor.putBoolean("isAuth", true);

            editor.apply();

            Intent toMain = new Intent(Authorization.this, MainActivity.class);
            startActivity(toMain);
        });

    }
}