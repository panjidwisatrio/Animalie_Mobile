package com.panji.animalie.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.panji.animalie.data.remote.api.ApiService
import com.panji.animalie.data.remote.api.RetrofitService
import com.panji.animalie.model.response.Auth
import com.panji.animalie.model.response.PostResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository(application: Application) {
    private val retrofit: ApiService = RetrofitService.create()

    suspend fun login(
        email: String,
        password: String
    ): LiveData<Resource<Auth>> {
        val auth = MutableLiveData<Resource<Auth>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            auth.postValue(Resource.Error(throwable.message))
        }

        auth.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.login(email, password)

            if (response.isSuccessful) {
                auth.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    auth.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    auth.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    auth.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    auth.postValue(Resource.Error("Internal server error"))
                } else {
                    auth.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return auth
    }

    suspend fun register(
        name: String,
        username: String,
        email: String,
        password: String,
        confirm_password: String
    ): LiveData<Resource<Auth>> {
        val auth = MutableLiveData<Resource<Auth>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            auth.postValue(Resource.Error(throwable.message))
        }

        auth.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.register(name, username, email, password, confirm_password)

            if (response.isSuccessful) {
                auth.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    auth.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    auth.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    auth.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    auth.postValue(Resource.Error("Internal server error"))
                } else {
                    auth.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return auth
    }

    suspend fun getLatestPost(type: String, selectedCategory: String? = null, selectedTag: String?): LiveData<Resource<PostResponse>> {
        val post = MutableLiveData<Resource<PostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            post.postValue(Resource.Error(throwable.message))
        }

        post.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = if (selectedCategory != null && type != "dashboard" && type != "tag") {
                retrofit.latestCategory(type, selectedCategory)
            } else if(selectedTag != null && type != "dashboard" && type != "interestGroup") {
                retrofit.latestTag(type, selectedTag)
            } else {
                retrofit.latest(type)
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

    suspend fun getPopularPost(type: String, selectedCategory: String?, selectedTag: String?): LiveData<Resource<PostResponse>> {
        val post = MutableLiveData<Resource<PostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            post.postValue(Resource.Error(throwable.message))
        }

        post.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = if (selectedCategory != null && type != "dashboard" && type != "tag") {
                retrofit.popularCategory(type, selectedCategory)
            } else if(selectedTag != null && type != "dashboard" && type != "interestGroup") {
                retrofit.popularTag(type, selectedTag)
            } else {
                retrofit.popular(type)
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

    suspend fun getUnanswerdPost(type: String, selectedCategory: String?, selectedTag: String?): LiveData<Resource<PostResponse>> {
        val post = MutableLiveData<Resource<PostResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            post.postValue(Resource.Error(throwable.message))
        }

        post.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = if (selectedCategory != null && type != "dashboard" && type != "tag") {
                retrofit.unanswerdCategory(type, selectedCategory)
            } else if(selectedTag != null && type != "dashboard" && type != "interestGroup") {
                retrofit.unanswerdTag(type, selectedTag)
            } else {
                retrofit.unanswerd(type)
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
}