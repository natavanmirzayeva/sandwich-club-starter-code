package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView alsoKnownAs,placeOfOrigin,description,ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        alsoKnownAs = findViewById(R.id.also_known_tv);
        placeOfOrigin = findViewById(R.id.origin_tv);
        description = findViewById(R.id.description_tv);
        ingredients = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }


            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }


            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich)
    {
        StringBuilder alsoknown = new StringBuilder();

        if(sandwich.getAlsoKnownAs().size() == 1)
        {
            alsoknown.append(sandwich.getAlsoKnownAs().get(0));
        }
        else
        {
            for(int i = 0;i<sandwich.getAlsoKnownAs().size();i++)
            {
                if(sandwich.getAlsoKnownAs().get(sandwich.getAlsoKnownAs().size()-1) == sandwich.getAlsoKnownAs().get(i))
                {
                    alsoknown.append(sandwich.getAlsoKnownAs().get(i) + ".");
                    break;
                }
                alsoknown.append(sandwich.getAlsoKnownAs().get(i) + ",");
            }
        }

        StringBuilder ingredients = new StringBuilder();

        if(sandwich.getIngredients().size() == 1)
        {
            ingredients.append(sandwich.getIngredients().get(0).toString());
        }
        else
        {
            for(int i = 0;i<sandwich.getIngredients().size();i++)
            {
                if(sandwich.getIngredients().get(sandwich.getIngredients().size()-1) == sandwich.getIngredients().get(i))
                {
                    ingredients.append(sandwich.getIngredients().get(i).toString() + ".");
                    break;
                }
                ingredients.append(sandwich.getIngredients().get(i).toString() + ",");
            }
        }

        alsoKnownAs.setText(alsoknown.toString());
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin().toString());
        description.setText(sandwich.getDescription().toString());
        this.ingredients.setText(ingredients.toString());
    }
}
