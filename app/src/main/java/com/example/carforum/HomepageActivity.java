package com.example.carforum;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomepageActivity extends AppCompatActivity {

    RecyclerView postList;
    ImageButton addPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        postList = findViewById(R.id.postList);
        addPostButton = findViewById(R.id.addPostButton);

        postList.setLayoutManager(new LinearLayoutManager(this));

        // TODO: attach adapter later for posts

        addPostButton.setOnClickListener(v -> {
            // TODO: Open CreatePostActivity
        });
    }
}
