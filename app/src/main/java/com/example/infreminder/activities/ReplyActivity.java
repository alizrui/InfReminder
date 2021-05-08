package com.example.infreminder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.infreminder.R;

public class ReplyActivity extends AppCompatActivity {

    private TextView tvTitle;
    private EditText edReply;
    private ImageButton ibReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        String title = intent.getStringExtra("title");


        tvTitle = findViewById(R.id.tvTitleReply);
        edReply = findViewById(R.id.etReplyText);
        ibReply = findViewById(R.id.ibReply);

        tvTitle.setText(title);

        ibReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String replyText = edReply.getText().toString().trim();

                if (title.equals(replyText)) {

                    NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.cancel(id);

                    onBackPressed();
                }
            }
        });
    }
}