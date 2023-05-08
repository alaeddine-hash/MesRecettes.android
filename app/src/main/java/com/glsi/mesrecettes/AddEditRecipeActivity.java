package com.glsi.mesrecettes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class AddEditRecipeActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText ingredientsEditText;
    private EditText instructionsEditText;
    private Button saveButton;

    private DatabaseReference recipesReference;
    private String userId;
    private Recipe existingRecipe;
    private boolean isEditMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_recipe);

        titleEditText = findViewById(R.id.edit_text_title);
        ingredientsEditText = findViewById(R.id.edit_text_ingredients);
        instructionsEditText = findViewById(R.id.edit_text_instructions);
        saveButton = findViewById(R.id.button_save);

        recipesReference = FirebaseManager.getRecipesReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Check if the intent has a "recipe" extra
        if (getIntent().hasExtra("recipe")) {
            isEditMode = true;
            existingRecipe = (Recipe) getIntent().getSerializableExtra("recipe");
            titleEditText.setText(existingRecipe.getTitle());
            ingredientsEditText.setText(existingRecipe.getIngredients());
            instructionsEditText.setText(existingRecipe.getInstructions());
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe();
            }
        });
    }

    private void saveRecipe() {
        String title = titleEditText.getText().toString().trim();
        String ingredients = ingredientsEditText.getText().toString().trim();
        String instructions = instructionsEditText.getText().toString().trim();

        if (title.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEditMode) {
            // Update the existing recipe
            String id = existingRecipe.getId();
            Recipe updatedRecipe = new Recipe(id, title, ingredients, instructions, userId);
            recipesReference.child(id).setValue(updatedRecipe);
            Toast.makeText(this, "Recette mise à jour", Toast.LENGTH_SHORT).show();
        } else {
            // Add a new recipe
            String id = recipesReference.push().getKey();
            Recipe newRecipe = new Recipe(id, title, ingredients, instructions, userId);
            assert id != null;
            recipesReference.child(id).setValue(newRecipe);
            Toast.makeText(this, "Recette enregistrée", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
