package io.github.yahia_hassan.bakingapp.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.yahia_hassan.bakingapp.pojo.Ingredient;
import io.github.yahia_hassan.bakingapp.R;

public class MasterListIngredientsAdapter extends RecyclerView.Adapter<MasterListIngredientsAdapter.IngredientsViewHolder> {

    private Context mContext;
    private ArrayList<Ingredient> mIngredientArrayList;

    public MasterListIngredientsAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ingredient_list_item, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        Ingredient currentIngredient = mIngredientArrayList.get(position);
        double quantity = currentIngredient.getQuantity();
        String measure = currentIngredient.getMeasure();
        String ingredient = currentIngredient.getIngredient();

        holder.quantityTextView.setText(String.valueOf(quantity));
        holder.measureTextView.setText(measure);
        holder.ingredientTextView.setText(ingredient);
    }

    @Override
    public int getItemCount() {
        if (mIngredientArrayList == null) return 0;
        return mIngredientArrayList.size();
    }

    public void setIngredientArrayList(ArrayList<Ingredient> ingredientArrayList) {
        mIngredientArrayList = ingredientArrayList;
        notifyDataSetChanged();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        TextView quantityTextView;
        TextView measureTextView;
        TextView ingredientTextView;
        public IngredientsViewHolder(View itemView) {
            super(itemView);
            quantityTextView = itemView.findViewById(R.id.ingredient_list_item_quantity_text_view);
            measureTextView = itemView.findViewById(R.id.ingredient_list_item_measure_text_view);
            ingredientTextView = itemView.findViewById(R.id.ingredient_list_item_ingredient_text_view);
        }

    }
}
