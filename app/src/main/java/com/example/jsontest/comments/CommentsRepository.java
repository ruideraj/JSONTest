package com.example.jsontest.comments;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import com.example.jsontest.JsonPlaceholderApi;
import com.example.jsontest.Runner;
import com.example.jsontest.SingleLiveEvent;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

public class CommentsRepository {

    private final JsonPlaceholderApi mJsonApi;
    private final Executor mExecutor;
    private final Handler mHandler;
    private final Runner mRunner;

    private final MutableLiveData<List<Comment>> mComments = new MutableLiveData<>();
    private final SingleLiveEvent<String> mToast = new SingleLiveEvent<>();

    public CommentsRepository(JsonPlaceholderApi api) {
        mJsonApi = api;

        mExecutor = AsyncTask.THREAD_POOL_EXECUTOR;
        mHandler = new Handler(Looper.getMainLooper());

        mRunner = new Runner(mHandler, mExecutor);
    }

    public MutableLiveData<List<Comment>> getComments() {
        return mComments;
    }

    public SingleLiveEvent<String> getToast() {
        return mToast;
    }

    public void loadComments(int postId) {
//        LoadCommentsTask task = new LoadCommentsTask(postId);
//        mExecutor.execute(task);

        mRunner.runNetwork(() -> getComments(postId),
                this::setComments,
                throwable -> mToast.setValue("An error occurred"));

//        Observable.fromCallable(() -> getComments(postId))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mComments::setValue, throwable -> mToast.setValue("An error occurred"));
    }

    private void setComments(List<Comment> list) {
        mComments.setValue(list);
    }

    private List<Comment> getComments(int postId) throws Exception {
        Call<List<Comment>> commentsCall = mJsonApi.getCommentsQuery(postId);
        List<Comment> list;
        try {
            Response<List<Comment>> response = commentsCall.execute();
            list = response.body();
        }
        catch(IOException io) {
            // TODO Could include logic for handling different HTTP status codes
            throw io;
        }

        return list;
    }

    private class LoadCommentsTask implements Runnable {
        private int mPostId;

        public LoadCommentsTask(int postId) {
            mPostId = postId;
        }

        @Override
        public void run() {
            Call<List<Comment>> commentsCall = mJsonApi.getCommentsQuery(mPostId);
            List<Comment> list = null;
            Exception e = null;
            try {
                Response<List<Comment>> response = commentsCall.execute();
                list = response.body();
            }
            catch(IOException io) {
                e = io;
            }

            CommentsUpdateTask updateTask = new CommentsUpdateTask(list, e);
            mHandler.post(updateTask);
        }
    }

    private class CommentsUpdateTask implements Runnable {
        private List<Comment> mList;
        private Exception mException;

        public CommentsUpdateTask(List<Comment> list, Exception e) {
            mList = list;
            mException = e;
        }

        @Override
        public void run() {
            if(mList != null) {
                mComments.setValue(mList);
            }
            if(mException != null) {
                mToast.setValue("An error occurred");
            }
        }
    }
}
