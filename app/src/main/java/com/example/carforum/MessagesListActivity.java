package com.example.carforum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MessagesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_list);

        MessageRepository.ensureSeeded();

        RecyclerView convoList = findViewById(R.id.convoList);
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        ConversationAdapter adapter = new ConversationAdapter(
                MessageRepository.getThreads(),
                threadId -> {
                    Intent i = new Intent(MessagesListActivity.this, MessagesActivity.class);
                    i.putExtra("thread_id", threadId);
                    startActivity(i);
                }
        );
        convoList.setLayoutManager(new LinearLayoutManager(this));
        convoList.setAdapter(adapter);
    }
}
