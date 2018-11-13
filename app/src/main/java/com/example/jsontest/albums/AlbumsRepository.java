package com.example.jsontest.albums;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import com.example.jsontest.JsonPlaceholderApi;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

public class AlbumsRepository {

    private JsonPlaceholderApi mJsonApi;
    private final Executor mExecutor;
    private final Handler mHandler;

    private final MutableLiveData<List<Album>> mAlbums = new MutableLiveData<>();

    public AlbumsRepository(JsonPlaceholderApi api) {
        mJsonApi = api;

        mExecutor = AsyncTask.THREAD_POOL_EXECUTOR;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public MutableLiveData<List<Album>> getAlbums() {
        return mAlbums;
    }

    public void loadAlbums() {
        LoadAlbumsTask task = new LoadAlbumsTask();
        mExecutor.execute(task);
    }

    private class LoadAlbumsTask implements Runnable {

        @Override
        public void run() {
            Call<List<Album>> commentsCall = mJsonApi.getAlbums();
            List<Album> list = null;
            Exception e = null;
            try {
                Response<List<Album>> response = commentsCall.execute();
                list = response.body();
            }
            catch(IOException io) {
                e = io;
            }

            AlbumsUpdateTask updateTask = new AlbumsUpdateTask(list, e);
            mHandler.post(updateTask);
        }
    }

    private class AlbumsUpdateTask implements Runnable {
        private List<Album> mList;
        private Exception mException;

        public AlbumsUpdateTask(List<Album> list, Exception e) {
            mList = list;
            mException = e;
        }

        @Override
        public void run() {
            if(mList != null) {
                mAlbums.setValue(mList);
            }
            if(mException != null) {
//                mToast.setValue("An error occurred");
            }
        }
    }
}
