package com.m_Sallam.mahmoudmostafa.bakingapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sallam on 2/8/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "recipeIngredients";

    // Contacts table name
    private static final String TABLE_INGREDIENT = "ingredients";


    // ingredients  Table Columns names
    private static final String QUANTITY = "quantity";
    private static final String MEASURE = "measure ";
    private static final String INGREDIENTS = "ingredients";
    Context  context ;
    //constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        Toast.makeText(context, "Database created ", Toast.LENGTH_LONG).show();

        this.context = context ;
    }

    //creating table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + TABLE_INGREDIENT + "("
                + QUANTITY + " TEXT primary key ," + MEASURE + " TEXT,"
                + INGREDIENTS + " TEXT   " + ")";
        db.execSQL(CREATE_INGREDIENTS_TABLE);

    }

    //upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);

        // Create tables again
        onCreate(db);

    }


    //adding new ingredient method
    public void addIngredient(Ingredients ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(QUANTITY, ingredient.getQuantity()); // Contact Name
        values.put(MEASURE, ingredient.getMeasure()); // Contact Phone Number
        values.put(INGREDIENTS, ingredient.getIngredient());

        // Inserting Row
        db.insert(TABLE_INGREDIENT, null, values);
        db.close(); // Closing database connection

//        Toast.makeText(context , "element inserted "  , Toast.LENGTH_SHORT).show();

    }


    //retrieving all the ingredients
    public ArrayList<Ingredients> getAllIngredients() {

        ArrayList<Ingredients> ingredientsList = new ArrayList<Ingredients>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INGREDIENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Ingredients ingredient;

                String quantity = cursor.getString(0);
                String measure = cursor.getString(1);
                String ing = cursor.getString(2);

                // Adding ingredients  to list
                ingredientsList.add(new Ingredients(quantity, measure, ing));

            } while (cursor.moveToNext());
        }

        // return contact list
        return ingredientsList;
    }


}
