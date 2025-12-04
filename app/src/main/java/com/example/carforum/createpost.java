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

public class createpost extends AppCompatActivity {

    void showmsg(String s) { Toast.makeText(createpost.this, s, Toast.LENGTH_SHORT).show(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_createpost);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<String> tags = new ArrayList<>();
        ArrayList<String> comments = new ArrayList<>();

        TextInputEditText input_title = findViewById(R.id.titleinput);
        TextInputEditText input_tags = findViewById(R.id.tagsinput);
        TextInputEditText input_content = findViewById(R.id.contentinput);

        findViewById(R.id.postbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title   = input_title.getText().toString().trim();
                String tags    = input_tags.getText().toString().trim();
                String content = input_content.getText().toString().trim();

                if (title.isEmpty() || tags.isEmpty() || content.isEmpty()) {
                    showmsg("empty fields");
                    return;
                }

                Post p = new Post(title, "temp", content);

                showmsg("post created");

                // handle post creation and change author field to current user
                // also parse the tags correctly, one entry per comma
                throw new RuntimeException("implement post creation");

                //finish();
            }
        });

        findViewById(R.id.cancelbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });
    }
}