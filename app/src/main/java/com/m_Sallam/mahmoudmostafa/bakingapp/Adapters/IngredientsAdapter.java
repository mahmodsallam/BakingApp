package com.m_Sallam.mahmoudmostafa.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m_Sallam.mahmoudmostafa.bakingapp.Model.Ingredients;
import com.m_Sallam.mahmoudmostafa.bakingapp.R;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Ingredients> ingredientsList;

    public IngredientsAdapter(Context context, ArrayList<Ingredients> ingredientsList) {
        this.context = context;
        this.ingredientsList = ingredientsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView quantity;
        TextView measure;
        TextView ingredient;

        public MyViewHolder(View itemView) {
            super(itemView);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            measure = (TextView) itemView.findViewById(R.id.measure);
            ingredient = (TextView) itemView.findViewById(R.id.ingredient);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_row, parent, false);
        return (new MyViewHolder(view));

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Ingredients ingredient = ingredientsList.get(position);
        holder.quantity.setText("Quantity : " + ingredient.getQuantity());
        holder.measure.setText("measure  : " + ingredient.getMeasure());
        holder.ingredient.setText("ingredient : " + ingredient.getIngredient());

    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }


}
