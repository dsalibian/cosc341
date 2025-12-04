package com.example.carforum;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class editpost extends AppCompatActivity {

    private Post post;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editpost);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // maybe use this to locate the post we want to edit in a global list?
        //int postid = getIntent().getIntExtra("postidx", 0);

        TextInputEditText input_title   = findViewById(R.id.titleinput);
        TextInputEditText input_tags    = findViewById(R.id.tagsinput);
        TextInputEditText input_content = findViewById(R.id.contentinput);

        postId = getIntent().getStringExtra("post_id");
        post = PostRepository.getById(postId);

        if (post == null) {
            Toast.makeText(this, "Post not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        input_title.setText(post.getTitle());
        input_tags.setText(String.join(", ", post.getTags()));
        input_content.setText(post.getContent());

        Button cancelbtn = findViewById(R.id.cancelbtn2);
        Button savebtn = findViewById(R.id.savebtn);

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title   = input_title.getText() != null ? input_title.getText().toString().trim() : "";
                String tagsRaw = input_tags.getText() != null ? input_tags.getText().toString().trim() : "";
                String content = input_content.getText() != null ? input_content.getText().toString().trim() : "";

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(editpost.this, "Title and content are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<String> tags = parseTags(tagsRaw);
                boolean updated = PostRepository.update(postId, title, tags, content);
                if (updated) {
                    Toast.makeText(editpost.this, "Post updated", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }

    private ArrayList<String> parseTags(String raw) {
        ArrayList<String> list = new ArrayList<>();
        if (raw == null || raw.isEmpty()) {
            return list;
        }
        for (String tag : raw.split(",")) {
            String cleaned = tag.trim();
            if (!cleaned.isEmpty()) {
                list.add(cleaned);
            }
        }
        return list;
    }
}
