package com.m_Sallam.mahmoudmostafa.bakingapp.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.m_Sallam.mahmoudmostafa.bakingapp.Model.Ingredients;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sallam on 2/4/2018.
 */

public class WidgetService extends RemoteViewsService {


    private ArrayList<Ingredients> ingredientsList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

            int appWidgetId = intent.getIntExtra
                    (
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID
                    );


//        ingredientsList = new ArrayList<>();
//        ingredientsList.add(new Ingredients("5", "5", "ingredient"));
//        ingredientsList.add(new Ingredients("5", "5", "ingredient"));
//        ingredientsList.add(new Ingredients("5", "5", "ingredient"));
//        ingredientsList.add(new Ingredients("5", "5", "ingredient"));
//        ingredientsList.add(new Ingredients("5", "5", "ingredient"));
//        ingredientsList.add(new Ingredients("5", "5", "ingredient"));
//        ingredientsList.add(new Ingredients("5", "5", "ingredient"));

        return (new ListProvider(this.getApplicationContext(), intent, ingredientsList));
    }



}
