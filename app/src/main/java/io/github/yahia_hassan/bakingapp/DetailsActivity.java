package io.github.yahia_hassan.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.yahia_hassan.bakingapp.POJO.Step;

public class DetailsActivity extends AppCompatActivity {

    public static final String STEP_ARRAY_LIST_INTENT = "step_array_list_intent";
    public static final String POSITION_STEP_ARRAY_LIST_INTENT = "position_step_array_list_intent";
    private int mArrayListPosition;
    private ArrayList<Step> mStepArrayList;
    private Button mNextButton;
    private Button mPreviousButton;
    DetailsFragment mDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mNextButton = findViewById(R.id.activity_details_next_button);
        mPreviousButton = findViewById(R.id.activity_details_previous_button);


        Intent intent = getIntent();
        mStepArrayList = intent.getParcelableArrayListExtra(STEP_ARRAY_LIST_INTENT);
        if (savedInstanceState == null) {
            int position = intent.getIntExtra(POSITION_STEP_ARRAY_LIST_INTENT, 0);
            Bundle bundle = new Bundle();
            bundle.putParcelable(DetailsFragment.PARCELABLE_STEP_ARGUMENT, mStepArrayList.get(position));

            updatePositionInSharedPreferences(position);
            mDetailsFragment = new DetailsFragment();
            mDetailsFragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_details_step_fragment_container, mDetailsFragment)
                    .commit();

        }

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        mArrayListPosition = sharedPreferences.getInt(getString(R.string.step_array_list_shared_preferences_key), 0);
        enableAndDisableButtons();

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayListPosition++;
                updatePositionInSharedPreferences(mArrayListPosition);
                enableAndDisableButtons();
                Bundle bundle = new Bundle();
                bundle.putParcelable(DetailsFragment.PARCELABLE_STEP_ARGUMENT, mStepArrayList.get(mArrayListPosition));

                mDetailsFragment = new DetailsFragment();
                mDetailsFragment.setArguments(bundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_details_step_fragment_container, mDetailsFragment)
                        .commit();
            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayListPosition--;
                updatePositionInSharedPreferences(mArrayListPosition);
                enableAndDisableButtons();
                Bundle bundle = new Bundle();
                bundle.putParcelable(DetailsFragment.PARCELABLE_STEP_ARGUMENT, mStepArrayList.get(mArrayListPosition));

                mDetailsFragment = new DetailsFragment();
                mDetailsFragment.setArguments(bundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_details_step_fragment_container, mDetailsFragment)
                        .commit();
            }
        });

    }

    private void updatePositionInSharedPreferences(int position) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.step_array_list_shared_preferences_key), position);
        editor.commit();
    }

    private void enableAndDisableButtons() {
        if (mArrayListPosition == 0) {
            mPreviousButton.setEnabled(false);
            mNextButton.setEnabled(true);
        } else if (mArrayListPosition == mStepArrayList.size() - 1) {
            mNextButton.setEnabled(false);
            mPreviousButton.setEnabled(true);
        } else {
            mNextButton.setEnabled(true);
            mPreviousButton.setEnabled(true);
        }
    }


}
