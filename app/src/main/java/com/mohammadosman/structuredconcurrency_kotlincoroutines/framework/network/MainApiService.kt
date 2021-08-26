package com.mohammadosman.structuredconcurrency_kotlincoroutines.framework.network

import com.mohammadosman.structuredconcurrency_kotlincoroutines.domain.Comment
import com.mohammadosman.structuredconcurrency_kotlincoroutines.domain.Post
import com.mohammadosman.structuredconcurrency_kotlincoroutines.domain.User
import retrofit2.http.GET
import retrofit2.http.Path

interface MainApiService {

    @GET("posts/{userId}")
    suspend fun getPostByUserId(@Path("userId") id: Int): Post

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): User

    @GET("users/{postId}/comments")
    suspend fun getUserComments(
        @Path("postId") postId: Int
    ): List<Comment>

}