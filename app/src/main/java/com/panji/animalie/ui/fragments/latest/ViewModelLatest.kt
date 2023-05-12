package com.panji.animalie.ui.fragments.latest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.Repository

class ViewModelLatest(application: Application): AndroidViewModel(application) {
    // inisialisasi repository
    private val repository = Repository(application)

    // mengambil data post
    suspend fun getPost() = repository.getPost()
}