package io.github.yahia_hassan.bakingapp;


import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import io.github.yahia_hassan.bakingapp.pojo.Step;



public class DetailsFragment extends Fragment {
    private TextView mDetailsFragmentTextView;
    private PlayerView mDetailsFragmentPlayerView;
    private SimpleExoPlayer mPlayer;
    private ImageView mDetailsFragmentImageView;
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
        mDetailsFragmentImageView = view.findViewById(R.id.fragment_details_image_view);

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

    private void showVideo() {
        if (!mStep.getVideoURL().isEmpty() && mStep.getThumbnailURL().isEmpty()) {

            mDetailsFragmentImageView.setVisibility(View.GONE);
            mDetailsFragmentPlayerView.setVisibility(View.VISIBLE);
            initializePlayer(mStep.getVideoURL());

        } else if (mStep.getVideoURL().isEmpty() && !mStep.getThumbnailURL().isEmpty()) {

            mDetailsFragmentPlayerView.setVisibility(View.GONE);
            mDetailsFragmentImageView.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(mStep.getThumbnailURL())
                    .placeholder(R.drawable.ic_broken_image_black_48dp)
                    .error(R.drawable.ic_broken_image_black_48dp)
                    .into(mDetailsFragmentImageView);

        } else {
            mDetailsFragmentPlayerView.setVisibility(View.GONE);
            mDetailsFragmentImageView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            showVideo();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mPlayer == null)) {
            showVideo();
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
