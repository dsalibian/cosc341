package com.example.carforum;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class viewpost extends AppCompatActivity {

    private String postId;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewpost);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //int postid = getIntent().getIntExtra("postidx", 0);

        Button backbtn = findViewById(R.id.backbtn);
        TextView titleView = findViewById(R.id.title3);
        TextView tagsView = findViewById(R.id.tags2);
        TextView contentView = findViewById(R.id.textView3);
        LinearLayout commentsContainer = findViewById(R.id.commentlistlayout);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        postId = getIntent().getStringExtra("post_id");
        post = PostRepository.getById(postId);

        if (post == null) {
            Toast.makeText(this, "Post not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        titleView.setText(post.getTitle());
        tagsView.setText(String.join(", ", post.getTags()));
        contentView.setText(post.getContent());

        List<String> comments = post.getComments();

        for (String commentText : comments) {
            TextView commentView = new TextView(this);
            commentView.setText(commentText);
            commentView.setPadding(20, 8, 0, 8); // Add some vertical spacing
            commentsContainer.addView(commentView);
        }

        if (comments.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("No comments yet");
            empty.setPadding(20, 8, 0, 8);
            commentsContainer.addView(empty);
        }

    }
}
