package com.m_Sallam.mahmoudmostafa.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.m_Sallam.mahmoudmostafa.bakingapp.Activities.IngredientsActivity;
import com.m_Sallam.mahmoudmostafa.bakingapp.Activities.RecipesActivity;
import com.m_Sallam.mahmoudmostafa.bakingapp.Model.Recipe;
import com.m_Sallam.mahmoudmostafa.bakingapp.R;
import com.m_Sallam.mahmoudmostafa.bakingapp.fragments.IngredientsFragment;

import java.util.ArrayList;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Recipe> recipes;
    FragmentManager fragmentManager;

    public RecipesAdapter(Context context, ArrayList<Recipe> recipes,
                          FragmentManager fragmentManager) {
        this.context = context;
        this.recipes = recipes;
        this.fragmentManager = fragmentManager;

    }

    public RecipesAdapter(Context context, ArrayList<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView RecipeName;
        ImageView RecipeImage;

        public MyViewHolder(View itemView, FragmentManager fragmentManager) {
            super(itemView);

            RecipeName = (TextView) itemView.findViewById(R.id.RecipeName);
            RecipeImage = (ImageView) itemView.findViewById(R.id.RecipeImage);
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "sweet.ttf");
            RecipeName.setTypeface(typeface);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_row, parent, false);
        return (new MyViewHolder(view, fragmentManager));

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Recipe recipe = recipes.get(position);
        holder.RecipeName.setText(recipe.getName());
        if (recipe.getImage() != null) {
            Glide.with(context).load(recipe.getImage()).placeholder(R.drawable.item_bg).into(holder.RecipeImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (RecipesActivity.mTwoPane == true) {
                    Bundle arguments = new Bundle();
                    arguments.putString("id", recipe.getId());


                    IngredientsFragment ingredientsFragment = new IngredientsFragment();
                    ingredientsFragment.setArguments(arguments);
                    fragmentManager.beginTransaction().
                            replace(R.id.IngredientsFragmentId, ingredientsFragment)
                            .commit();

                    if(RecipesActivity.IngredientsFragmentContainer.getVisibility() == View.GONE) {
                        RecipesActivity.IngredientsFragmentContainer.setVisibility(View.VISIBLE);
                    }

                } else {
                    Intent intent = new Intent(context, IngredientsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id", recipe.getId());
                    context.startActivity(intent);
                }


                //storing the recipe id in shared preference
                SharedPreferences.Editor editor =
                        context.getSharedPreferences("recipeId" , Context.MODE_PRIVATE) .edit() ;
                editor.putString("id"  , recipe.getId()) ;
                editor.apply();


            }
        });


    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }


}
