package io.github.yahia_hassan.bakingapp.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import io.github.yahia_hassan.bakingapp.adapters.WidgetAsyncTask;
import io.github.yahia_hassan.bakingapp.pojo.Ingredient;
import io.github.yahia_hassan.bakingapp.pojo.Recipe;
import io.github.yahia_hassan.bakingapp.R;
public class WidgetAdapter implements RemoteViewsService.RemoteViewsFactory {


    private Context mContext;
    private ArrayList<Ingredient> mIngredientArrayList;

    public WidgetAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        WidgetAsyncTask widgetAsyncTask = new WidgetAsyncTask(mContext);
        try {
            ArrayList<Recipe> recipeArrayList = widgetAsyncTask.execute().get(3L, java.util.concurrent.TimeUnit.SECONDS);
            SharedPreferences sharedPref = mContext.getSharedPreferences(mContext.getString(R.string.recent_recipe_file_key), Context.MODE_PRIVATE);
            int recentRecipeId = sharedPref.getInt(mContext.getString(R.string.recent_recipe_id), 1);
            int index = recentRecipeId - 1;
            mIngredientArrayList = recipeArrayList.get(index).getIngredients();
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mIngredientArrayList != null) return mIngredientArrayList.size();
        else return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mIngredientArrayList != null) {
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
            remoteViews.setTextViewText(R.id.widget_quantity_text_view, String.valueOf(mIngredientArrayList.get(position).getQuantity()));
            remoteViews.setTextViewText(R.id.widget_measure_text_view, String.valueOf(mIngredientArrayList.get(position).getMeasure()));
            remoteViews.setTextViewText(R.id.widget_ingredient_text_view, String.valueOf(mIngredientArrayList.get(position).getIngredient()));
            return remoteViews;
        } else {
            return null;
        }
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
