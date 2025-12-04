package com.example.carforum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

public class LoginActivity extends AppCompatActivity {

    Button joinButton, loginButton;
    TextView exploreLink;
    android.widget.EditText emailInput, passwordInput;
    View featureSmart, featureReputation, featureRepair, featureModeration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        joinButton = findViewById(R.id.joinButton);
        exploreLink = findViewById(R.id.exploreLink);
        loginButton = findViewById(R.id.loginButton);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        featureSmart = findViewById(R.id.featureSmart);
        featureReputation = findViewById(R.id.featureReputation);
        featureRepair = findViewById(R.id.featureRepair);
        featureModeration = findViewById(R.id.featureModeration);

        joinButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        });

        exploreLink.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, HomepageActivity.class));
        });

        loginButton.setOnClickListener(v -> attemptLogin());

        featureSmart.setOnClickListener(v ->
                showFeatureDialog("Smart Search", "Surfaces the best threads, users, and categories for your query.")
        );
        featureReputation.setOnClickListener(v ->
                showFeatureDialog("Reputation System", "Highlights trusted contributors and quality answers.")
        );
        featureRepair.setOnClickListener(v ->
                showFeatureDialog("Repair Help", "Connects you to mechanics and DIY guides for your issues.")
        );
        featureModeration.setOnClickListener(v ->
                showFeatureDialog("Fair Moderation", "Keeps discussions safe and respectful for everyone.")
        );
    }

    private void attemptLogin() {
        String email = emailInput.getText() != null ? emailInput.getText().toString().trim() : "";
        String password = passwordInput.getText() != null ? passwordInput.getText().toString().trim() : "";

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Placeholder auth: accept any non-empty credentials
        UserSession.setCurrentUser(email);
        Toast.makeText(this, "Logged in as " + email, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, HomepageActivity.class));
        finish();
    }

    private void showFeatureDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Got it", null)
                .show();
    }
}
