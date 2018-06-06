package io.github.yahia_hassan.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import io.github.yahia_hassan.bakingapp.POJO.Recipe;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipeClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.main_activity_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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

    }
}
