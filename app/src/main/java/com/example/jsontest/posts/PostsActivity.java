package com.example.jsontest.posts;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.jsontest.R;
import com.example.jsontest.comments.CommentsActivity;

public class PostsActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private PostAdapter mAdapter;

    private PostViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(PostViewModel.class);

        mRecycler = findViewById(R.id.main_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(layoutManager);
        mAdapter = new PostAdapter(mViewModel);
        mRecycler.setAdapter(mAdapter);

        mViewModel.postList.observe(this, list -> mAdapter.setData(list));

        mViewModel.clickedPost.observe(this, post -> {
            Intent intent = new Intent(this, CommentsActivity.class);
            intent.putExtra(CommentsActivity.EXTRA_POST, post);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.start();
    }
}
