package com.glsi.mesrecettes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManager {
    private static FirebaseAuth firebaseAuth;
    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference recipesReference;
    private static DatabaseReference usersReference;

    public static FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public static DatabaseReference getRecipesReference() {
        if (recipesReference == null) {
            firebaseDatabase = FirebaseDatabase.getInstance("https://mesrecettes-a44d6-default-rtdb.firebaseio.com"
            );
            recipesReference = firebaseDatabase.getReference("recipes");
        }
        return recipesReference;
    }
    public static DatabaseReference getUsersReference() {
        if (usersReference == null) {
            firebaseDatabase = FirebaseDatabase.getInstance("https://mesrecettes-a44d6-default-rtdb.firebaseio.com");
            usersReference = firebaseDatabase.getReference("users");
        }
        return usersReference;
    }
}
