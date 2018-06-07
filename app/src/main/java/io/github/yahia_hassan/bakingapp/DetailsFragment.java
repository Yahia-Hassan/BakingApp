package io.github.yahia_hassan.bakingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import io.github.yahia_hassan.bakingapp.POJO.Step;


public class DetailsFragment extends Fragment {

    public static final String PARCELABLE_STEP_ARGUMENT = "parcelable_step_array_list_argument";


    public DetailsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        Step step;
        Bundle bundle = getArguments();
        if (bundle.getParcelable(PARCELABLE_STEP_ARGUMENT) != null) {
            step = bundle.getParcelable(PARCELABLE_STEP_ARGUMENT);
            TextView detailsFragmentTextView = view.findViewById(R.id.fragment_details_text_view);
            detailsFragmentTextView.setText(step.getDescription());

        }
        return view;
    }

}
