package com.example.jsontest

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jsontest.posts.Post
import com.example.jsontest.posts.PostDao

@Database(entities = [Post::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
        abstract fun postDao(): PostDao
}