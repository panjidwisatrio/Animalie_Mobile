package com.panji.animalie.data.remote.api

import com.panji.animalie.model.response.Auth
import com.panji.animalie.model.response.BookmarkPostResponse
import com.panji.animalie.model.response.CreatePostResponse
import com.panji.animalie.model.response.DetailPostResponse
import com.panji.animalie.model.response.LikePostResponse
import com.panji.animalie.model.response.MyProfileResponse
import com.panji.animalie.model.response.PostResponse
import com.panji.animalie.model.response.TagResponse
import com.panji.animalie.model.response.TagStoreResponse
import com.panji.animalie.model.response.UploadImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @POST("logout")
    suspend fun logout(
        @Header("Authorization")
        token: String
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
        @Query("userId")
        userId: String,
    ): Response<DetailPostResponse>

    @GET("tag")
    suspend fun allTag(): Response<TagResponse>

    @GET("tag-search")
    suspend fun searchTag(
        @Query("query")
        search: String,
    ): Response<TagResponse>

    @POST("tag/store")
    suspend fun storeTag(
        @Header("Authorization")
        token: String,
        @Query("name_tag")
        name: String,
    ): Response<TagStoreResponse>

    @GET("post/create")
    suspend fun createPost(
        @Header("Authorization")
        token: String,
    ): Response<CreatePostResponse>

    @POST("post/store")
    suspend fun storePost(
        @Header("Authorization")
        token: String,
        @Query("title")
        title: String,
        @Query("slug")
        slug: String,
        @Query("category_id")
        category: String,
        @Query("content")
        content: String,
        @Query("tags[]")
        tag: List<String>? = null,
    ): Response<CreatePostResponse>

    @Multipart
    @POST("upload")
    suspend fun uploadImage(
        @Header("Authorization")
        token: String,
        @Part
        upload: MultipartBody.Part,
    ): Response<UploadImageResponse>

    @GET("search-latest")
    suspend fun searchLatest(
        @Query("type")
        type: String,
        @Query("search")
        search: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("search-latest")
    suspend fun searchLatestCategory(
        @Query("type")
        type: String,
        @Query("selectedCategory")
        selectedCategory: String,
        @Query("search")
        search: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("search-latest")
    suspend fun searchLatestTag(
        @Query("type")
        type: String,
        @Query("selectedTag")
        selectedTag: String,
        @Query("search")
        search: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("search-popular")
    suspend fun searchPopular(
        @Query("type")
        type: String,
        @Query("search")
        search: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("search-popular")
    suspend fun searchPopularCategory(
        @Query("type")
        type: String,
        @Query("selectedCategory")
        selectedCategory: String,
        @Query("search")
        search: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("search-popular")
    suspend fun searchPopularTag(
        @Query("type")
        type: String,
        @Query("selectedTag")
        selectedTag: String,
        @Query("search")
        search: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("search-unanswerd")
    suspend fun searchUnanswerd(
        @Query("type")
        type: String,
        @Query("search")
        search: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("search-unanswerd")
    suspend fun searchUnanswerdCategory(
        @Query("type")
        type: String,
        @Query("selectedCategory")
        selectedCategory: String,
        @Query("search")
        search: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @GET("search-unanswerd")
    suspend fun searchUnanswerdTag(
        @Query("type")
        type: String,
        @Query("selectedTag")
        selectedTag: String,
        @Query("search")
        search: String,
        @Query("page")
        page: Int? = 1,
    ): Response<PostResponse>

    @POST("like-post/{id}")
    suspend fun likePost(
        @Header("Authorization")
        token: String,
        @Path("id")
        id: String,
    ): Response<LikePostResponse>

    @POST("saved-post/{id}")
    suspend fun bookmarkPost(
        @Header("Authorization")
        token: String,
        @Path("id")
        id: String,
    ): Response<BookmarkPostResponse>
}