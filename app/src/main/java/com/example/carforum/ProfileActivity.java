package com.example.carforum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileName;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profile_name);
        backButton = findViewById(R.id.back_button);

        profileName.setText(UserSession.getCurrentUser());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.postFooter).setOnClickListener(v -> openMyPosts());
    }

    private void openMyPosts() {
        startActivity(new Intent(ProfileActivity.this, MyPostsActivity.class));
    }
}
