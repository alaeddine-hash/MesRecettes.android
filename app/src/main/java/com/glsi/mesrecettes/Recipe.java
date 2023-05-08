package com.glsi.mesrecettes;

import java.io.Serializable;

public class Recipe implements Serializable {
    private String id;
    private String title;
    private String ingredients;
    private String instructions;
    private String userId; // Add userId field

    public Recipe() {}

    public Recipe(String id, String title, String ingredients, String instructions, String userId) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.userId = userId;
    }

    // Add getters and setters for all fields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
