package com.example.jsontest.posts;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.jsontest.ADB;
import com.example.jsontest.JsonPlaceholderApi;
import com.example.jsontest.SingleLiveEvent;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.Callable;

public class PostsViewModel extends ViewModel {

    public MutableLiveData<List<Post>> postList;
    public SingleLiveEvent<Post> clickedPost = new SingleLiveEvent<>();

    private PostsRepository mPostsRepository;

    private Disposable disposable = null;

    public PostsViewModel(JsonPlaceholderApi api) {
        mPostsRepository = new PostsRepository(api);
        postList = mPostsRepository.getPostList();
    }

    public void start() {
        if(postList.getValue() == null) {
            mPostsRepository.loadPostList();
        }

        Callable<String> callable = () -> {
            try {
                int count = 2;
                while(count > 0) {
                    ADB.d("PostsViewModel", "callable ping");
                    count--;
                    Thread.sleep(5000);
                }
            }
            catch(InterruptedException ie) {
                ADB.d("PostsViewModel", "Interrupted!");
            }

            return "done";
        };

        disposable = Observable.fromCallable(callable)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(successString -> ADB.d("PostsViewModel", "Callable success"),
                        errorString -> ADB.d("PostsViewModel", "Callable error"));
    }

    public void onClick(int position) {
        List<Post> list = postList.getValue();
        if(list != null && position < list.size()) {
            clickedPost.setValue(list.get(position));
        }
    }

    @Override
    protected void onCleared() {
        if(disposable != null) {
            disposable.dispose();
        }

        super.onCleared();
    }
}
