package com.example.recipeviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RecipeRecViewAdapter extends RecyclerView.Adapter<RecipeRecViewAdapter.ViewHolder> {

    private ArrayList<Recipe> recipes = new ArrayList<>();
    //context of main activity i think
    private Context context;

    public RecipeRecViewAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(recipes.get(position).getName());


        holder.btnLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String url = recipes.get(position).getRecipeUrl();
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                context.startActivity(launchBrowser);
            }
        });

        //Save recipe to local database
        holder.btnAddFavorite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new AgentAsyncTask((Activity) v.getContext(), recipes.get(position)).execute();
            }
        });

        Glide.with(context).asBitmap().load(recipes.get(position).getImageUrl()).centerCrop().into(holder.imgFood);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName;
        private ImageView imgFood;
        private CardView parent;
        private Button btnLink,btnAddFavorite;

        //Add other items from xml layout template here
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgFood = itemView.findViewById(R.id.imgFood);
            parent = itemView.findViewById(R.id.parent);
            btnLink = itemView.findViewById(R.id.btnLink);
            btnAddFavorite = itemView.findViewById(R.id.btnAddFavorite);
        }


    }

    private static class AgentAsyncTask extends AsyncTask<Void, Void, Integer> {

        //Prevent leak
        private WeakReference<Activity> weakActivity;
        private Recipe recipe;

        public AgentAsyncTask(Activity activity, Recipe recipe) {
            weakActivity = new WeakReference<>(activity);
            this.recipe = recipe;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Activity activity = (Activity) weakActivity.get();
            AppDatabase db = AppDatabase.getInstance(activity);
            RecipeDao recipeDao = db.recipeDao();
            recipeDao.insertAll(new RecipeEntity(recipe));
            Log.i("INSERT","inserted to db");
            return 0;
        }

        @Override
        protected void onPostExecute(Integer agentsCount) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return;
            }


            Toast.makeText(activity, "Recipe added to favorites.", Toast.LENGTH_LONG).show();

        }
    }
}
