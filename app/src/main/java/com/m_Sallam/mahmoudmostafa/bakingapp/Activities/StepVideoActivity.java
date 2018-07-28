package com.m_Sallam.mahmoudmostafa.bakingapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.m_Sallam.mahmoudmostafa.bakingapp.R;
import com.m_Sallam.mahmoudmostafa.bakingapp.fragments.StepVideoFragment;

public class StepVideoActivity extends AppCompatActivity {


    android.support.v4.app.FragmentManager fragmentManager;
    StepVideoFragment stepVideoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_video);

        stepVideoFragment = new StepVideoFragment();

        if (savedInstanceState != null) {

            stepVideoFragment = (StepVideoFragment) getSupportFragmentManager().getFragment(savedInstanceState, "myFragment");
            getSupportFragmentManager().beginTransaction().replace(R.id.videoContainer, stepVideoFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.videoContainer, stepVideoFragment).commit();

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        // putting the fragment in the bundle
        getSupportFragmentManager().putFragment(outState, "myFragment", stepVideoFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        stepVideoFragment = (StepVideoFragment) getSupportFragmentManager().getFragment(savedInstanceState, "myFragment");
        super.onRestoreInstanceState(savedInstanceState);
    }
}
