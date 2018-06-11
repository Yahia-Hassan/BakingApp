package io.github.yahia_hassan.bakingapp;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
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
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import io.github.yahia_hassan.bakingapp.pojo.Step;

import static android.view.View.GONE;


public class DetailsFragment extends Fragment {
    TextView mDetailsFragmentTextView;
    PlayerView mDetailsFragmentPlayerView;
    SimpleExoPlayer mPlayer;
    private Step mStep;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    public static final String PARCELABLE_STEP_ARGUMENT = "parcelable_step_array_list_argument";

    private final String CURRENT_WINDOW_INDEX = "current_window_index";
    private final String CURRENT_PLAYBACK_POSITION = "current_playback_position";
    private final String PLAY_WHEN_READY = "play_when_ready";


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

        Bundle bundle = getArguments();
        if (bundle.getParcelable(PARCELABLE_STEP_ARGUMENT) != null) {
            mStep = bundle.getParcelable(PARCELABLE_STEP_ARGUMENT);
        }

        mDetailsFragmentTextView.setText(mStep.getDescription());

        if (savedInstanceState != null) {
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW_INDEX);
            playbackPosition = savedInstanceState.getLong(CURRENT_PLAYBACK_POSITION);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
        }

        return view;
    }

    private String getInstructionVideoUrl() {
        String videoUrl = null;
        if (mStep.getVideoURL().isEmpty() && !mStep.getThumbnailURL().isEmpty()) {
            videoUrl =  mStep.getThumbnailURL();

        } else if (!mStep.getVideoURL().isEmpty() && mStep.getThumbnailURL().isEmpty()) {
            videoUrl = mStep.getVideoURL();
        }
        return videoUrl;
    }

    private void initializePlayer(String stringUrl) {
        if (mPlayer == null) {
            mPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            mDetailsFragmentPlayerView.setPlayer(mPlayer);
            mPlayer.setPlayWhenReady(playWhenReady);
            mPlayer.seekTo(currentWindow, playbackPosition);
        }
        MediaSource mediaSource = buildMediaSource(Uri.parse(stringUrl));
        mPlayer.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(Util.getUserAgent(getContext(), getString(R.string.app_name))))
                .createMediaSource(uri);
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }


    private void saveState() {
        if (mPlayer != null) {
            /*
            These lines of code will not get called on devices with API 23 or lower as the mPlayer will be null because the
            last line in releasePlayer() is  mPlayer = null
             */
            playbackPosition = mPlayer.getCurrentPosition();
            currentWindow = mPlayer.getCurrentWindowIndex();
            playWhenReady = mPlayer.getPlayWhenReady();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putInt(CURRENT_WINDOW_INDEX, currentWindow);
        outState.putLong(CURRENT_PLAYBACK_POSITION, playbackPosition);
        outState.putBoolean(PLAY_WHEN_READY, playWhenReady);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if (getInstructionVideoUrl() != null) {
                initializePlayer(getInstructionVideoUrl());
            } else {
                mDetailsFragmentPlayerView.setVisibility(GONE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mPlayer == null)) {
            if (getInstructionVideoUrl() != null) {
                initializePlayer(getInstructionVideoUrl());
            } else {
                mDetailsFragmentPlayerView.setVisibility(GONE);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            saveState();
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

}
