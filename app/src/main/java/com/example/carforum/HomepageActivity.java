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

        postAdapter = new PostAdapter(PostRepository.getAll(), new PostAdapter.PostInteractionListener() {
            @Override
            public void onViewPost(String postId) {
                Intent intent = new Intent(HomepageActivity.this, viewpost.class);
                intent.putExtra("post_id", postId);
                startActivity(intent);
            }

            @Override
            public void onEditPost(String postId) {
                Intent intent = new Intent(HomepageActivity.this, editpost.class);
                intent.putExtra("post_id", postId);
                startActivity(intent);
            }

            @Override
            public void onDeletePost(String postId) {
                Intent intent = new Intent(HomepageActivity.this, deletepost.class);
                intent.putExtra("post_id", postId);
                startActivity(intent);
            }
        });

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
        postAdapter.updateData(filterPosts(currentQuery));
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

    public void scrollToTop() {
        if (postList != null) {
            postList.smoothScrollToPosition(0);
        }
    }

    private void openProfile() {
        Intent intent = new Intent(HomepageActivity.this, ProfileActivity.class);
        intent.putExtra("profile_name", UserSession.getCurrentUser());
        intent.putExtra("is_self", true);
        startActivity(intent);
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

    private void openMyPosts() {
        startActivity(new Intent(HomepageActivity.this, MyPostsActivity.class));
    }
}
