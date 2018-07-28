package com.m_Sallam.mahmoudmostafa.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.m_Sallam.mahmoudmostafa.bakingapp.Activities.RecipesActivity;
import com.m_Sallam.mahmoudmostafa.bakingapp.Activities.StepVideoActivity;
import com.m_Sallam.mahmoudmostafa.bakingapp.Model.Steps;
import com.m_Sallam.mahmoudmostafa.bakingapp.R;
import com.m_Sallam.mahmoudmostafa.bakingapp.fragments.StepVideoFragment;

import java.util.ArrayList;



public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Steps> StepsList;
    FragmentManager fragmentManager;

    public StepsAdapter(Context context, ArrayList<Steps> StepsList, FragmentManager fragmentManager) {
        this.context = context;
        this.StepsList = StepsList;
        this.fragmentManager = fragmentManager;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Description;
        TextView shortDescription;
        ImageView stepImage;

        public MyViewHolder(View itemView, FragmentManager fragmentManager) {
            super(itemView);


            Description = (TextView) itemView.findViewById(R.id.Description);
            shortDescription = (TextView) itemView.findViewById(R.id.shortDescription);
            stepImage = (ImageView) itemView.findViewById(R.id.stepImage);

            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "great.ttf");
            shortDescription.setTypeface(typeface);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_row, parent, false);
        return (new MyViewHolder(view, fragmentManager));

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Steps Step = StepsList.get(position);
        holder.Description.setText(Step.getDescription());
        holder.shortDescription.setText(Step.getShortDescription());
        Glide.with(context).load(Step.getThumbnailUrl()).into(holder.stepImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (RecipesActivity.mTwoPane == true) {
                    Bundle arguments = new Bundle();
                    arguments.putString("VideoUrl", Step.getVideoUrl());

                    StepVideoFragment videoFragment = new StepVideoFragment();
                    videoFragment.setArguments(arguments);

                    /*fragmentManager.beginTransaction().
                            replace(R.id.IngredientsFragmentId, videoFragment)
                            .commit();
*/
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.IngredientsFragmentId, videoFragment)
                            .commit();

                } else {
                    Intent intent = new Intent(context, StepVideoActivity.class);
                    intent.putExtra("VideoUrl", Step.getVideoUrl());
                    context.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return StepsList.size();
    }


}
