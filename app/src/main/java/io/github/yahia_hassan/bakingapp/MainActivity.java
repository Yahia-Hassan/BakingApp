package io.github.yahia_hassan.bakingapp;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.yahia_hassan.bakingapp.adapters.RecipesAdapter;
import io.github.yahia_hassan.bakingapp.pojo.Recipe;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipeClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.main_activity_recycler_view);
        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        if (isTablet) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        }

        final RecipesAdapter recipesAdapter = new RecipesAdapter(this, this);
        recyclerView.setAdapter(recipesAdapter);
        final MainActivityViewModel mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getRecipes().observe(this, new Observer<ArrayList<Recipe>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Recipe> recipes) {
                recipesAdapter.setRecipes(recipes);
            }
        });
    }

    @Override
    public void onRecipeClickListener(Recipe recipe) {
        Intent intent = new Intent(this, MasterListActivity.class);
        intent.putExtra(MasterListActivity.RECIPE_EXTRA, recipe);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.recent_recipe_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.recent_recipe_id), recipe.getId());
        editor.commit();

        startActivity(intent);
    }
}
