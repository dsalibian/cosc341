package com.example.carforum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class editpost extends AppCompatActivity {

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

        // set these to the values in the actual post,
        // since we are editing an actual post
        input_title.setText("sample title");
        input_tags.setText("tag0, tag1, tag2");
        input_content.setText("sample post content");

        findViewById(R.id.cancelbtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        findViewById(R.id.savebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                throw new RuntimeException("finish editpost");
            }
        });

    }
}