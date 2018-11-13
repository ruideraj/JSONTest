package com.example.jsontest.photos;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import com.example.jsontest.JsonPlaceholderApi;
import com.example.jsontest.SingleLiveEvent;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

public class PhotosRepository {

    private JsonPlaceholderApi mJsonApi;
    private final Executor mExecutor;
    private final Handler mHandler;

    private final MutableLiveData<List<Photo>> mPhotos = new MutableLiveData<>();
    private final SingleLiveEvent<String> mToast = new SingleLiveEvent<>();

    public PhotosRepository(JsonPlaceholderApi api) {
        mJsonApi = api;

        mExecutor = AsyncTask.THREAD_POOL_EXECUTOR;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public MutableLiveData<List<Photo>> getPhotos() {
        return mPhotos;
    }

    public SingleLiveEvent<String> getToast() {
        return mToast;
    }

    public void loadPhotos(int albumId) {
        LoadPhotosTask task = new LoadPhotosTask(albumId);
        mExecutor.execute(task);
    }

    private class LoadPhotosTask implements Runnable {
        private int mAlbumId;

        public LoadPhotosTask(int albumId) {
            mAlbumId = albumId;
        }

        @Override
        public void run() {
            Call<List<Photo>> photosCall = mJsonApi.getAlbumPhotos(mAlbumId);
            List<Photo> list = null;
            Exception e = null;
            try {
                Response<List<Photo>> response = photosCall.execute();
                list = response.body();
            }
            catch(IOException io) {
                e = io;
            }

            PhotosUpdateTask updateTask = new PhotosUpdateTask(list, e);
            mHandler.post(updateTask);
        }
    }

    private class PhotosUpdateTask implements Runnable {
        private List<Photo> mList;
        private Exception mException;

        public PhotosUpdateTask(List<Photo> list, Exception e) {
            mList = list;
            mException = e;
        }

        @Override
        public void run() {
            if(mList != null) {
                mPhotos.setValue(mList);
            }
            if(mException != null) {
                mToast.setValue("An error occurred");
            }
        }
    }
}
