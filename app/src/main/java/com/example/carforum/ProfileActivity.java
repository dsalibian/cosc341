package com.example.carforum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileName;
    private TextView reputationScore;
    private ImageButton backButton;

    private static final String PREFS_NAME = "QuizPrefs";
    private static final String HIGH_SCORE_KEY = "highScore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profile_name);
        reputationScore = findViewById(R.id.reputation_score);
        backButton = findViewById(R.id.back_button);

        profileName.setText(UserSession.getCurrentUser());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.postFooter).setOnClickListener(v -> openMyPosts());

        calculateAndSetReputation();
    }

    private void calculateAndSetReputation() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int highScore = prefs.getInt(HIGH_SCORE_KEY, 0);

        int totalUpvotes = 0;
        String currentUser = UserSession.getCurrentUser();
        ArrayList<Post> allPosts = PostRepository.getAll();
        for (Post post : allPosts) {
            if (post.getAuthor().equals(currentUser)) {
                totalUpvotes += post.getScore();
            }
        }

        int reputation = highScore + totalUpvotes;
        reputationScore.setText("Reputation: " + reputation);

        int finalTotalUpvotes = totalUpvotes;
        reputationScore.setOnClickListener(v -> {
            String message = "Highscore: " + highScore + " + upvotes " + finalTotalUpvotes + " = " + reputation + " reputation";
            Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_LONG).show();
        });
    }

    private void openMyPosts() {
        startActivity(new Intent(ProfileActivity.this, MyPostsActivity.class));
    }
}
