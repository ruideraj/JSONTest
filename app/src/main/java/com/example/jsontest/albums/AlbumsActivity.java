package com.example.jsontest.albums;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

        mViewModel = ViewModelProviders.of(this, new ViewModelFactory(this))
                .get(AlbumsViewModel.class);

        mRecycler = findViewById(R.id.list_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
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
