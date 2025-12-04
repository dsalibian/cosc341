package com.example.carforum;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessagesActivity extends AppCompatActivity {

    private RecyclerView convoList;
    private EditText input;
    private MessageAdapter adapter;
    private String threadId;
    private MessageThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        threadId = getIntent().getStringExtra("thread_id");
        if (threadId == null) {
            threadId = MessageRepository.createOrGetThread("New Contact").getId();
        }

        MessageRepository.ensureSeeded();
        thread = MessageRepository.getThreadById(threadId);
        if (thread == null) {
            thread = MessageRepository.createOrGetThread("New Contact");
            threadId = thread.getId();
        }

        convoList = findViewById(R.id.convoList);
        input = findViewById(R.id.messageInput);
        ImageButton sendButton = findViewById(R.id.sendButton);
        ImageButton backButton = findViewById(R.id.backButton);
        android.widget.TextView title = findViewById(R.id.messagesTitle);

        title.setText("Chat with " + thread.getContact());

        backButton.setOnClickListener(v -> finish());

        adapter = new MessageAdapter(thread.getMessages());
        convoList.setLayoutManager(new LinearLayoutManager(this));
        convoList.setAdapter(adapter);
        convoList.scrollToPosition(adapter.getItemCount() - 1);

        sendButton.setOnClickListener(v -> {
            String text = input.getText() != null ? input.getText().toString().trim() : "";
            if (text.isEmpty()) {
                Toast.makeText(this, "Enter a message", Toast.LENGTH_SHORT).show();
                return;
            }
            Message msg = new Message(UserSession.getCurrentUser(), text, true);
            MessageRepository.addMessage(threadId, msg);
            adapter.addMessage(msg);
            input.setText("");
            convoList.scrollToPosition(adapter.getItemCount() - 1);
        });
    }
}
