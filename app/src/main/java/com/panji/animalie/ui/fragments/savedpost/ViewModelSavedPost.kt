package com.panji.animalie.ui.fragments.savedpost

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.repo.PostRepository

class ViewModelSavedPost(application: Application) : AndroidViewModel(application) {
    // inisialisasi repository
    private val repository = PostRepository(application)

    // register dan mengembalikan data ke view
    suspend fun getSavedPost(
        token: String,
        page: Int? = 1
    ) = repository.getSavedPost(token, page)
}