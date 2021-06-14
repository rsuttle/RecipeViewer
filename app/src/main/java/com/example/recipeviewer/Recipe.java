package com.example.recipeviewer;

public class Recipe {
    private String name;
    private int score;
    private String imageUrl;
    private String recipeUrl;

    public Recipe(String name, int score, String imageUrl, String recipeUrl) {
        this.name = name;
        this.score = score;
        this.imageUrl = imageUrl;
        this.recipeUrl = recipeUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRecipeUrl() {
        return recipeUrl;
    }

    public void setRecipeUrl(String recipeUrl) {
        this.recipeUrl = recipeUrl;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", email='" + score + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
