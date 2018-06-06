package io.github.yahia_hassan.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.github.yahia_hassan.bakingapp.Adapters.MasterListIngredientsAdapter;
import io.github.yahia_hassan.bakingapp.Adapters.MasterListStepsAdapter;
import io.github.yahia_hassan.bakingapp.POJO.Recipe;
import io.github.yahia_hassan.bakingapp.POJO.Step;


public class MasterListFragment extends Fragment implements MasterListStepsAdapter.StepClickListener {

    public static final String RECIPE_BUNDLE_EXTRA_KEY = "recipe_bundle_extra_key";

    public MasterListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_master_list, container, false);
        Recipe recipe;

        RecyclerView ingredientRecyclerView = view.findViewById(R.id.master_list_fragment_ingredients_recycler_view);
        MasterListIngredientsAdapter masterListIngredientsAdapter = new MasterListIngredientsAdapter(getContext());
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ingredientRecyclerView.setAdapter(masterListIngredientsAdapter);

        RecyclerView stepRecyclerView = view.findViewById(R.id.master_list_fragment_steps_recycler_view);
        MasterListStepsAdapter masterListStepsAdapter = new MasterListStepsAdapter(getContext(), this);
        stepRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        stepRecyclerView.setAdapter(masterListStepsAdapter);

        Bundle bundle = getArguments();
        if (bundle.getParcelable(RECIPE_BUNDLE_EXTRA_KEY) != null) {
            recipe = bundle.getParcelable(RECIPE_BUNDLE_EXTRA_KEY);
            masterListIngredientsAdapter.setIngredientArrayList(recipe.getIngredients());
            masterListStepsAdapter.setStepArrayList(recipe.getSteps());
        }

        return view;
    }

    @Override
    public void onStepClickListener(ArrayList<Step> stepArrayList, int position) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putParcelableArrayListExtra("steps_list", stepArrayList);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
