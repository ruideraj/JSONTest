package com.example.jsontest;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;
import com.example.jsontest.albums.AlbumsViewModel;
import com.example.jsontest.comments.CommentsViewModel;
import com.example.jsontest.photos.PhotosViewModel;
import com.example.jsontest.posts.PostsViewModel;
import com.example.jsontest.users.UserDetailsRepository;
import com.example.jsontest.users.UserDetailsViewModel;
import com.example.jsontest.users.UsersRepository;
import com.example.jsontest.users.UsersViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Runner mRunner;
    private JsonPlaceholderApi mJsonApi;

    public ViewModelFactory(Context context) {
        mRunner = ((JsonTestApplication) context.getApplicationContext()).getRunner();
        mJsonApi = ((JsonTestApplication) context.getApplicationContext()).getJsonApi();
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
        else if(modelClass.isAssignableFrom(UsersViewModel.class)) {
            UsersRepository repository = new UsersRepository(mRunner, mJsonApi);

            //noinspection unchecked
            return (T) new UsersViewModel(repository);
        }
        else if(modelClass.isAssignableFrom(UserDetailsViewModel.class)) {
            UserDetailsRepository repository = new UserDetailsRepository(mRunner, mJsonApi);

            //noinspection unchecked
            return (T) new UserDetailsViewModel(repository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
