package com.m_Sallam.mahmoudmostafa.bakingapp.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.m_Sallam.mahmoudmostafa.bakingapp.Constants.Constants;
import com.m_Sallam.mahmoudmostafa.bakingapp.Model.DatabaseHandler;
import com.m_Sallam.mahmoudmostafa.bakingapp.Model.Ingredients;
import com.m_Sallam.mahmoudmostafa.bakingapp.R;
import com.m_Sallam.mahmoudmostafa.bakingapp.fragments.IngredientsFragment;
import com.m_Sallam.mahmoudmostafa.bakingapp.fragments.RecipesFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity
{


    android.support.v4.app.FragmentManager fragmentManager;
    RecipesFragment recipesFragment;


    IngredientsFragment ingredientsFragment;
    public static boolean mTwoPane = false;
    public static FrameLayout IngredientsFragmentContainer ;
    ArrayList<Ingredients> ingredientsList;
    public static DatabaseHandler db;

    String id = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        IngredientsFragmentContainer = (FrameLayout) findViewById(R.id.IngredientsFragmentId);

        getSupportActionBar().setTitle("Recipes");

        //checking whether it is one pane or two pane ui
        if (findViewById(R.id.IngredientsFragmentId) != null)
        {
            mTwoPane = true;
            if (savedInstanceState != null)
            {
                boolean visible = savedInstanceState.getBoolean("visible") ;
                if(visible==true) {
                    IngredientsFragmentContainer.setVisibility(View.VISIBLE);
                }
                else
                {
                    IngredientsFragmentContainer.setVisibility(View.GONE);
                }
            }

        }
        else
        {
            mTwoPane = false;
        }

        //in case of single pane
        recipesFragment = new RecipesFragment();
        getSupportFragmentManager()
                .beginTransaction().add(R.id.recipesContainer, recipesFragment).commit();

        if (mTwoPane == true)
        {

        }

        //create and use the database
        db = new DatabaseHandler(this);
        ingredientsList = new ArrayList<>();

        //getting the recipe id from the shared preference
        SharedPreferences sharedPreferences = getSharedPreferences("recipeId" , MODE_PRIVATE) ;
        id = sharedPreferences.getString("id" , null) ;

//        Toast.makeText(getApplicationContext() , "id : " + id , Toast.LENGTH_LONG).show();
        showIngredients(Constants.LINK);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        if(mTwoPane == true )
        {

            if(IngredientsFragmentContainer.getVisibility() == View.VISIBLE)
            {
                outState.putBoolean("visible" , true);
            }
            else if(IngredientsFragmentContainer.getVisibility() == View.GONE)
            {
                outState.putBoolean("visible" , false);
            }
        }
        super.onSaveInstanceState(outState);
    }






    private void showIngredients(String link) {
        StringRequest stringRequest = new StringRequest(link, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    ingredientsList.clear();

                    if(id==null)
                    {
                        id = "0" ;
                    }
                    JSONArray Result = new JSONArray(response);
                    JSONObject object = Result.getJSONObject(Integer.parseInt(id));
                    JSONArray ingredients = object.getJSONArray("ingredients");
                    for (int j = 0; j < ingredients.length(); j++) {
                        JSONObject obj = ingredients.getJSONObject(j);

                        String quantity = obj.getString("quantity");
                        String measure = obj.getString("measure");
                        String ingredient = obj.getString("ingredient");

                        ingredientsList.add(new Ingredients(quantity, measure, ingredient));
                        db.addIngredient(new Ingredients(quantity, measure, ingredient));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
