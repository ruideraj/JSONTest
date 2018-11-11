package com.example.jsontest;

import com.example.jsontest.comments.Comment;
import com.example.jsontest.posts.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface JsonPlaceholderApi {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{postId}/comments")
    Call<List<Comment>> getComments(@Path("postId") int postId);

    @GET("comments")
    Call<List<Comment>> getCommentsQuery(@Query("postId") int postId);

}