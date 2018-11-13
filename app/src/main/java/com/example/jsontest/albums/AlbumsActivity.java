package com.example.jsontest.albums;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.example.jsontest.R;
import com.example.jsontest.ViewModelFactory;
import com.example.jsontest.photos.PhotosActivity;

public class AlbumsActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private AlbumsAdapter mAdapter;

    private AlbumsViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(getApplication()))
                .get(AlbumsViewModel.class);

        mRecycler = findViewById(R.id.list_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(layoutManager);
        mAdapter = new AlbumsAdapter(mViewModel);
        mRecycler.setAdapter(mAdapter);

        mViewModel.albums.observe(this, albums -> mAdapter.setData(albums));

        mViewModel.clickedAlbum.observe(this, album -> {
            if(album != null) {
                Intent intent = new Intent(this, PhotosActivity.class);
                intent.putExtra(PhotosActivity.EXTRA_ALBUM, album);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.start();
    }
}
