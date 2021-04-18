package com.example.foodapp;

public class ProductState {
    private final String name;
    private final float calories;
    private final float proteins;
    private final float fats;
    private final float carbohydrates;
    private final float grams;

    public ProductState(String name, float calories, float proteins, float fats, float carbohydrates, float grams) {
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.grams = grams;
    }

    public String getName() {
        return name;
    }
    public float getCalories() {
        return calories;
    }
    public float getProteins() { return proteins; }
    public float getFats() { return fats; }
    public float getCarbohydrates() {
        return carbohydrates;
    }
    public float getGrams() {
        return grams;
    }

}
