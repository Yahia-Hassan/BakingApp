package io.github.yahia_hassan.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.yahia_hassan.bakingapp.POJO.Recipe;
import io.github.yahia_hassan.bakingapp.POJO.Step;

public class MasterListActivity extends AppCompatActivity implements MasterListFragment.OnFragmentItemInteractionWhenIsTabletListener {

    public static final String RECIPE_EXTRA = "recipe_extra";
    public static final int FIRST_ITEM_INDEX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);

        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);

        if (savedInstanceState == null) {
            Intent intent  = getIntent();
            Recipe recipe = intent.getParcelableExtra(RECIPE_EXTRA);

            MasterListFragment masterListFragment = new MasterListFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(MasterListFragment.RECIPE_BUNDLE_EXTRA_KEY, recipe);
            masterListFragment.setArguments(bundle);

            if (isTablet) {
                DetailsFragment detailsFragment = new DetailsFragment();
                Bundle detailsFragmentBundle = new Bundle();
                detailsFragmentBundle.putParcelable(DetailsFragment.PARCELABLE_STEP_ARGUMENT, recipe.getSteps().get(FIRST_ITEM_INDEX));
                detailsFragment.setArguments(detailsFragmentBundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.master_list_fragment_left_container, masterListFragment)
                        .commit();

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.master_list_fragment_right_container, detailsFragment)
                        .commit();

            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.master_list_fragment_container, masterListFragment)
                        .commit();
            }
        }


    }

    @Override
    public void onFragmentItemInteractionWhenIsTablet(Step step) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsFragment.PARCELABLE_STEP_ARGUMENT, step);
        detailsFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.master_list_fragment_right_container, detailsFragment)
                .commit();
    }
}
