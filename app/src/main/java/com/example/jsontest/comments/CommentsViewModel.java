package com.example.jsontest.comments;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.jsontest.posts.Post;

import java.util.List;

public class CommentsViewModel extends ViewModel {

    private CommentsRepository mRepository;

    public MutableLiveData<String> postTitle = new MutableLiveData<>();
    public MutableLiveData<List<Comment>> comments;

    public CommentsViewModel() {
        mRepository = new CommentsRepository();
        comments = mRepository.getComments();
    }

    public void start(Post post) {
        if(post == null) return;

        postTitle.setValue(post.getTitle());

        int postId = post.getId();
        if(comments.getValue() == null && postId >= 0) {
            mRepository.loadComments(postId);
        }
    }
}
