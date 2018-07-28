package com.m_Sallam.mahmoudmostafa.bakingapp.Activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.m_Sallam.mahmoudmostafa.bakingapp.R;
import com.m_Sallam.mahmoudmostafa.bakingapp.fragments.StepsFragment;

public class StepsActivity extends AppCompatActivity {


    android.support.v4.app.FragmentManager fragmentManager;
    StepsFragment stepsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        getSupportActionBar().setTitle("Recipe steps ") ;



        stepsFragment = new StepsFragment();

        if (savedInstanceState != null) {
            stepsFragment = (StepsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "myFragment");
            getSupportFragmentManager().beginTransaction().replace(R.id.stepsContainer, stepsFragment).commit();
        } else {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.stepsContainer, stepsFragment).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, "myFragment", stepsFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        stepsFragment = (StepsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "myFragment");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back, menu);
        return  true ;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId() ;
        if(id==R.id.back)
        {
            //performing back operation
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
