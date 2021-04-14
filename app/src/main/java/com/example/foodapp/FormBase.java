package com.example.foodapp;

import android.content.Context;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class FormBase {
    public TextInputLayout layout;
    public TextInputEditText input;
    public RadioGroup group;
    public AutoCompleteTextView menu;
    public String type;
    public Context ctx;

    FormBase() {
        this.type = "null";
    }

    FormBase(Context ctx, TextInputLayout layout, TextInputEditText input) {
        this.ctx = ctx;
        this.layout = layout;
        this.input = input;
        this.type = "text";
    }

    FormBase(Context ctx, RadioGroup group) {
        this.ctx = ctx;
        this.group = group;
        this.type = "group";
    }

    FormBase(Context ctx, AutoCompleteTextView menu) {
        this.ctx = ctx;
        this.menu = menu;
        this.type = "menu";
    }

    boolean validate() {
        if (this.type.equals("text")) {
            if (Objects.requireNonNull(input.getText()).toString().isEmpty()) {
                layout.setError("Введите своё имя");
                return false;
            } else {
                layout.setError(null);
                layout.setErrorEnabled(false);
                return true;
            }
        } else if (this.type.equals("group")) {
            if (group.getCheckedRadioButtonId() == -1) {
                Toast.makeText(ctx, "Укажите свой пол", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } else if (this.type.equals("menu")) {
            if (menu.getEditableText().toString().isEmpty()) {
                menu.setError("Укажите свою физическую активность");
                return false;
            } else {
                menu.setError(null);
                return true;
            }
        }
        return false;
    }

    String getText() {
        if (this.type.equals("text"))
            return Objects.requireNonNull(input.getText()).toString();
        else if (this.type.equals("menu"))
            return menu.getEditableText().toString();
        else
            return "No string for this type";
    }
}
