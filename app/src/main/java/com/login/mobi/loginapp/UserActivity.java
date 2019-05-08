package com.login.mobi.loginapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    private TextView tvUser;
    private Button btDiaryIn;
    private TextView allDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        tvUser = findViewById(R.id.tvUser);
        btDiaryIn = findViewById(R.id.btDiaryIn);
        allDiary = findViewById(R.id.allDiary);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvUser.setText(bundle.getString("user"));
        }

        if (bundle != null) {
            allDiary.setText(bundle.getString("diary"));
        }

        btDiaryIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, DiaryActivity.class);
                startActivity(intent);
            }
        });
    }
}
