package com.example.carforum;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.text.TextUtils;

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

        TextInputEditText input_title = findViewById(R.id.titleinput);
        TextInputEditText input_tags = findViewById(R.id.tagsinput);
        TextInputEditText input_content = findViewById(R.id.contentinput);

        Button cancelbtn = findViewById(R.id.cancelbtn);
        Button postbtn = findViewById(R.id.postbtn);

        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title   = input_title.getText() != null ? input_title.getText().toString().trim() : "";
                String tags    = input_tags.getText() != null ? input_tags.getText().toString().trim() : "";
                String content = input_content.getText() != null ? input_content.getText().toString().trim() : "";

                if (title.isEmpty() || tags.isEmpty() || content.isEmpty()) {
                    showmsg("empty fields");
                    return;
                }

                ArrayList<String> parsedTags = parseTags(tags);
                Post p = new Post(title, UserSession.getCurrentUser(), content);
                p.setTags(parsedTags);

                PostRepository.add(p);
                showmsg("post created");
                finish();
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });
    }

    private ArrayList<String> parseTags(String raw) {
        ArrayList<String> list = new ArrayList<>();
        if (raw == null || raw.isEmpty()) {
            return list;
        }
        for (String tag : raw.split(",")) {
            String cleaned = tag.trim();
            if (!TextUtils.isEmpty(cleaned)) {
                list.add(cleaned);
            }
        }
        return list;
    }
}
