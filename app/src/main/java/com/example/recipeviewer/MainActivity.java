package com.example.recipeviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.recipeviewer.MESSAGE";
    private RecyclerView recipeRecyclerView;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    RecipeRecViewAdapter adapter;
    ExtendedFloatingActionButton favoritesButton;
    RecipeDao recipeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recipeRecyclerView = findViewById(R.id.recipeRecView);
        favoritesButton = findViewById(R.id.btnViewFavorites);




        loadRecipeData();

    }


    public void goToFavoritesScreen(View view){
        Intent intent = new Intent(this, FavoritesScreen.class);
        intent.putExtra(EXTRA_MESSAGE, "hi");
        startActivity(intent);
    }

    private void loadRecipeData(){
        String url = "https://api.spoonacular.com/recipes/random?apiKey=aa71101df6e942c090d4f79e20431c11&number=10";
        String apiKey = "aa71101df6e942c090d4f79e20431c11";

        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String> () {
            @Override
            public void onResponse(String res){
                try{
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray arr = jsonObject.getJSONArray("recipes");
                    for(int i = 0; i < arr.length(); ++i){
                        String name = arr.getJSONObject(i).getString("title");
                        int score = arr.getJSONObject(i).getInt("spoonacularScore");
                        String image = arr.getJSONObject(i).getString("image");
                        String recipeUrl = arr.getJSONObject(i).getString("spoonacularSourceUrl");
                        recipes.add(new Recipe(name,score,image,recipeUrl));
                    }

                    adapter = new RecipeRecViewAdapter(getApplicationContext());
                    adapter.setRecipes(recipes);
                    recipeRecyclerView.setAdapter(adapter);
                    //recipeRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
                    recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                } catch(JSONException e){
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError e){
                    Toast.makeText(MainActivity.this, "Error" + e.toString(),Toast.LENGTH_SHORT);
                }
        }){
            @Override
            public Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("apiKey",apiKey);
                params.put("number","1");
                return params;
            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                return params;
//            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        req.setShouldCache(false);
        requestQueue.add(req);

    }
}