package com.example.jsontest.posts;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.jsontest.SingleLiveEvent;

import java.util.List;

public class PostViewModel extends ViewModel {

    public MutableLiveData<List<Post>> postList;
    public SingleLiveEvent<Post> clickedPost = new SingleLiveEvent<>();

    private PostRepository mPostRepository;

    public PostViewModel() {
        mPostRepository = new PostRepository();
        postList = mPostRepository.getPostList();
    }

    public void start() {
        if(postList.getValue() == null) {
            mPostRepository.loadPostList();
        }
    }

    public void onClick(int position) {
        List<Post> list = postList.getValue();
        if(list != null && position < list.size()) {
            clickedPost.setValue(list.get(position));
        }
    }
}
