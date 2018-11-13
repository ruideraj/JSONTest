package com.example.jsontest.comments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jsontest.R;
import com.example.jsontest.ViewModelFactory;
import com.example.jsontest.posts.Post;

public class CommentsActivity extends AppCompatActivity {

    public static final String EXTRA_POST = "post";

    private RecyclerView mRecycler;
    private CommentsAdapter mAdapter;

    private CommentsViewModel mViewModel;
    private Post mPost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        mRecycler = findViewById(R.id.comments_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(layoutManager);
        mAdapter = new CommentsAdapter();
        mRecycler.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(getApplication()))
                .get(CommentsViewModel.class);

        mViewModel.postTitle.observe(this, title -> {
            TextView titleText = findViewById(R.id.comments_title);
            titleText.setText(title);
        });

        mViewModel.comments.observe(this, list -> mAdapter.setData(list));

        mViewModel.toast.observe(this, string -> Toast.makeText(this, string, Toast.LENGTH_SHORT).show());

        if(savedInstanceState == null) {
            Intent intent = getIntent();
            mPost = intent.getParcelableExtra(EXTRA_POST);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.start(mPost);
    }
}
