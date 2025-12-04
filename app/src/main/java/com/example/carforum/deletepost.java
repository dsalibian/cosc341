package com.example.carforum;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class deletepost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deletepost);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //int postid = getIntent().getIntExtra("postidx", 0);

        TextView title = findViewById(R.id.title2);
        TextView content = findViewById(R.id.content);

        String postId = getIntent().getStringExtra("post_id");
        Post post = PostRepository.getById(postId);

        if (post == null) {
            Toast.makeText(this, "Post not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        title.setText(post.getTitle());
        content.setText(post.getContent());

        Button cancelbtn = findViewById(R.id.cancelbtn3);
        Button deletebtn = findViewById(R.id.deletebtn);

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean removed = PostRepository.delete(postId);
                if (removed) {
                    Toast.makeText(deletepost.this, "Post deleted", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });


    }
}
