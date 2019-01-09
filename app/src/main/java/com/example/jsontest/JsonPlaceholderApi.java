package com.example.jsontest;

import com.example.jsontest.comments.Comment;
import com.example.jsontest.albums.Album;
import com.example.jsontest.photos.Photo;
import com.example.jsontest.posts.Post;
import com.example.jsontest.todos.Todo;
import com.example.jsontest.users.User;
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

    @GET("albums")
    Call<List<Album>> getAlbums();

    @GET("albums/{albumId}/photos")
    Call<List<Photo>> getAlbumPhotos(@Path("albumId") int albumId);

    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/{userId}/todos")
    Call<List<Todo>> getUserTodos(@Path("userId") int userId);

}
