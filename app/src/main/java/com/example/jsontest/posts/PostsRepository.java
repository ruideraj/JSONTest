package com.example.jsontest.posts;

import androidx.lifecycle.MutableLiveData;
import com.example.jsontest.AppDatabase;
import com.example.jsontest.JsonPlaceholderApi;
import com.example.jsontest.Runner;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class PostsRepository {

    private JsonPlaceholderApi mJsonApi;
    private AppDatabase mDatabase;
    private Runner mRunner;

    private final MutableLiveData<List<Post>> mPostList = new MutableLiveData<>();

    public PostsRepository(JsonPlaceholderApi api, AppDatabase appDb, Runner runner) {
        mJsonApi = api;
        mDatabase = appDb;
        mRunner = runner;
    }

    public MutableLiveData<List<Post>> getPostList() {
        return mPostList;
    }

    public void loadPostList() {
        mRunner.runBackground(new LoadPostList());
    }

    private class LoadPostList implements Runnable {
        @Override
        public void run() {
            List<Post> list = mDatabase.postDao().getAll();

            if(list.isEmpty()) {
                Call<List<Post>> postCall = mJsonApi.getPosts();
                Response<List<Post>> postResponse;
                try {
                    postResponse = postCall.execute();
                    list = postResponse.body();

                    mDatabase.postDao().insertPostList(list);
                }
                catch(IOException io) {
                    io.printStackTrace();
                }
            }

            mRunner.runUi(new UpdatePostList(list));
        }
    }

    private class UpdatePostList implements Runnable {
        private List<Post> mList;

        public UpdatePostList(List<Post> list) {
            mList = list;
        }

        @Override
        public void run() {
            // TODO Implement error handling logic
            mPostList.setValue(mList);
        }
    }
}
