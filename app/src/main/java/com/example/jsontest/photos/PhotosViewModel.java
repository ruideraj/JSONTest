package com.example.jsontest.photos;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.jsontest.JsonPlaceholderApi;
import com.example.jsontest.SingleLiveEvent;
import com.example.jsontest.albums.Album;

import java.util.List;

public class PhotosViewModel extends ViewModel {

    private PhotosRepository mRepository;

    public final MutableLiveData<String> albumTitle = new MutableLiveData<>();
    public final MutableLiveData<List<Photo>> photos;
    public SingleLiveEvent<String> toast;

    public PhotosViewModel(JsonPlaceholderApi api) {
        mRepository = new PhotosRepository(api);
        photos = mRepository.getPhotos();
        toast = mRepository.getToast();
    }

    public void start(Album album) {
        if(album == null) return;

        albumTitle.setValue(album.getTitle());
        int albumId = album.getId();
        if(photos.getValue() == null && albumId >= 0) {
            mRepository.loadPhotos(albumId);
        }
    }
}
