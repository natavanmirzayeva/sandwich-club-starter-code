package com.udacity.sandwichclub.utils;

import android.os.Build;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json)
    {

        try
        {
            JSONObject sandwichDetail = new JSONObject(json);
            JSONObject sandwichName = sandwichDetail.getJSONObject("name");

            JSONArray alsoknownAsArray = sandwichName.getJSONArray("alsoKnownAs");

            ArrayList<String> arrayList = new ArrayList<>();
            for(int i = 0;i<alsoknownAsArray.length();i++)
            {
                arrayList.add(alsoknownAsArray.getString(i));  // getting each also_known_as information from also known json array
            }

            JSONArray ingredientArray = sandwichDetail.getJSONArray("ingredients");
            ArrayList<String> ingredientArrayList = new ArrayList<>();
            for(int i = 0;i<ingredientArray.length();i++)
            {
                ingredientArrayList.add(ingredientArray.getString(i)); // getting each ingredient from ingredient json array
            }

            Sandwich sandwich = new Sandwich();
            sandwich.setMainName(sandwichName.getString("mainName"));
            sandwich.setAlsoKnownAs(arrayList);
            sandwich.setPlaceOfOrigin(sandwichDetail.getString("placeOfOrigin"));
            sandwich.setDescription(sandwichDetail.getString("description"));
            sandwich.setImage(sandwichDetail.getString("image"));
            sandwich.setIngredients(ingredientArrayList);
            return  sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
