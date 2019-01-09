package com.example.jsontest.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.jsontest.R;
import com.example.jsontest.albums.AlbumsActivity;
import com.example.jsontest.posts.PostsActivity;
import com.example.jsontest.users.UsersActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.main_button_posts).setOnClickListener(this);
        findViewById(R.id.main_button_albums).setOnClickListener(this);
        findViewById(R.id.main_button_users).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.main_button_posts:
                startActivity(new Intent(this, PostsActivity.class));
                break;
            case R.id.main_button_albums:
                startActivity(new Intent(this, AlbumsActivity.class));
                break;
            case R.id.main_button_users:
                startActivity(new Intent(this, UsersActivity.class));
                break;
        }
    }
}
