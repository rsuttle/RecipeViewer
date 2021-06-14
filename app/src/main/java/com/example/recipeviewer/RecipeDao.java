package com.example.recipeviewer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipeentity")
    List<Recipe> getAll();

    @Insert
    void insertAll(RecipeEntity... recipes);

    @Delete
    void delete(RecipeEntity recipe);
}
