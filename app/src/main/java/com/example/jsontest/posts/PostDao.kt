package com.example.jsontest.posts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDao {
    @Query("SELECT * from post")
    fun getAll(): List<Post>

    @Insert
    fun insertPost(user: Post)

    @Insert
    fun insertPostList(userList: List<Post>)
}