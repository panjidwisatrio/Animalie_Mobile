package com.panji.animalie.data.remote.api

import com.panji.animalie.model.response.Auth
import com.panji.animalie.model.response.CreatePostResponse
import com.panji.animalie.model.response.DetailPostResponse
import com.panji.animalie.model.response.EditProfileResponse
import com.panji.animalie.model.response.MyProfileResponse
import com.panji.animalie.model.response.PostResponse
import com.panji.animalie.model.response.TagResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
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

    @GET("latest")
    suspend fun latest(
        @Query("type")
        type: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("latest")
    suspend fun latestCategory(
        @Query("type")
        type: String,
        @Query("selectedCategory")
        selectedCategory: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("latest")
    suspend fun latestTag(
        @Query("type")
        type: String,
        @Query("selectedTag")
        selectedTag: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("popular")
    suspend fun popular(
        @Query("type")
        type: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("popular")
    suspend fun popularCategory(
        @Query("type")
        type: String,
        @Query("selectedCategory")
        selectedCategory: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("popular")
    suspend fun popularTag(
        @Query("type")
        type: String,
        @Query("selectedTag")
        selectedTag: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("unanswerd")
    suspend fun unanswerd(
        @Query("type")
        type: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("unanswerd")
    suspend fun unanswerdCategory(
        @Query("type")
        type: String,
        @Query("selectedCategory")
        selectedCategory: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("unanswerd")
    suspend fun unanswerdTag(
        @Query("type")
        type: String,
        @Query("selectedTag")
        selectedTag: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("myProfile")
    suspend fun myProfile(
        @Header("Authorization")
        token: String
    ): Response<MyProfileResponse>

    @GET("profile/{username}")
    suspend fun otherProfile(
        @Path("username")
        username: String,
    ): Response<MyProfileResponse>

    @GET("savedpost")
    suspend fun savedPost(
        @Header("Authorization")
        token: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("myProfile/mypost")
    suspend fun myPost(
        @Query("userId")
        userId: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("myProfile/discussion")
    suspend fun myDiscussion(
        @Query("userId")
        userId: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("post-detail/{slug}")
    suspend fun detailPost(
        @Path("slug")
        slug: String,
    ): Response<DetailPostResponse>

    @GET("tag")
    suspend fun allTag(): Response<TagResponse>

    @GET("post/create")
    suspend fun createPost(
        @Header("Authorization")
        token: String,
    ): Response<CreatePostResponse>

    @PATCH("profile")
    suspend fun editProfile(
        @Header("Authorization")
        token: String,
        @Query("name")
        name: String,
        @Query("username")
        username: String,
        @Query("work_place")
        work_place: String? = null,
        @Query("job_position")
        job_position: String? = null,
        @Query("email")
        email: String,
        @Query("avatar")
        avatar: String? = null,
    ): Response<EditProfileResponse>
}
