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

public class msgsoverview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_msgsoverview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.backbtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        LinearLayout msglayout = findViewById(R.id.msgslayout);

        // temporary
        String[] msgs = {"sample message 1", "sample message 2", "sample message 3"};

        for (int i = 0; i < msgs.length; i++) {
            TextView messageView = new TextView(this);
            messageView.setText(msgs[i]);
            messageView.setTextSize(18f);
            messageView.setPadding(40, 30, 40, 30); // L, T, R, B padding

            final int idx = i;
            messageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(msgsoverview.this, idx, Toast.LENGTH_SHORT).show();
                }
            });

            msglayout.addView(messageView);
        }

    }
}