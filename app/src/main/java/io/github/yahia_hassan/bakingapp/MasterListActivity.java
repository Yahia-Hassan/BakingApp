package io.github.yahia_hassan.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.github.yahia_hassan.bakingapp.POJO.Recipe;

public class MasterListActivity extends AppCompatActivity {

    public static final String RECIPE_EXTRA = "recipe_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);

        if (savedInstanceState == null) {
            Intent intent  = getIntent();
            Recipe recipe = intent.getParcelableExtra(RECIPE_EXTRA);

            MasterListFragment masterListFragment = new MasterListFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(MasterListFragment.RECIPE_BUNDLE_EXTRA_KEY, recipe);
            masterListFragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.master_list_fragment_container, masterListFragment)
                    .commit();
        }


    }
}
