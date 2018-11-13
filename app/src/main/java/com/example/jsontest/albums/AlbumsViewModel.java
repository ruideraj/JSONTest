package com.example.jsontest.albums;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.jsontest.JsonPlaceholderApi;
import com.example.jsontest.SingleLiveEvent;

import java.util.List;

public class AlbumsViewModel extends ViewModel {

    private AlbumsRepository mRepository;

    public final MutableLiveData<List<Album>> albums;
    public SingleLiveEvent<Album> clickedAlbum = new SingleLiveEvent<>();

    public AlbumsViewModel(JsonPlaceholderApi api) {
        mRepository = new AlbumsRepository(api);
        albums = mRepository.getAlbums();
    }

    public void start() {
        if(albums.getValue() == null) {
            mRepository.loadAlbums();
        }
    }

    public void onClick(int position) {
        List<Album> list = albums.getValue();
        if(list != null && position < list.size()) {
            clickedAlbum.setValue(list.get(position));
        }
    }
}
