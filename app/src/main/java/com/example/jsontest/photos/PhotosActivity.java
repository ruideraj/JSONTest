package com.example.jsontest.photos;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.example.jsontest.R;
import com.example.jsontest.ViewModelFactory;
import com.example.jsontest.albums.Album;

public class PhotosActivity extends AppCompatActivity {

    public static final String EXTRA_ALBUM = "album";

    private PhotosAdapter mAdapter;

    private Album mAlbum;
    private PhotosViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        // Calculate number of columns based on device width
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int widthPx = metrics.widthPixels;
        int photoWidthPx = getResources().getDimensionPixelSize(R.dimen.photo_item_width);
        int columns = widthPx / photoWidthPx;

        RecyclerView recycler = findViewById(R.id.photos_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this, columns);
        recycler.setLayoutManager(layoutManager);
        mAdapter = new PhotosAdapter(this);
        recycler.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this, new ViewModelFactory(this))
                .get(PhotosViewModel.class);

        mViewModel.photos.observe(this, photos -> mAdapter.setData(photos));

        mViewModel.toast.observe(this, toast -> Toast.makeText(this, toast, Toast.LENGTH_SHORT).show());

        if(savedInstanceState == null) {
            Intent intent = getIntent();
            mAlbum = intent.getParcelableExtra(EXTRA_ALBUM);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.start(mAlbum);
    }
}
