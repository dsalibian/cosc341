package com.example.carforum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button joinButton, loginButton;
    TextView exploreLink;
    android.widget.EditText emailInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        joinButton = findViewById(R.id.joinButton);
        exploreLink = findViewById(R.id.exploreLink);
        loginButton = findViewById(R.id.loginButton);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);

        joinButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        });

        exploreLink.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, HomepageActivity.class));
        });

        loginButton.setOnClickListener(v -> attemptLogin());
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
}
