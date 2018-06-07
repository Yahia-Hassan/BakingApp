package io.github.yahia_hassan.bakingapp;


import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import io.github.yahia_hassan.bakingapp.POJO.Step;

import static android.view.View.GONE;


public class DetailsFragment extends Fragment {
    TextView mDetailsFragmentTextView;
    PlayerView mDetailsFragmentPlayerView;
    SimpleExoPlayer mSimpleExoPlayer;

    public static final String PARCELABLE_STEP_ARGUMENT = "parcelable_step_array_list_argument";


    public DetailsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        mDetailsFragmentPlayerView = view.findViewById(R.id.fragment_details_player_view);
        mDetailsFragmentTextView = view.findViewById(R.id.fragment_details_text_view);

        Step step = null;
        Bundle bundle = getArguments();
        if (bundle.getParcelable(PARCELABLE_STEP_ARGUMENT) != null) {
            step = bundle.getParcelable(PARCELABLE_STEP_ARGUMENT);
        }

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        mSimpleExoPlayer =
                ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        mDetailsFragmentPlayerView.setPlayer(mSimpleExoPlayer);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), getString(R.string.app_name)));


        mDetailsFragmentTextView.setText(step.getDescription());
        if (step.getVideoURL().isEmpty() && step.getThumbnailURL().isEmpty()) {
            mDetailsFragmentPlayerView.setVisibility(GONE);
        } else if (step.getVideoURL().isEmpty() && !step.getThumbnailURL().isEmpty()) {
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(step.getThumbnailURL()));
            mSimpleExoPlayer.prepare(videoSource);
            mSimpleExoPlayer.setPlayWhenReady(true);
        } else if (!step.getVideoURL().isEmpty() && step.getThumbnailURL().isEmpty()) {
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(step.getVideoURL()));
            mSimpleExoPlayer.prepare(videoSource);
            mSimpleExoPlayer.setPlayWhenReady(true);
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mSimpleExoPlayer.release();

    }

}
