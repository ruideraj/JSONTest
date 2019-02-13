package com.example.jsontest

import android.app.Application
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JsonTestApplication : Application() {

    private lateinit var mRunner : Runner
    private lateinit var mJsonApi : JsonPlaceholderApi
    private lateinit var mDb : AppDatabase

    override fun onCreate() {
        super.onCreate()

        val executor = AsyncTask.THREAD_POOL_EXECUTOR
        val handler = Handler(Looper.getMainLooper())
        mRunner = Runner(handler, executor)

        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.json_api_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mJsonApi = retrofit.create(JsonPlaceholderApi::class.java)

        mDb = Room.databaseBuilder(this, AppDatabase::class.java, getString(R.string.database_name)).build()
    }

    fun getRunner() = mRunner
    fun getJsonApi() = mJsonApi
    fun getDatabase() = mDb
}