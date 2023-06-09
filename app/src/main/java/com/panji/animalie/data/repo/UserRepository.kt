package com.panji.animalie.data.repo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.data.remote.api.RetrofitService
import com.panji.animalie.data.repo.firebase.AuthRepository
import com.panji.animalie.model.response.Auth
import com.panji.animalie.model.response.MyProfileResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(application: Application) {
    private val retrofit = RetrofitService.create()
    private val authRepository = AuthRepository()

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
            authRepository.login(email, password).addOnCompleteListener {
                if (it.isSuccessful && response.isSuccessful) {
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
            authRepository.register(email, password).addOnCompleteListener {
                if (it.isSuccessful && response.isSuccessful) {
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
        }
        return auth
    }

    suspend fun logout(token: String): LiveData<Resource<String>> {
        val auth = MutableLiveData<Resource<String>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            auth.postValue(Resource.Error(throwable.message))
        }

        auth.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.logout(token)
            authRepository.logout()

            if (response.isSuccessful) {
                auth.postValue(Resource.Success("Success"))
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

    suspend fun getMyProfile(
        token: String? = null,
        username: String? = null,
    ): LiveData<Resource<MyProfileResponse>> {
        val user = MutableLiveData<Resource<MyProfileResponse>>()

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            user.postValue(Resource.Error(throwable.message))
        }

        user.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = if (token != null) {
                retrofit.myProfile("Bearer $token")
            } else {
                retrofit.otherProfile(username!!)
            }

            if (response.isSuccessful) {
                user.postValue(Resource.Success(response.body()))
            } else {
                if (response.code() == 401) {
                    user.postValue(Resource.Error("Unauthorized"))
                } else if (response.code() == 403) {
                    user.postValue(Resource.Error("API limit exceeded"))
                } else if (response.code() == 422) {
                    user.postValue(Resource.Error("Query parameter is missing"))
                } else if (response.code() == 500) {
                    user.postValue(Resource.Error("Internal server error"))
                } else {
                    user.postValue(Resource.Error("Something went wrong"))
                }
            }
        }
        return user
    }
}