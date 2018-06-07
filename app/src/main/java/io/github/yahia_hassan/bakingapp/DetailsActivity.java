package io.github.yahia_hassan.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.yahia_hassan.bakingapp.POJO.Step;

public class DetailsActivity extends AppCompatActivity {

    public static final String STEP_ARRAY_LIST_INTENT = "step_array_list_intent";
    public static final String POSITION_STEP_ARRAY_LIST_INTENT = "position_step_array_list_intent";
    private int mArrayListPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        ArrayList<Step> stepArrayList = intent.getParcelableArrayListExtra(STEP_ARRAY_LIST_INTENT);
        int position = intent.getIntExtra(POSITION_STEP_ARRAY_LIST_INTENT, 0);
        mArrayListPosition = position;
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsFragment.PARCELABLE_STEP_ARGUMENT, stepArrayList.get(position));

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_details_step_fragment_container, detailsFragment)
                .commit();
    }
}
