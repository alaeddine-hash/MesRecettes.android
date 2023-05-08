package com.glsi.mesrecettes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {

    private User user;
    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewRole;
    private Button buttonEditUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        textViewName = findViewById(R.id.text_view_name);
        textViewEmail = findViewById(R.id.text_view_email);
        textViewRole = findViewById(R.id.text_view_role);
        buttonEditUser = findViewById(R.id.button_edit_user);

        textViewName.setText(user.getName());
        textViewEmail.setText(user.getEmail());
        textViewRole.setText(user.getRole());

        buttonEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to EditUserActivity
                Intent editIntent = new Intent(UserProfileActivity.this, EditUserActivity.class);
                editIntent.putExtra("user", user);
                startActivity(editIntent);
            }
        });
    }
}