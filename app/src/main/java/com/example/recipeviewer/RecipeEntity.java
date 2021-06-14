package com.example.recipeviewer;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"name","recipeUrl"})
public class RecipeEntity {
    @NonNull
    public String name;
    public int score;
    public String imageUrl;
    @NonNull
    public String recipeUrl;

    public RecipeEntity(String name, int score, String imageUrl, String recipeUrl){
        this.name = name;
        this.score = score;
        this.imageUrl = imageUrl;
        this.recipeUrl = recipeUrl;;
    }

    public RecipeEntity(Recipe recipe){
        name = recipe.getName();
        score = recipe.getScore();
        imageUrl = recipe.getImageUrl();
        recipeUrl = recipe.getRecipeUrl();
    }
}
