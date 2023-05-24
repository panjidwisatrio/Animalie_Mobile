package com.panji.animalie.ui.myprofile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.Repository

class ViewModelMyProfile(application: Application): AndroidViewModel(application)  {
    // inisialisasi repository
    private val repository = Repository(application)

    // register dan mengembalikan data ke view
    suspend fun getProfile(token: String) = repository.getMyProfile(token)
}