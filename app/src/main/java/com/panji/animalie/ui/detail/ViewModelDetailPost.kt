package com.panji.animalie.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.Repository

class ViewModelDetailPost(application: Application): AndroidViewModel(application) {
    // inisialisasi repository
    private val repository = Repository(application)

    // register dan mengembalikan data ke view
    suspend fun getDetailPost(slug: String) = repository.getDetailPost(slug)
}