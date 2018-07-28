package com.m_Sallam.mahmoudmostafa.bakingapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.m_Sallam.mahmoudmostafa.bakingapp.R;
import com.m_Sallam.mahmoudmostafa.bakingapp.fragments.IngredientsFragment;


public class IngredientsActivity extends AppCompatActivity {


    android.support.v4.app.FragmentManager fragmentManager;
    IngredientsFragment ingredientsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        ingredientsFragment = new IngredientsFragment();

        if (savedInstanceState != null) {
            ingredientsFragment = (IngredientsFragment) getSupportFragmentManager().
                    getFragment(savedInstanceState, "myFragment");
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ingredientsContainer, ingredientsFragment).commit();


        } else {

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ingredientsContainer, ingredientsFragment).commit();

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        getSupportFragmentManager().putFragment(outState, "myFragment", ingredientsFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        ingredientsFragment = (IngredientsFragment) getSupportFragmentManager().
                getFragment(savedInstanceState, "myFragment");

        super.onRestoreInstanceState(savedInstanceState);
    }


}
