package com.panji.animalie.ui.myprofile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.repo.UserRepository

class ViewModelMyProfile(application: Application): AndroidViewModel(application)  {
    // inisialisasi repository
    private val repository = UserRepository(application)

    // register dan mengembalikan data ke view
    suspend fun getProfile(token: String? = null, username: String? = null) = repository.getMyProfile(token, username)
    suspend fun logout(token: String) = repository.logout(token)
}