package com.example.jsontest.comments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.jsontest.JsonPlaceholderApi;
import com.example.jsontest.SingleLiveEvent;
import com.example.jsontest.posts.Post;

import java.util.List;

public class CommentsViewModel extends ViewModel {

    private CommentsRepository mRepository;

    public MutableLiveData<String> postTitle = new MutableLiveData<>();
    public MutableLiveData<List<Comment>> comments;
    public SingleLiveEvent<String> toast;

    public CommentsViewModel(JsonPlaceholderApi api) {
        mRepository = new CommentsRepository(api);
        comments = mRepository.getComments();
        toast = mRepository.getToast();
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
