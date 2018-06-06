package io.github.yahia_hassan.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.yahia_hassan.bakingapp.POJO.Recipe;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private Context mContext;
    private RecipeClickListener mRecipeClickListener;
    private ArrayList<Recipe> mRecipes;

    public RecipesAdapter(Context context, RecipeClickListener recipeClickListener) {
        mContext = context;
        mRecipeClickListener = recipeClickListener;
    }

    public interface RecipeClickListener {
        void onRecipeClickListener(int position);
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recipe_list_item, parent, false);

        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        String recipeName = mRecipes.get(position).getName();
        holder.recipeTextView.setText(recipeName);
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) return 0;
        return mRecipes.size();
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeTextView;
        public RecipesViewHolder(View itemView) {
            super(itemView);
            recipeTextView = itemView.findViewById(R.id.list_item_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mRecipeClickListener.onRecipeClickListener(position);
        }
    }
}
