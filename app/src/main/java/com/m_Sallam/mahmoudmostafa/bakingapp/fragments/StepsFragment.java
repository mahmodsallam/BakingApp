package com.m_Sallam.mahmoudmostafa.bakingapp.fragments;

import android.content.Intent;
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
import com.m_Sallam.mahmoudmostafa.bakingapp.Activities.RecipesActivity;
import com.m_Sallam.mahmoudmostafa.bakingapp.Adapters.StepsAdapter;
import com.m_Sallam.mahmoudmostafa.bakingapp.Constants.Constants;
import com.m_Sallam.mahmoudmostafa.bakingapp.Model.Steps;
import com.m_Sallam.mahmoudmostafa.bakingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class StepsFragment extends android.support.v4.app.Fragment {
    public StepsFragment() {

    }

    String id;
    RecyclerView recyclerView;
    StepsAdapter adapter;
    ArrayList<Steps> dataSet;
    RecyclerView.LayoutManager mLayoutManager;
    FragmentManager fragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (RecipesActivity.mTwoPane == true && getArguments() != null) {
            id = getArguments().getString("id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.steps_fragment, container, false);
        dataSet = new ArrayList<>();
        if (RecipesActivity.mTwoPane == false) {
            Intent intent = getActivity().getIntent();
            id = intent.getStringExtra("id");
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.StepsRecyclerView);

        if (savedInstanceState != null) {
            dataSet = savedInstanceState.getParcelableArrayList("dataset");
            adapter = new StepsAdapter(getActivity(), dataSet, fragmentManager);
            mLayoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        } else {

            showIngredients(Constants.LINK);

            adapter = new StepsAdapter(getActivity(), dataSet, fragmentManager);
            mLayoutManager = new GridLayoutManager(getActivity(), 1);
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

    private void showIngredients(String link) {
        dataSet.clear();

        StringRequest stringRequest = new StringRequest(link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray Result = new JSONArray(response);
                    JSONObject object = Result.getJSONObject(Integer.parseInt(id) - 1);

                    JSONArray steps = object.getJSONArray("steps");
                    for (int j = 0; j < steps.length(); j++) {
                        JSONObject obj = steps.getJSONObject(j);

                        String id = obj.getString("id");
                        String shortDescription = obj.getString("shortDescription");
                        String description = obj.getString("description");
                        String Video = obj.getString("videoURL");
                        String thumbnailUrl = obj.getString("thumbnailURL");

                        dataSet.add(new Steps(id, shortDescription, Video, thumbnailUrl, description));
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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

}
