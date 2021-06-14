package com.example.recipeviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FavoritesScreen extends AppCompatActivity {


    private ArrayList<Recipe> recipes = new ArrayList<>();
    private RecyclerView favoritesRecyclerView;
    private FavoritesRecViewAdapter adapter;

    RecipeDao recipeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_screen);




        //Get favs from db, display in recyclerview in the exact same way as the main activity.
        //Can maybe reuse the adapter?
        //Use singleton for db
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        recipeDao = db.recipeDao();

        List<Recipe> recipeList = recipeDao.getAll();
        recipes.addAll(recipeList);
        for(int i = 0; i < recipes.size(); ++i){
            Log.i("Recipe", recipes.get(i).toString());
        }

        favoritesRecyclerView = findViewById(R.id.favRecipeRecView);
        adapter = new FavoritesRecViewAdapter(getApplicationContext());
        adapter.setRecipes(recipes);
        favoritesRecyclerView.setAdapter(adapter);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}