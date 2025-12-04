package com.example.carforum;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView resultsList;
    private EditText searchInput;
    private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        resultsList = findViewById(R.id.resultsList);
        searchInput = findViewById(R.id.searchInput);
        ImageButton backButton = findViewById(R.id.backButton);
        ImageButton filterButton = findViewById(R.id.filterButton);

        resultsList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostAdapter(PostRepository.getAll(), new PostAdapter.PostInteractionListener() {
            @Override
            public void onViewPost(String postId) {
                // forward to view page
                startActivity(new android.content.Intent(SearchActivity.this, viewpost.class)
                        .putExtra("post_id", postId));
            }

            @Override
            public void onEditPost(String postId) {
                startActivity(new android.content.Intent(SearchActivity.this, editpost.class)
                        .putExtra("post_id", postId));
            }

            @Override
            public void onDeletePost(String postId) {
                startActivity(new android.content.Intent(SearchActivity.this, deletepost.class)
                        .putExtra("post_id", postId));
            }
        });
        resultsList.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());
        filterButton.setOnClickListener(v -> Toast.makeText(this, "Filters coming soon", Toast.LENGTH_SHORT).show());

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.updateData(filterPosts(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    public void scrollToTop() {
        if (resultsList != null) {
            resultsList.smoothScrollToPosition(0);
        }
    }

    private ArrayList<Post> filterPosts(String query) {
        ArrayList<Post> posts = PostRepository.getAll();
        if (query == null || query.trim().isEmpty()) {
            return posts;
        }
        String lower = query.trim().toLowerCase();
        ArrayList<Post> filtered = new ArrayList<>();
        for (Post p : posts) {
            String tags = String.join(", ", p.getTags());
            if (p.getTitle().toLowerCase().contains(lower)
                    || p.getContent().toLowerCase().contains(lower)
                    || tags.toLowerCase().contains(lower)) {
                filtered.add(p);
            }
        }
        return filtered;
    }
}
