package com.m_Sallam.mahmoudmostafa.bakingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.m_Sallam.mahmoudmostafa.bakingapp.Activities.RecipesActivity;
import com.m_Sallam.mahmoudmostafa.bakingapp.Activities.StepsActivity;
import com.m_Sallam.mahmoudmostafa.bakingapp.Adapters.IngredientsAdapter;
import com.m_Sallam.mahmoudmostafa.bakingapp.Constants.Constants;
import com.m_Sallam.mahmoudmostafa.bakingapp.Model.Ingredients;
import com.m_Sallam.mahmoudmostafa.bakingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class IngredientsFragment extends android.support.v4.app.Fragment {

    public IngredientsFragment() {

    }

    String id;
    RecyclerView recyclerView;
    IngredientsAdapter adapter;
    ArrayList<Ingredients> dataSet;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.ingredients_fragment, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.ingredientsRecyclerView);
        dataSet = new ArrayList<>();


        //checking on save some thing on bundle or not

        if (savedInstanceState != null) {
            //getting the id from the others Activities
            //in case of two pane

//            Toast.makeText(getContext(), "saved called ", Toast.LENGTH_LONG).show();

            id = savedInstanceState.getString("id");

            dataSet = savedInstanceState.getParcelableArrayList("dataset");
            adapter = new IngredientsAdapter(getActivity(), dataSet);
            mLayoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }
        else
            {

            //getting the id from the others Activities
            //in case of two pane
            if (RecipesActivity.mTwoPane == true && getArguments() != null) {

                id = getArguments().getString("id");

            }

            // in case of single pane
            else if (RecipesActivity.mTwoPane == false && id == null) {
                id = getActivity().getIntent().getStringExtra("id");
            } else {
                //showing default recipes
                id = "2";
            }

            showIngredients(Constants.LINK);
            adapter = new IngredientsAdapter(getActivity(), dataSet);
            mLayoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }


        //related to clicking on fab
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (RecipesActivity.mTwoPane == true) {

                    StepsFragment stepsFragment = new StepsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    stepsFragment.setArguments(bundle);

                    getFragmentManager().beginTransaction().
                            replace(R.id.IngredientsFragmentId, stepsFragment).commit();
                }
                else
                {
//                    Toast.makeText(getActivity(), "the steps", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), StepsActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("dataset", dataSet);
        outState.putString("id", id);

        super.onSaveInstanceState(outState);
    }


    private void showIngredients(String link) {

        dataSet.clear();
        StringRequest stringRequest = new StringRequest(link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray Result = new JSONArray(response);
                    JSONObject object = Result.getJSONObject(Integer.parseInt(id) - 1);
                    JSONArray ingredients = object.getJSONArray("ingredients");
                    for (int j = 0; j < ingredients.length(); j++) {
                        JSONObject obj = ingredients.getJSONObject(j);

                        String quantity = obj.getString("quantity");
                        String measure = obj.getString("measure");
                        String ingredient = obj.getString("ingredient");

                        dataSet.add(new Ingredients(quantity, measure, ingredient));
                        adapter.notifyDataSetChanged();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getBaseContext());
        requestQueue.add(stringRequest);

    }

}
