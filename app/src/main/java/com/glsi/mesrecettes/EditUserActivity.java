package com.glsi.mesrecettes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditUserActivity extends AppCompatActivity {

    private User user;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextRole;
    private Button buttonSave;
    private DatabaseReference usersReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        usersReference = FirebaseDatabase.getInstance().getReference("users");

        editTextName = findViewById(R.id.edit_text_name);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextRole = findViewById(R.id.edit_text_role);
        buttonSave = findViewById(R.id.button_save);

        editTextName.setText(user.getName());
        editTextEmail.setText(user.getEmail());
        editTextRole.setText(user.getRole());

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the updated user information to Firebase
                // and return to UserProfileActivity
                user.setName(editTextName.getText().toString());
                user.setEmail(editTextEmail.getText().toString());
                user.setRole(editTextRole.getText().toString());

                usersReference.child(user.getId()).setValue(user, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                        if (error == null) {
                            Toast.makeText(EditUserActivity.this, "User updated successfully", Toast.LENGTH_SHORT).show();
                            Intent userProfileIntent = new Intent(EditUserActivity.this, UserProfileActivity.class);
                            userProfileIntent.putExtra("user", user);
                            startActivity(userProfileIntent);
                            finish();
                        } else {
                            Toast.makeText(EditUserActivity.this, "Error updating user", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}