package com.example.carforum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;

public class HomepageActivity extends AppCompatActivity {

    RecyclerView postList;
    FloatingActionButton addPostButton;
    ImageButton backButton;
    ImageButton iconProfile, iconSearch, iconQuiz, iconAlerts, iconMessages;
    TabLayout homeTabs;
    android.widget.EditText searchHomeInput;
    PostAdapter postAdapter;
    private String currentQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        postList = findViewById(R.id.postList);
        addPostButton = findViewById(R.id.addPostButton);
        backButton = findViewById(R.id.backButton);
        iconProfile = findViewById(R.id.iconProfile);
        iconSearch = findViewById(R.id.iconSearch);
        iconQuiz = findViewById(R.id.iconQuiz);
        iconAlerts = findViewById(R.id.iconAlerts);
        iconMessages = findViewById(R.id.iconMessages);
        homeTabs = findViewById(R.id.homeTabs);
        searchHomeInput = findViewById(R.id.searchHomeInput);

        PostRepository.ensureSeeded();

        postList.setLayoutManager(new LinearLayoutManager(this));

        postAdapter = new PostAdapter(new ArrayList<>(), new PostAdapter.PostInteractionListener() {
            @Override
            public void onViewPost(String postId) {
                Intent intent = new Intent(HomepageActivity.this, viewpost.class);
                intent.putExtra("post_id", postId);
                startActivity(intent);
            }

            @Override
            public void onEditPost(String postId) {
                // Not used in this activity
            }

            @Override
            public void onDeletePost(String postId) {
                // Not used in this activity
            }

            @Override
            public void onUpvote(Post post) {
                if (post != null) {
                    post.upvote();
                }
                refreshPosts();
            }

            @Override
            public void onDownvote(Post post) {
                if (post != null) {
                    post.downvote();
                }
                refreshPosts();
            }
        }, true);

        postList.setAdapter(postAdapter);

        addPostButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, createpost.class);
            startActivity(intent);
        });

        backButton.setOnClickListener(v -> finish());

        homeTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { refreshPosts(); }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        iconProfile.setOnClickListener(v -> openProfile());
        iconSearch.setOnClickListener(v -> openSearch());
        iconQuiz.setOnClickListener(v -> openQuiz());
        iconAlerts.setOnClickListener(v -> openNotifications());
        iconMessages.setOnClickListener(v -> openMessages());

        searchHomeInput.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentQuery = s.toString();
                refreshPosts();
            }

            @Override
            public void afterTextChanged(android.text.Editable s) { }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshPosts();
    }

    private void refreshPosts() {
        postAdapter.updateData(getPosts(currentQuery));
    }

    private ArrayList<Post> getPosts(String query) {
        ArrayList<Post> posts = PostRepository.getAll();
        String lower = query == null ? "" : query.trim().toLowerCase();

        // 1. Filter posts based on the search query
        ArrayList<Post> filtered = new ArrayList<>();
        if (lower.isEmpty()) {
            filtered.addAll(posts);
        } else {
            for (Post p : posts) {
                String tags = String.join(", ", p.getTags());
                if (p.getTitle().toLowerCase().contains(lower)
                        || p.getContent().toLowerCase().contains(lower)
                        || tags.toLowerCase().contains(lower)) {
                    filtered.add(p);
                }
            }
        }

        // 2. Sort posts based on the selected tab
        int selectedTabPosition = homeTabs.getSelectedTabPosition();
        TabLayout.Tab selectedTab = homeTabs.getTabAt(selectedTabPosition);
        if (selectedTab != null && selectedTab.getText() != null) {
            String selectedTabText = selectedTab.getText().toString();
            if ("Popular".equalsIgnoreCase(selectedTabText)) {
                Collections.sort(filtered, (p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
            } else {
                // Default to chronological order for "Home"
                Collections.reverse(filtered); // Most recent first
            }
        } else {
            // Default sorting if tab is not available
            Collections.reverse(filtered);
        }
        return filtered;
    }

    public void scrollToTop() {
        if (postList != null) {
            postList.smoothScrollToPosition(0);
        }
    }

    private void openProfile() {
        startActivity(new Intent(HomepageActivity.this, ProfileActivity.class));
    }

    private void openSearch() {
        startActivity(new Intent(HomepageActivity.this, SearchActivity.class));
    }

    private void openQuiz() {
        startActivity(new Intent(HomepageActivity.this, QuizActivity.class));
    }

    private void openMessages() {
        startActivity(new Intent(HomepageActivity.this, MessagesListActivity.class));
    }

    private void openNotifications() {
        startActivity(new Intent(HomepageActivity.this, NotificationsActivity.class));
    }
}
