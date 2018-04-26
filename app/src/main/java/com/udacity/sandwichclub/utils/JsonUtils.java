package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject object = new JSONObject(json);

        JSONObject subObj = object.getJSONObject("name");

        //get Main Name
        String mainName = subObj.getString("mainName");

        //get alsoKnownAs
        JSONArray alternateName = subObj.getJSONArray("alsoKnownAs");
        ArrayList<String> alternateNameList = new ArrayList<>();
        for (int i = 0; i < alternateName.length(); i++) {
            String altName = alternateName.getString(i);
            alternateNameList.add(altName);
        }

        //get placeOfOrigin
        String placeOfOrigin = object.getString("placeOfOrigin");


        //get Description
        String description = object.getString("description");

        //get image
        String imageUrl = object.getString("image");

        //get ingredients list
        JSONArray ingredientsArray = object.getJSONArray("ingredients");
        ArrayList<String> ingredients = new ArrayList<>();

        //loop throught the array
        for (int i = 0; i < ingredientsArray.length(); i++) {
            String ingredientsName = ingredientsArray.getString(i);
            ingredients.add(ingredientsName);
        }


        Log.i(TAG, "parseSandwichJson MainName: " + mainName);
        Log.i(TAG, "parseSandwichJson MainName: " + alternateNameList);
        Log.i(TAG, "parseSandwichJson MainName: " + placeOfOrigin);
        Log.i(TAG, "parseSandwichJson MainName: " + description);
        Log.i(TAG, "parseSandwichJson MainName: " + imageUrl);
        Log.i(TAG, "parseSandwichJson MainName: " + ingredients);

        Sandwich deliciousSandwich = new Sandwich(mainName,alternateNameList,placeOfOrigin,description,imageUrl,ingredients);
        return deliciousSandwich;
    }
}

