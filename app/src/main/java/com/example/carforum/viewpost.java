package com.example.carforum;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class viewpost extends AppCompatActivity {

    private Post post;
    private LinearLayout commentsContainer;
    private EditText commentInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpost);

        String postId = getIntent().getStringExtra("post_id");
        PostRepository.ensureSeeded();
        post = PostRepository.getById(postId);

        if (post == null) {
            Toast.makeText(this, "Post not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ImageButton back = findViewById(R.id.backbtn);
        TextView title = findViewById(R.id.title3);
        TextView smallTitle = findViewById(R.id.threadTitleSmall);
        TextView author = findViewById(R.id.postAuthor);
        TextView tags = findViewById(R.id.tags2);
        TextView content = findViewById(R.id.textView3);
        commentsContainer = findViewById(R.id.commentlistlayout);
        commentInput = findViewById(R.id.commentInput);
        Button postCommentBtn = findViewById(R.id.postCommentBtn);

        title.setText(post.getTitle());
        smallTitle.setText(post.getTitle());
        author.setText(post.getAuthor());
        tags.setText(TextUtils.join(", ", post.getTags()));
        content.setText(post.getContent());

        View.OnClickListener authorClick = v -> openProfile(post.getAuthor());
        author.setOnClickListener(authorClick);
        title.setOnClickListener(authorClick);

        back.setOnClickListener(v -> finish());

        postCommentBtn.setOnClickListener(v -> {
            String body = commentInput.getText() != null ? commentInput.getText().toString().trim() : "";
            if (body.isEmpty()) {
                Toast.makeText(viewpost.this, "Enter a comment", Toast.LENGTH_SHORT).show();
                return;
            }
            String commenter = UserSession.getCurrentUser();
            post.addComment(commenter, body);
            commentInput.setText("");
            renderComments();
            Toast.makeText(viewpost.this, "Comment added", Toast.LENGTH_SHORT).show();
        });

        renderComments();
    }

    private void renderComments() {
        commentsContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Comment c : post.getComments()) {
            View item = inflater.inflate(R.layout.item_comment, commentsContainer, false);
            TextView author = item.findViewById(R.id.commentAuthor);
            TextView body = item.findViewById(R.id.commentBody);

            author.setText(c.getAuthor());
            body.setText(c.getBody());

            View.OnClickListener profileClick = v -> openProfile(c.getAuthor());
            author.setOnClickListener(profileClick);
            item.setOnClickListener(profileClick);

            commentsContainer.addView(item);
        }
    }

    private void openProfile(String username) {
        if (username == null) {
            username = "Unknown";
        }
        startActivity(new android.content.Intent(this, ProfileActivity.class)
                .putExtra("profile_name", username));
    }
}
