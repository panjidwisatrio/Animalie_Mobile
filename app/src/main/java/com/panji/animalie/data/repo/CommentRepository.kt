package com.panji.animalie.data.repo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.panji.animalie.data.remote.api.ApiService
import com.panji.animalie.data.remote.api.RetrofitService
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.model.response.CommentPostResponse
import com.panji.animalie.model.response.LikePostResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentRepository(application: Application) {
    private val retrofit: ApiService = RetrofitService.create()

    suspend fun addComment(
        token: String,
        postId: String,
        body: String,
    ): LiveData<Resource<CommentPostResponse>> {
        val comment = MutableLiveData<Resource<CommentPostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            comment.postValue(Resource.Error(throwable.message))
        }

        comment.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.commentPost(
                "Bearer $token",
                postId,
                body
            )

            if (response.isSuccessful) {
                comment.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    comment.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    comment.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    comment.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    comment.postValue(Resource.Error("Internal server error"))
                } else {
                    comment.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return comment
    }

    suspend fun deleteComment(
        token: String,
        commentId: String,
    ): LiveData<Resource<CommentPostResponse>> {
        val comment = MutableLiveData<Resource<CommentPostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            comment.postValue(Resource.Error(throwable.message))
        }

        comment.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.deleteComment("Bearer $token", commentId)

            if (response.isSuccessful) {
                comment.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    comment.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    comment.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    comment.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    comment.postValue(Resource.Error("Internal server error"))
                } else {
                    comment.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return comment
    }

    suspend fun likeComment(
        token: String,
        commentId: String,
    ): LiveData<Resource<LikePostResponse>> {
        val comment = MutableLiveData<Resource<LikePostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            comment.postValue(Resource.Error(throwable.message))
        }

        comment.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.likeComment("Bearer $token", commentId)

            if (response.isSuccessful) {
                comment.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    comment.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    comment.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    comment.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    comment.postValue(Resource.Error("Internal server error"))
                } else {
                    comment.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return comment
    }
}