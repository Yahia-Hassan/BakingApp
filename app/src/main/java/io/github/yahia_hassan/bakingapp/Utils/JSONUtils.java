package io.github.yahia_hassan.bakingapp.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.github.yahia_hassan.bakingapp.POJO.Ingredient;
import io.github.yahia_hassan.bakingapp.POJO.Recipe;
import io.github.yahia_hassan.bakingapp.POJO.Step;

public class JSONUtils {
    private static final String TAG = JSONUtils.class.getSimpleName();

    private static final String ID = "id";
    private static final String NAME = "name";

    private static final String INGREDIENTS_JSON_ARRAY = "ingredients";
    private static final String INGREDIENTS_QUANTITY = "quantity";
    private static final String INGREDIENTS_MEASURE = "measure";
    private static final String INGREDIENTS_INGREDIENT = "ingredient";

    private static final String STEPS_JSON_ARRAY = "steps";
    private static final String STEPS_ID = "id";
    private static final String STEPS_SHORT_DESCRIPTION = "shortDescription";
    private static final String STEPS_DESCRIPTION = "description";
    private static final String STEPS_VIDEO_URL = "videoURL";
    private static final String STEPS_THUMBNAIL_URL = "thumbnailURL";

    private static final String SERVINGS = "servings";

    public static ArrayList<Recipe> getRecipeList(String stringJSON) {
        JSONArray mainJsonArray = getJSONArray(stringJSON);
        if (mainJsonArray == null) {
            Log.e(TAG, "mainJsonArray is null");
            return null;
        }
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        Recipe recipeObject;
        for (int i = 0; i < mainJsonArray.length(); i++) {
            try {
                JSONObject nthJSONObject = mainJsonArray.getJSONObject(i);
                int id = nthJSONObject.getInt(ID);
                String name = nthJSONObject.getString(NAME);
                ArrayList<Ingredient> ingredientArrayList = getIngredientList(mainJsonArray, i);
                ArrayList<Step> stepArrayList = getStepList(mainJsonArray, i);
                int servings = nthJSONObject.getInt(SERVINGS);
                recipeObject = new Recipe(id, name, ingredientArrayList, stepArrayList, servings);
                recipeArrayList.add(recipeObject);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "JSONException in getRecipeList()");
            }
        }
        return recipeArrayList;
    }

    private static JSONArray getJSONArray(String stringJSON) {
        try {
            return new JSONArray(stringJSON);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "JSONException at getJSONArray()");
            return null;
        }
    }

    /*
        Returns an ArrayList of Ingredient objects for a given index in the main JSON array.
     */
    private static ArrayList<Ingredient> getIngredientList(JSONArray mainJSONArray, int index) {
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        Ingredient ingredientObject;
        try {
            JSONObject jsonObject = mainJSONArray.getJSONObject(index);
            JSONArray ingredientsJSONArray = jsonObject.getJSONArray(INGREDIENTS_JSON_ARRAY);
            for (int i = 0; i < ingredientsJSONArray.length(); i++) {
                JSONObject nthJSONObject = ingredientsJSONArray.getJSONObject(i);
                double quantity = nthJSONObject.getDouble(INGREDIENTS_QUANTITY);
                String measure = nthJSONObject.getString(INGREDIENTS_MEASURE);
                String ingredient = nthJSONObject.getString(INGREDIENTS_INGREDIENT);
                ingredientObject = new Ingredient(quantity, measure, ingredient);
                ingredientArrayList.add(ingredientObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "JSONException in getIngredientList()");
        }
        return ingredientArrayList;
    }


    /*
        Returns an ArrayList of Step objects for a given index in the main JSON array.
     */
    private static ArrayList<Step> getStepList(JSONArray mainJSONArray, int index) {
        ArrayList<Step> stepArrayList = new ArrayList<>();
        Step stepObject;
        try {
            JSONObject jsonObject = mainJSONArray.getJSONObject(index);
            JSONArray stepsJSONArray = jsonObject.getJSONArray(STEPS_JSON_ARRAY);
            for (int i = 0; i < stepsJSONArray.length(); i++) {
                JSONObject nthJSONObject = stepsJSONArray.getJSONObject(i);
                int id = nthJSONObject.getInt(STEPS_ID);
                String shortDescription = nthJSONObject.getString(STEPS_SHORT_DESCRIPTION);
                String description = nthJSONObject.getString(STEPS_DESCRIPTION);
                String videoURL = nthJSONObject.getString(STEPS_VIDEO_URL);
                String thumbnailURL = nthJSONObject.getString(STEPS_THUMBNAIL_URL);

                stepObject = new Step(id, shortDescription, description, videoURL, thumbnailURL);
                stepArrayList.add(stepObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "JSONException in getStepList()");
        }
        return stepArrayList;
    }
}
