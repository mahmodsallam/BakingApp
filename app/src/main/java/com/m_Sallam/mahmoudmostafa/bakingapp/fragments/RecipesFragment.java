package com.m_Sallam.mahmoudmostafa.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
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
import com.m_Sallam.mahmoudmostafa.bakingapp.Adapters.RecipesAdapter;
import com.m_Sallam.mahmoudmostafa.bakingapp.Constants.Constants;
import com.m_Sallam.mahmoudmostafa.bakingapp.Model.Recipe;
import com.m_Sallam.mahmoudmostafa.bakingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipesFragment extends android.support.v4.app.Fragment {
    ArrayList<Recipe> dataSet;
    RecipesAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    public RecipesFragment() {

    }

    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipes_fragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.RecipesRecyclerView);

        dataSet = new ArrayList<>();
        fragmentManager = getFragmentManager();

        if (savedInstanceState != null)
        {
            //there exists some thing in the array
            dataSet = savedInstanceState.getParcelableArrayList("dataset");
            //working with the recycler and its adapter
            adapter = new RecipesAdapter(getContext(), dataSet, fragmentManager);
            mLayoutManager = new GridLayoutManager(getContext(), 1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

        }
        else
            {
            showRecipes(Constants.LINK);
            adapter = new RecipesAdapter(getActivity().getBaseContext(), dataSet, fragmentManager);
            mLayoutManager = new GridLayoutManager(getActivity().getBaseContext(), 1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("dataset", dataSet);
        super.onSaveInstanceState(outState);
    }


    private void showRecipes(String link) {
        dataSet.clear();

        StringRequest stringRequest = new StringRequest(link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray Result = new JSONArray(response);

                    for (int i = 0; i < Result.length(); i++) {

                        JSONObject object = Result.getJSONObject(i);

                        String id = object.getString("id");
                        String Name = object.getString("name");
                        String image;
                        try {
                            image = object.getString("image");

                        } catch (Exception e) {
                            image = null;
                        }

                        dataSet.add(new Recipe(id, Name, image));
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
