package io.github.yahia_hassan.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.github.yahia_hassan.bakingapp.adapters.MasterListIngredientsAdapter;
import io.github.yahia_hassan.bakingapp.adapters.MasterListStepsAdapter;
import io.github.yahia_hassan.bakingapp.pojo.Recipe;
import io.github.yahia_hassan.bakingapp.pojo.Step;


public class MasterListFragment extends Fragment implements MasterListStepsAdapter.StepClickListener {

    public static final String RECIPE_BUNDLE_EXTRA_KEY = "recipe_bundle_extra_key";


    private OnFragmentItemInteractionWhenIsTabletListener mListener;

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
        /*
        Make new interface here, implement it in the MasterListActivity,
        if isTablet pass the Step object to the interface's method.
         */
        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        if (isTablet) {
            mListener.onFragmentItemInteractionWhenIsTablet(stepArrayList.get(position));
        } else {
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.putParcelableArrayListExtra(DetailsActivity.STEP_ARRAY_LIST_INTENT, stepArrayList);
            intent.putExtra(DetailsActivity.POSITION_STEP_ARRAY_LIST_INTENT, position);
            startActivity(intent);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentItemInteractionWhenIsTabletListener) {
            mListener = (OnFragmentItemInteractionWhenIsTabletListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentItemInteractionWhenIsTabletListener {
        void onFragmentItemInteractionWhenIsTablet(Step step);
    }
}