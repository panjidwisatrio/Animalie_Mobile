package com.panji.animalie.data.remote.api

import com.panji.animalie.model.response.Auth
import com.panji.animalie.model.response.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun login(
        @Query("email")
        email: String,
        @Query("password")
        password: String
    ): Response<Auth>

    @POST("register")
    suspend fun register(
        @Query("name")
        name: String,
        @Query("username")
        username: String,
        @Query("email")
        email: String,
        @Query("password")
        password: String,
        @Query("confirm_password")
        confirm_password: String
    ): Response<Auth>

    @GET("post")
    suspend fun post(): Response<PostResponse>

    @GET("latest")
    suspend fun latest(
        @Query("type")
        type: String,
    ): Response<PostResponse>

    @GET("latest")
    suspend fun latestCategory(
        @Query("type")
        type: String,
        @Query("selectedCategory")
        selectedCategory: String,
    ): Response<PostResponse>

    @GET("latest")
    suspend fun latestTag(
        @Query("type")
        type: String,
        @Query("selectedTag")
        selectedTag: String,
    ): Response<PostResponse>

    @GET("popular")
    suspend fun popular(
        @Query("type")
        type: String,
    ): Response<PostResponse>

    @GET("popular")
    suspend fun popularCategory(
        @Query("type")
        type: String,
        @Query("selectedCategory")
        selectedCategory: String,
    ): Response<PostResponse>

    @GET("popular")
    suspend fun popularTag(
        @Query("type")
        type: String,
        @Query("selectedTag")
        selectedTag: String,
    ): Response<PostResponse>

    @GET("unanswerd")
    suspend fun unanswerd(
        @Query("type")
        type: String,
    ): Response<PostResponse>

    @GET("unanswerd")
    suspend fun unanswerdCategory(
        @Query("type")
        type: String,
        @Query("selectedCategory")
        selectedCategory: String,
    ): Response<PostResponse>

    @GET("unanswerd")
    suspend fun unanswerdTag(
        @Query("type")
        type: String,
        @Query("selectedTag")
        selectedTag: String,
    ): Response<PostResponse>
}