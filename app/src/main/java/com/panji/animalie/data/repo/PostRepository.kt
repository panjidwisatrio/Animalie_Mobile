package com.panji.animalie.data.repo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.data.remote.api.ApiService
import com.panji.animalie.data.remote.api.RetrofitService
import com.panji.animalie.model.response.BookmarkPostResponse
import com.panji.animalie.model.response.CreatePostResponse
import com.panji.animalie.model.response.DeletePostResponse
import com.panji.animalie.model.response.DetailPostResponse
import com.panji.animalie.model.response.LikePostResponse
import com.panji.animalie.model.response.PostResponse
import com.panji.animalie.model.response.UpdatePostResponse
import com.panji.animalie.model.response.UploadImageResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class PostRepository(application: Application) {
    private val retrofit: ApiService = RetrofitService.create()

    suspend fun getLatestPost(
        type: String,
        selectedCategory: String? = null,
        selectedTag: String? = null,
        query: String? = null,
        page: Int?
    ): LiveData<Resource<PostResponse>> {
        val post = MutableLiveData<Resource<PostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            post.postValue(Resource.Error(throwable.message))
        }

        post.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = if (selectedCategory != null && type != "dashboard" && type != "tag") {
                if (query != null) {
                    retrofit.searchLatestCategory(type, selectedCategory, query, page)
                } else {
                    retrofit.latestCategory(type, selectedCategory, page)
                }
            } else if (selectedTag != null && type != "dashboard" && type != "interestGroup") {
                if (query != null) {
                    retrofit.searchLatestTag(type, selectedTag, query, page)
                } else {
                    retrofit.latestTag(type, selectedTag, page)
                }
            } else {
                if (query != null) {
                    retrofit.searchLatest(type, query, page)
                } else {
                    retrofit.latest(type, page)
                }
            }

            if (response.isSuccessful) {
                post.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    post.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    post.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    post.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    post.postValue(Resource.Error("Internal server error"))
                } else {
                    post.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return post
    }

    suspend fun getPopularPost(
        type: String,
        selectedCategory: String? = null,
        selectedTag: String? = null,
        query: String? = null,
        page: Int?
    ): LiveData<Resource<PostResponse>> {
        val post = MutableLiveData<Resource<PostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            post.postValue(Resource.Error(throwable.message))
        }

        post.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = if (selectedCategory != null && type != "dashboard" && type != "tag") {
                if (query != null) {
                    retrofit.searchPopularCategory(type, selectedCategory, query, page)
                } else {
                    retrofit.popularCategory(type, selectedCategory, page)
                }
            } else if (selectedTag != null && type != "dashboard" && type != "interestGroup") {
                if (query != null) {
                    retrofit.searchPopularTag(type, selectedTag, query, page)
                } else {
                    retrofit.popularTag(type, selectedTag, page)
                }
            } else {
                if (query != null) {
                    retrofit.searchPopular(type, query, page)
                } else {
                    retrofit.popular(type, page)
                }
            }

            if (response.isSuccessful) {
                post.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    post.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    post.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    post.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    post.postValue(Resource.Error("Internal server error"))
                } else {
                    post.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return post
    }

    suspend fun getUnanswerdPost(
        type: String,
        selectedCategory: String? = null,
        selectedTag: String? = null,
        query: String? = null,
        page: Int?
    ): LiveData<Resource<PostResponse>> {
        val post = MutableLiveData<Resource<PostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            post.postValue(Resource.Error(throwable.message))
        }

        post.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = if (selectedCategory != null && type != "dashboard" && type != "tag") {
                if (query != null) {
                    retrofit.searchUnanswerdCategory(type, selectedCategory, query, page)
                } else {
                    retrofit.unanswerdCategory(type, selectedCategory, page)
                }
            } else if (selectedTag != null && type != "dashboard" && type != "interestGroup") {
                if (query != null) {
                    retrofit.searchUnanswerdTag(type, selectedTag, query, page)
                } else {
                    retrofit.unanswerdTag(type, selectedTag, page)
                }
            } else {
                if (query != null) {
                    retrofit.searchUnanswerd(type, query, page)
                } else {
                    retrofit.unanswerd(type, page)
                }
            }

            if (response.isSuccessful) {
                post.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    post.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    post.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    post.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    post.postValue(Resource.Error("Internal server error"))
                } else {
                    post.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return post
    }

    suspend fun getSavedPost(token: String, page: Int?): LiveData<Resource<PostResponse>> {
        val post = MutableLiveData<Resource<PostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            post.postValue(Resource.Error(throwable.message))
        }

        post.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.savedPost("Bearer $token", page)

            if (response.isSuccessful) {
                post.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    post.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    post.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    post.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    post.postValue(Resource.Error("Internal server error"))
                } else {
                    post.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return post
    }

    suspend fun getMyPost(userId: String, page: Int?): LiveData<Resource<PostResponse>> {
        val post = MutableLiveData<Resource<PostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            post.postValue(Resource.Error(throwable.message))
        }

        post.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.myPost(userId, page)

            if (response.isSuccessful) {
                post.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    post.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    post.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    post.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    post.postValue(Resource.Error("Internal server error"))
                } else {
                    post.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return post
    }

    suspend fun getDiscussion(userId: String, page: Int?): LiveData<Resource<PostResponse>> {
        val post = MutableLiveData<Resource<PostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            post.postValue(Resource.Error(throwable.message))
        }

        post.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.myDiscussion(userId, page)

            if (response.isSuccessful) {
                post.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    post.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    post.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    post.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    post.postValue(Resource.Error("Internal server error"))
                } else {
                    post.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return post
    }

    suspend fun getDetailPost(slug: String, userId: String): LiveData<Resource<DetailPostResponse>> {
        val post = MutableLiveData<Resource<DetailPostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            post.postValue(Resource.Error(throwable.message))
        }

        post.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.detailPost(slug, userId)

            if (response.isSuccessful) {
                post.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    post.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    post.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    post.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    post.postValue(Resource.Error("Internal server error"))
                } else {
                    post.postValue(Resource.Error("Something went wrong: ${response.code()}"))
                }
            }
        }
        return post
    }

    suspend fun getDataPreparedCreatePost(token: String): LiveData<Resource<CreatePostResponse>> {
        val catetag = MutableLiveData<Resource<CreatePostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            catetag.postValue(Resource.Error(throwable.message))
        }

        catetag.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.createPost("Bearer $token")

            if (response.isSuccessful) {
                catetag.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    catetag.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    catetag.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    catetag.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    catetag.postValue(Resource.Error("Internal server error"))
                } else {
                    catetag.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return catetag
    }

    suspend fun uploadImage(token: String, image: MultipartBody.Part): LiveData<Resource<UploadImageResponse>> {
        val uploadImage = MutableLiveData<Resource<UploadImageResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            uploadImage.postValue(Resource.Error(throwable.message))
        }

        uploadImage.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.uploadImage("Bearer $token", image)

            if (response.isSuccessful) {
                uploadImage.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    uploadImage.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    uploadImage.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    uploadImage.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    uploadImage.postValue(Resource.Error("Internal server error"))
                } else {
                    uploadImage.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return uploadImage
    }

    suspend fun createPost(
        token: String,
        title: String,
        slug: String,
        categoryId: String,
        content: String,
        tag: List<String>? = null
    ): LiveData<Resource<CreatePostResponse>> {
        val createPost = MutableLiveData<Resource<CreatePostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            createPost.postValue(Resource.Error(throwable.message))
        }

        createPost.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.storePost("Bearer $token", title, slug, categoryId, content, tag)

            if (response.isSuccessful) {
                createPost.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    createPost.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    createPost.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    createPost.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    createPost.postValue(Resource.Error("Internal server error"))
                } else {
                    createPost.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return createPost
    }

    suspend fun likePost(token: String, postId: String): LiveData<Resource<LikePostResponse>> {
        val likePost = MutableLiveData<Resource<LikePostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            likePost.postValue(Resource.Error(throwable.message))
        }

        likePost.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.likePost("Bearer $token", postId)

            if (response.isSuccessful) {
                likePost.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    likePost.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    likePost.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    likePost.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    likePost.postValue(Resource.Error("Internal server error"))
                } else {
                    likePost.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return likePost
    }

    suspend fun bookmarkPost(token: String, postId: String): LiveData<Resource<BookmarkPostResponse>> {
        val bookmarkPost = MutableLiveData<Resource<BookmarkPostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            bookmarkPost.postValue(Resource.Error(throwable.message))
        }

        bookmarkPost.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.bookmarkPost("Bearer $token", postId)

            if (response.isSuccessful) {
                bookmarkPost.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    bookmarkPost.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    bookmarkPost.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    bookmarkPost.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    bookmarkPost.postValue(Resource.Error("Internal server error"))
                } else {
                    bookmarkPost.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return bookmarkPost
    }

    suspend fun editPost(
        token: String,
        postId: String,
        title: String,
        slug: String,
        categoryId: String,
        content: String,
        tag: List<String>? = null
    ): LiveData<Resource<UpdatePostResponse>> {
        val updatePost = MutableLiveData<Resource<UpdatePostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            updatePost.postValue(Resource.Error(throwable.message))
        }

        updatePost.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.updatePost("Bearer $token", postId, title, slug, categoryId, content, tag)

            if (response.isSuccessful) {
                updatePost.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    updatePost.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    updatePost.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    updatePost.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    updatePost.postValue(Resource.Error("Internal server error"))
                } else {
                    updatePost.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return updatePost
    }

    suspend fun deletePost(token: String, slug: String): LiveData<Resource<DeletePostResponse>> {
        val deletePost = MutableLiveData<Resource<DeletePostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            deletePost.postValue(Resource.Error(throwable.message))
        }

        deletePost.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.deletePost("Bearer $token", slug)

            if (response.isSuccessful) {
                deletePost.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    deletePost.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    deletePost.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    deletePost.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    deletePost.postValue(Resource.Error("Internal server error"))
                } else {
                    deletePost.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return deletePost
    }
}