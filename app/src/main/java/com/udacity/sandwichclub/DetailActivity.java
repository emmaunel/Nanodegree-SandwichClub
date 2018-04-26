package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = "DetailActivity";
    private Sandwich sandwich = null;
    private TextView ingredientsTv, description, placeoforgin, alsoKnownAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        description = findViewById(R.id.description_tv);
        placeoforgin = findViewById(R.id.origin_tv);
        alsoKnownAs = findViewById(R.id.also_known_tv);

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
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "onCreate: "+ e.toString());
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        List<String> akaList = sandwich.getAlsoKnownAs();
        String aka = "";
        for (int i = 0; i < akaList.size(); i++) {
            aka += ", ";
            aka += akaList.get(i);
        }
        alsoKnownAs.setText(aka);


        List<String> ingredientList = sandwich.getIngredients();
        String ingredients = "";
        for (int i=0; i<ingredientList.size(); i++) {
            if (i > 0) {
                ingredients += ", ";
            }
            ingredients += ingredientList.get(i);
        }
        ingredientsTv.setText(ingredients);

        description.setText(sandwich.getDescription());
        placeoforgin.setText(sandwich.getPlaceOfOrigin());
    }
}