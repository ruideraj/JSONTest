package com.example.jsontest.posts;

import androidx.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import com.example.jsontest.JsonPlaceholderApi;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class PostsRepository {

    private JsonPlaceholderApi mJsonApi;

    private final MutableLiveData<List<Post>> mPostList = new MutableLiveData<>();

    public PostsRepository(JsonPlaceholderApi api) {
        mJsonApi = api;
    }

    public MutableLiveData<List<Post>> getPostList() {
        return mPostList;
    }

    public void loadPostList() {
        LoadPostListTask task = new LoadPostListTask();
        task.execute();
    }

    private class LoadPostListTask extends AsyncTask<Void, Void, List<Post>> {
        @Override
        protected List<Post> doInBackground(Void... voids) {

            List<Post> list = null;
            Call<List<Post>> postCall = mJsonApi.getPosts();
            Response<List<Post>> postResponse;
            try {
                postResponse = postCall.execute();
                list = postResponse.body();
            }
            catch(IOException io) {
                io.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<Post> posts) {
            mPostList.setValue(posts);
        }
    }
}
