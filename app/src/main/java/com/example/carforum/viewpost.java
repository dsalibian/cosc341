package com.example.carforum;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class viewpost extends AppCompatActivity {

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

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        LinearLayout commentsContainer = findViewById(R.id.commentlistlayout);

        // will need to fetch comments from the post we are viewing
        List<String> comments = new ArrayList<>();
        comments.add("sample comment1");
        comments.add("sample comment2");
        comments.add("sample comment3");

        for (String commentText : comments) {
            TextView commentView = new TextView(this);
            commentView.setText(commentText);
            commentView.setPadding(20, 8, 0, 8); // Add some vertical spacing
            commentsContainer.addView(commentView);
        }

    }
}