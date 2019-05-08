package com.login.mobi.loginapp;

import android.app.LauncherActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    private TextView tvUser;
    private Button btDiaryIn;
    DatabaseHelper db;

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    ListView diarylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        db = new DatabaseHelper(this);

        listItem = new ArrayList<>();

        viewData();

        diarylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = diarylist.getItemAtPosition(i).toString();
                Toast.makeText(UserActivity.this, "" + text, Toast.LENGTH_SHORT).show();
            }
        });

        tvUser = findViewById(R.id.tvUser);
        btDiaryIn = findViewById(R.id.btDiaryIn);
        diarylist = findViewById(R.id.diarylist);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvUser.setText(bundle.getString("user"));
        }

        btDiaryIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, DiaryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void viewData() {
        Cursor cursor = db.getAllDiary();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data to show ", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                listItem.add(cursor.getString(1));
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.activity_list_item, listItem);
            diarylist.setAdapter(adapter);
        }
    }
}