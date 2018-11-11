package com.example.jsontest.posts;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import com.example.jsontest.JsonPlaceholderApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class PostRepository {

    private static final String API_BASE_URL = "https://jsonplaceholder.typicode.com/";

    private JsonPlaceholderApi jsonApi;

    private final MutableLiveData<List<Post>> postList = new MutableLiveData<>();

    public PostRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonPlaceholderApi.class);
    }

    public MutableLiveData<List<Post>> getPostList() {
        return postList;
    }

    public void loadPostList() {
        LoadPostListTask task = new LoadPostListTask();
        task.execute();
    }

    private class LoadPostListTask extends AsyncTask<Void, Void, List<Post>> {
        @Override
        protected List<Post> doInBackground(Void... voids) {

            List<Post> list = null;
            Call<List<Post>> postCall = jsonApi.getPosts();
            Response<List<Post>> postResponse;
            try {
                postResponse = postCall.execute();
                list = postResponse.body();
            }
            catch(IOException io) {
                io.printStackTrace();
            }

//            Post post = new Post();
//            post.id = 1;
//            post.userId = 3;
//            post.title = "Title1";
//            post.body = "Body1";
//
//            Post post2 = new Post();
//            post2.id = 2;
//            post2.userId = 4;
//            post2.title = "Title2";
//            post2.body = "Body2";
//
//            List<Post> list = new ArrayList<>();
//            list.add(post);
//            list.add(post2);

            return list;
        }

        @Override
        protected void onPostExecute(List<Post> posts) {
            postList.setValue(posts);
        }
    }
}
