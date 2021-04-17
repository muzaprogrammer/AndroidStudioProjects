package com.example.a9retrofita.interfaces;

import com.example.a9retrofita.models.Post;
import com.example.a9retrofita.models.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceHolderAPI {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("users")
    Call<List<User>> getUsers();
}
