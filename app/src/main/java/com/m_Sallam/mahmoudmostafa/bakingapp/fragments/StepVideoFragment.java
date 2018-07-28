package com.m_Sallam.mahmoudmostafa.bakingapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m_Sallam.mahmoudmostafa.bakingapp.Activities.RecipesActivity;
import com.m_Sallam.mahmoudmostafa.bakingapp.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class StepVideoFragment extends android.support.v4.app.Fragment {

    SimpleExoPlayerView simpleExoPlayerView;
    String VideoUrl;
    SimpleExoPlayer simpleExoPlayer;
    long position;
    boolean ready ;


    public StepVideoFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (RecipesActivity.mTwoPane == true && getArguments() != null) {
            VideoUrl = getArguments().getString("VideoUrl");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.step_video_fragment, container, false);
        simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.player_view);

        //in case of single pane data is sent by using intent
        if (RecipesActivity.mTwoPane == false) {
            //getting the videos link from the intent
            Intent intent = getActivity().getIntent();
            VideoUrl = intent.getStringExtra("VideoUrl");

            //if there is a link we need to initialize our player
            if (VideoUrl != null) {
                intialize(VideoUrl);
            } else {
//                Toast.makeText(getContext(), "video un available ", Toast.LENGTH_LONG).show();
                releasePlayer();
            }

        }

        //checking whether the bundle is empty or not
        if (savedInstanceState != null && RecipesActivity.mTwoPane==false  )
        {
            //in this case there is a video playing
            position = savedInstanceState.getLong("position");
            simpleExoPlayer.seekTo(position);
            ready = savedInstanceState.getBoolean("ready") ;
            simpleExoPlayer.setPlayWhenReady(ready);

        }

        return rootView;
    }


    //for saving the seeking position
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("position", position);
        outState.putBoolean("ready" , ready);
        super.onSaveInstanceState(outState);
    }

    //this method used for building our player
    public void intialize(String mediaUrl) {

            //creating the defaults
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            //create the player
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mediaUrl),
                    new DefaultDataSourceFactory(getActivity(), userAgent), new DefaultExtractorsFactory(),
                    null, null);

            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(ready);
            ready = simpleExoPlayer.getPlayWhenReady() ;

    }


    //during the activity life cycle
    @Override
    public void onPause() {
        if (simpleExoPlayer != null) {

            ready = simpleExoPlayer.getPlayWhenReady();
            position = simpleExoPlayer.getCurrentPosition();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;

        }

        super.onPause();

    }

    @Override
    public void onStop() {
        releasePlayer();
        super.onStop();
    }

    @Override
    public void onResume() {
        intialize(VideoUrl);
        if (position != C.TIME_UNSET) {
            simpleExoPlayer.seekTo(position);
        }
        super.onResume();
    }

    @Override
    public void onStart() {
        releasePlayer();
        super.onStart();
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            long playbackPosition = simpleExoPlayer.getCurrentPosition();
            long currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }
}
