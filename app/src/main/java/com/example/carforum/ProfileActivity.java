package com.example.carforum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileNameView;
    private TextView profileSubtitle;
    private TextView highScoreValue;
    private ImageButton backButton;
    private ImageButton messageButton;
    private ImageButton reportButton;
    private android.widget.Button messageCta;
    private android.widget.Button reportCta;
    private android.view.View myPostsCard;
    private boolean isSelf;
    private String profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileNameView = findViewById(R.id.profile_name);
        profileSubtitle = findViewById(R.id.profile_subtitle);
        highScoreValue = findViewById(R.id.high_score_value);
        backButton = findViewById(R.id.back_button);
        messageButton = findViewById(R.id.message_button);
        reportButton = findViewById(R.id.report_button);
        messageCta = findViewById(R.id.message_cta);
        reportCta = findViewById(R.id.report_cta);
        myPostsCard = findViewById(R.id.my_posts_card);

        profileName = getIntent().getStringExtra("profile_name");
        if (profileName == null || profileName.trim().isEmpty()) {
            profileName = "Community Member";
        }

        isSelf = getIntent().getBooleanExtra("is_self", false);
        if (!isSelf && profileName.equalsIgnoreCase(UserSession.getCurrentUser())) {
            isSelf = true;
        }

        profileNameView.setText(profileName);
        profileSubtitle.setText("CarForum member");

        backButton.setOnClickListener(v -> finish());

        View.OnClickListener msgListener = v -> openMessageThread();
        View.OnClickListener reportListener = v -> reportUser();

        messageButton.setOnClickListener(msgListener);
        messageCta.setOnClickListener(msgListener);
        reportButton.setOnClickListener(reportListener);
        reportCta.setOnClickListener(reportListener);

        myPostsCard.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, MyPostsActivity.class)));

        if (isSelf) {
            hideMessaging();
            loadHighScore();
        } else {
            findViewById(R.id.high_score_card).setVisibility(View.GONE);
            myPostsCard.setVisibility(View.GONE);
        }
    }

    private void openMessageThread() {
        MessageRepository.ensureSeeded();
        MessageThread thread = MessageRepository.createOrGetThread(profileName);
        Intent intent = new Intent(ProfileActivity.this, MessagesActivity.class);
        intent.putExtra("thread_id", thread.getId());
        startActivity(intent);
    }

    private void reportUser() {
        new AlertDialog.Builder(this)
                .setTitle("Report " + profileName)
                .setMessage("Report this user for inappropriate content?")
                .setPositiveButton("Report", (dialog, which) ->
                        Toast.makeText(ProfileActivity.this, "Report submitted", Toast.LENGTH_SHORT).show())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void loadHighScore() {
        android.content.SharedPreferences prefs = getSharedPreferences("QuizPrefs", MODE_PRIVATE);
        int highScore = prefs.getInt("highScore", 0);
        highScoreValue.setText(String.valueOf(highScore));
    }

    private void hideMessaging() {
        int gone = View.GONE;
        messageButton.setVisibility(gone);
        reportButton.setVisibility(gone);
        messageCta.setVisibility(gone);
        reportCta.setVisibility(gone);
    }
}
