package com.panji.animalie.ui.otherprofile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.Repository

class ViewModelProfile(application: Application): AndroidViewModel(application) {
    // inisialisasi repository
     private val repository = Repository(application)

    // register dan mengembalikan data ke view
     suspend fun getProfile(username: String) = repository.getOtherProfile(username)
}