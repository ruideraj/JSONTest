package com.example.jsontest.posts;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.jsontest.AppDatabase;
import com.example.jsontest.JsonPlaceholderApi;
import com.example.jsontest.Runner;
import com.example.jsontest.SingleLiveEvent;

import java.util.List;

public class PostsViewModel extends ViewModel {

    public MutableLiveData<List<Post>> postList;
    public SingleLiveEvent<Post> clickedPost = new SingleLiveEvent<>();

    private PostsRepository mPostsRepository;

    public PostsViewModel(JsonPlaceholderApi api, AppDatabase db, Runner runner) {
        mPostsRepository = new PostsRepository(api, db, runner);
        postList = mPostsRepository.getPostList();
    }

    public void start() {
        if(postList.getValue() == null) {
            mPostsRepository.loadPostList();
        }
    }

    public void onClick(int position) {
        List<Post> list = postList.getValue();
        if(list != null && position < list.size()) {
            clickedPost.setValue(list.get(position));
        }
    }
}
