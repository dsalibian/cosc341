package com.example.carforum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPostsActivity extends AppCompatActivity {

    private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        ImageButton backButton = findViewById(R.id.backButton);
        RecyclerView list = findViewById(R.id.myPostsList);
        TextView emptyView = findViewById(R.id.emptyState);

        backButton.setOnClickListener(v -> finish());

        adapter = new PostAdapter(new ArrayList<>(), new PostAdapter.PostInteractionListener() {
            @Override
            public void onViewPost(String postId) {
                startActivity(new Intent(MyPostsActivity.this, viewpost.class).putExtra("post_id", postId));
            }

            @Override
            public void onEditPost(String postId) {
                startActivity(new Intent(MyPostsActivity.this, editpost.class).putExtra("post_id", postId));
            }

            @Override
            public void onDeletePost(String postId) {
                startActivity(new Intent(MyPostsActivity.this, deletepost.class).putExtra("post_id", postId));
            }

            @Override
            public void onUpvote(Post post) {
                // Not used in this activity
            }

            @Override
            public void onDownvote(Post post) {
                // Not used in this activity
            }
        }, false);

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        refresh(emptyView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView emptyView = findViewById(R.id.emptyState);
        refresh(emptyView);
    }

    private void refresh(TextView emptyView) {
        ArrayList<Post> mine = new ArrayList<>();
        String user = UserSession.getCurrentUser();
        for (Post p : PostRepository.getAll()) {
            if (p.getAuthor().equalsIgnoreCase(user)) {
                mine.add(p);
            }
        }
        adapter.updateData(mine);
        emptyView.setVisibility(mine.isEmpty() ? android.view.View.VISIBLE : android.view.View.GONE);
    }
}
