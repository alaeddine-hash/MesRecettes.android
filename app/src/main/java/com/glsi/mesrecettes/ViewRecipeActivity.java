package com.glsi.mesrecettes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewRecipeActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView ingredientsTextView;
    private TextView instructionsTextView;
    private Button buttonEditRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        titleTextView = findViewById(R.id.text_view_title);
        ingredientsTextView = findViewById(R.id.text_view_ingredients);
        instructionsTextView = findViewById(R.id.text_view_instructions);
        buttonEditRecipe = findViewById(R.id.button_edit_recipe);

        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("recipe");

        titleTextView.setText(recipe.getTitle());
        ingredientsTextView.setText(recipe.getIngredients());
        instructionsTextView.setText(recipe.getInstructions());

        buttonEditRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRecipe(recipe);
            }
        });
    }

    private void editRecipe(Recipe recipe) {
        Intent intent = new Intent(ViewRecipeActivity.this, AddEditRecipeActivity.class);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }
}