package com.panji.animalie.data.repo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.data.remote.api.RetrofitService
import com.panji.animalie.model.response.TagResponse
import com.panji.animalie.model.response.TagStoreResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagRepository(application: Application) {
    private val retrofit = RetrofitService.create()

    suspend fun getAllTag(query: String? = null): LiveData<Resource<TagResponse>> {
        val tag = MutableLiveData<Resource<TagResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            tag.postValue(Resource.Error(throwable.message))
        }

        tag.postValue(Resource.Loading())

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = if (query != null) {
                retrofit.searchTag(query)
            } else {
                retrofit.allTag()
            }

            if (response.isSuccessful) {
                tag.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    tag.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    tag.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    tag.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    tag.postValue(Resource.Error("Internal server error"))
                } else {
                    tag.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return tag
    }

    suspend fun createTag(token: String, tag: String): LiveData<Resource<TagStoreResponse>> {
        val createTag = MutableLiveData<Resource<TagStoreResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            createTag.postValue(Resource.Error(throwable.message))
        }

        createTag.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.storeTag("Bearer $token", tag)

            if (response.isSuccessful) {
                createTag.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    createTag.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    createTag.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    createTag.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    createTag.postValue(Resource.Error("Internal server error"))
                } else {
                    createTag.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return createTag
    }
}