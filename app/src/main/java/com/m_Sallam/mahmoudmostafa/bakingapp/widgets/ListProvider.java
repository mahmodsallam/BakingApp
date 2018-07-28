package com.m_Sallam.mahmoudmostafa.bakingapp.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.m_Sallam.mahmoudmostafa.bakingapp.Activities.RecipesActivity;
import com.m_Sallam.mahmoudmostafa.bakingapp.Model.Ingredients;
import com.m_Sallam.mahmoudmostafa.bakingapp.R;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sallam on 2/4/2018.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {


    private ArrayList<Ingredients> ingredientsList = null;
    private Context context;
    private int appWidgetId;
    String id = null;

    public ListProvider(Context context, Intent intent, ArrayList<Ingredients> ingredientsList) {
        this.context = context;
        this.ingredientsList = ingredientsList;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    @Override
    public void onCreate()
    {
        ingredientsList = new ArrayList<>();
        ingredientsList.add(new Ingredients("5", "5", "ingredient"));
        ingredientsList = RecipesActivity.db.getAllIngredients() ;
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_list_row);
        Ingredients ingredient = ingredientsList.get(position);

        //fill the fields of the row
        remoteView.setTextViewText(R.id.quantity, "Quantity : " + ingredient.getQuantity());
        remoteView.setTextViewText(R.id.measure, "measure : " + ingredient.getMeasure());
        remoteView.setTextViewText(R.id.ingredient, "Ingredients : " + ingredient.getIngredient());

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }



}
