package com.example.recipeviewer;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {RecipeEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();
    private static AppDatabase instance;
    public static AppDatabase getInstance(Context context){
        if(instance != null) return instance;
        instance = Room.databaseBuilder(context,AppDatabase.class,"recipesDb").allowMainThreadQueries().build();
        return instance;

    }
}

