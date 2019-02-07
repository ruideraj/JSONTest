package com.example.jsontest.comments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecycler.setLayoutManager(layoutManager);
        mAdapter = new CommentsAdapter();
        mRecycler.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this, new ViewModelFactory(this))
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
