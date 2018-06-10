package io.github.yahia_hassan.bakingapp.Widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.io.IOException;
import java.util.ArrayList;

import io.github.yahia_hassan.bakingapp.POJO.Ingredient;
import io.github.yahia_hassan.bakingapp.POJO.Recipe;
import io.github.yahia_hassan.bakingapp.R;
import io.github.yahia_hassan.bakingapp.Utils.JSONUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WidgetAdapter implements RemoteViewsService.RemoteViewsFactory {

    private static final String STRING_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private Context mContext;
    private ArrayList<Ingredient> mIngredientArrayList;

    public WidgetAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        loadIngredients();
    }

    @Override
    public void onDataSetChanged() {

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
        return 4;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void loadIngredients() {
        new AsyncTask<Void, Void, ArrayList<Recipe>>() {
            @Override
            protected ArrayList<Recipe> doInBackground(Void... voids) {
                String stringJSON;
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(STRING_URL)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    stringJSON = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    stringJSON = null;
                }
                return JSONUtils.getRecipeList(stringJSON);
            }

            @Override
            protected void onPostExecute(ArrayList<Recipe> data) {
                SharedPreferences sharedPref = mContext.getSharedPreferences(mContext.getString(R.string.recent_recipe_file_key), Context.MODE_PRIVATE);
                int recentRecipeId = sharedPref.getInt(mContext.getString(R.string.recent_recipe_id), 1);
                int index = recentRecipeId - 1;
                Log.d("WidgetAdapter", "Index Is " + String.valueOf(index));
                mIngredientArrayList = data.get(index).getIngredients();
                Log.d("WidgetAdapter", "Load Finished");
                Log.d("WidgetAdapter", data.toString());
            }
        }.execute();
    }
}
