package com.example.carforum;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ImageButton backButton = findViewById(R.id.backButton);
        RecyclerView list = findViewById(R.id.notificationsList);
        TextView count = findViewById(R.id.notificationsCount);

        backButton.setOnClickListener(v -> finish());

        ArrayList<NotificationItem> seed = new ArrayList<>();
        seed.add(new NotificationItem("MechanicMike replied to your post", "\"What car should I buy?\"", "2 hours ago", R.drawable.ic_reply, 0xFF1D4ED8));
        seed.add(new NotificationItem("CarEnthusiast92 liked your post", "\"Best compact cars of the year\"", "4 hours ago", R.drawable.ic_heart, 0xFF2563EB));
        seed.add(new NotificationItem("JohnDoe mentioned you in", "\"DIY: How to change car oil\"", "6 hours ago", R.drawable.ic_chat, 0xFF1D4ED8));
        seed.add(new NotificationItem("NewInTown started following you", "", "1 day ago", R.drawable.ic_person_add, 0xFF7C3AED));

        count.setText(seed.size() + " new");

        NotificationAdapter adapter = new NotificationAdapter(seed);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }
}
