package com.panji.animalie.ui.fragments.mypost

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.Repository

class ViewModelMyPost(application: Application) : AndroidViewModel(application) {
    // inisialisasi repository
    private val repository = Repository(application)

    // register dan mengembalikan data ke view
    suspend fun getMyPost(
        userId: String,
        page: Int? = 1
    ) = repository.getMyPost(userId, page)
}