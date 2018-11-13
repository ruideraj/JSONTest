package com.example.jsontest;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.example.jsontest.comments.CommentsViewModel;
import com.example.jsontest.albums.AlbumsViewModel;
import com.example.jsontest.photos.PhotosViewModel;
import com.example.jsontest.posts.PostsViewModel;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static final String API_BASE_URL = "https://jsonplaceholder.typicode.com/";

    private static volatile ViewModelFactory INSTANCE;
    private static final Object sLock = new Object();

    private Application mApplication;
    private JsonPlaceholderApi mJsonApi;

    public static ViewModelFactory getInstance(Application application) {
        if(INSTANCE == null) {
            synchronized(sLock) {
                if(INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application);
                }
            }
        }

        return INSTANCE;
    }

    private ViewModelFactory(Application application) {
        mApplication = application;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mJsonApi = retrofit.create(JsonPlaceholderApi.class);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(PostsViewModel.class)) {
            //noinspection unchecked
            return (T) new PostsViewModel(mJsonApi);
        }
        else if(modelClass.isAssignableFrom(CommentsViewModel.class)) {
            //noinspection unchecked
            return (T) new CommentsViewModel(mJsonApi);
        }
        else if(modelClass.isAssignableFrom(AlbumsViewModel.class)) {
            //noinspection unchecked
            return (T) new AlbumsViewModel(mJsonApi);
        }
        else if(modelClass.isAssignableFrom(PhotosViewModel.class)) {
            //noinspection unchecked
            return (T) new PhotosViewModel(mJsonApi);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
