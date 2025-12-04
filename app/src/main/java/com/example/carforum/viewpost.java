package com.example.carforum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.EditText;
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

        ImageButton backbtn = findViewById(R.id.backbtn);
        TextView titleView = findViewById(R.id.title3);
        TextView tagsView = findViewById(R.id.tags2);
        TextView contentView = findViewById(R.id.textView3);
        TextView metaAuthor = findViewById(R.id.metaAuthor);
        LinearLayout commentsContainer = findViewById(R.id.commentlistlayout);
        EditText commentInput = findViewById(R.id.commentInput);
        ImageButton sendComment = findViewById(R.id.sendComment);

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
        metaAuthor.setText("u/" + post.getAuthor());

        List<String> comments = post.getComments();

        String[] demoAuthors = new String[] {"Alex", "MechanicMike", "CarEnthusiast92", "JohnDoe", "NewInTown"};
        int idx = 0;
        LayoutInflater inflater = LayoutInflater.from(this);
        for (String commentText : comments) {
            String commenter = demoAuthors[idx % demoAuthors.length];
            idx++;
            addCommentView(inflater, commentsContainer, commenter, commentText);
        }

        if (comments.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("No comments yet");
            empty.setPadding(20, 8, 0, 8);
            commentsContainer.addView(empty);
        }

        sendComment.setOnClickListener(v -> {
            String newComment = commentInput.getText() != null ? commentInput.getText().toString().trim() : "";
            if (newComment.isEmpty()) {
                Toast.makeText(this, "Type a comment first", Toast.LENGTH_SHORT).show();
                return;
            }
            String currentUser = UserSession.getCurrentUser();
            post.addComment(newComment);
            if (commentsContainer.getChildCount() == 1 && commentsContainer.getChildAt(0) instanceof TextView) {
                TextView tv = (TextView) commentsContainer.getChildAt(0);
                if ("No comments yet".contentEquals(tv.getText())) {
                    commentsContainer.removeAllViews();
                }
            }
            addCommentView(inflater, commentsContainer, currentUser, newComment);
            commentInput.setText("");
            commentsContainer.post(() -> commentsContainer.getParent().requestChildFocus(commentsContainer, commentsContainer));
        });

    }

    private void openProfile(String username) {
        startActivity(new android.content.Intent(this, ProfileActivity.class)
                .putExtra("username", username));
    }

    private void addCommentView(LayoutInflater inflater, LinearLayout container, String commenter, String text) {
        android.view.View item = inflater.inflate(R.layout.item_comment, container, false);
        TextView authorView = item.findViewById(R.id.commentAuthor);
        TextView bodyView = item.findViewById(R.id.commentBody);
        authorView.setText("u/" + commenter);
        bodyView.setText(text);
        authorView.setOnClickListener(v -> openProfile(commenter));
        bodyView.setOnClickListener(v -> openProfile(commenter));
        container.addView(item);
    }
}
