package io.github.yahia_hassan.bakingapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import io.github.yahia_hassan.bakingapp.POJO.Recipe;
import io.github.yahia_hassan.bakingapp.Utils.JSONUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Recipe>> mRecipes;
    private static final String STRING_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public LiveData<ArrayList<Recipe>> getRecipes() {
        if (mRecipes == null) {
            mRecipes = new MutableLiveData<>();
            loadRecipes();
        }
        return mRecipes;
    }

    private void loadRecipes() {
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
                mRecipes.setValue(data);

            }
        }.execute();
    }
}
