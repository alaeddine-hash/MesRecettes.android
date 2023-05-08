package com.glsi.mesrecettes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private Button manageUsersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Button logoutButton = findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        manageUsersButton = findViewById(R.id.button_manage_users);
        manageUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UsersActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {
            checkIfUserIsAdmin(currentUser.getUid());

            // Initialize the RecyclerView, Adapter and fetch recipes from Firebase
            recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

            recipeAdapter = new RecipeAdapter(this);
            recyclerView.setAdapter(recipeAdapter);

            String currentUserId = currentUser.getUid();

            DatabaseReference recipesReference = FirebaseManager.getRecipesReference();
            recipesReference.orderByChild("userId").equalTo(currentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Recipe> recipeList = new ArrayList<>();
                    for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                        Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                        recipeList.add(recipe);
                    }
                    recipeAdapter.setRecipes(recipeList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
                }
            });

            FloatingActionButton fabAddRecipe = findViewById(R.id.fab_add_recipe);
            fabAddRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AddEditRecipeActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void checkIfUserIsAdmin(String userId) {
        DatabaseReference usersReference = FirebaseManager.getUsersReference();
        usersReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null && "admin".equals(user.getRole())) {
                    manageUsersButton.setVisibility(View.VISIBLE);
                } else {
                    manageUsersButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error checking admin status", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }


}

