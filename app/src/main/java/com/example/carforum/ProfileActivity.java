package com.example.carforum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carforum.MessageThread;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String username = getIntent().getStringExtra("username");
        if (username == null || username.isEmpty()) {
            username = "Community Member";
        }
        final String profileUser = username;

        TextView name = findViewById(R.id.profileName);
        TextView subtitle = findViewById(R.id.profileSubtitle);
        ImageButton backButton = findViewById(R.id.backButton);
        ImageButton messageButton = findViewById(R.id.messageButton);
        ImageButton reportButton = findViewById(R.id.reportButton);

        name.setText(profileUser);
        subtitle.setText("Joined: Enthusiast · 120 karma");

        backButton.setOnClickListener(v -> finish());

        messageButton.setOnClickListener(v -> {
            MessageRepository.ensureSeeded();
            MessageThread thread = MessageRepository.createOrGetThread(profileUser);
            Intent i = new Intent(ProfileActivity.this, MessagesActivity.class);
            i.putExtra("thread_id", thread.getId());
            startActivity(i);
        });

        reportButton.setOnClickListener(v ->
                Toast.makeText(this, "Reported " + profileUser + " to moderators", Toast.LENGTH_SHORT).show());
    }
}
