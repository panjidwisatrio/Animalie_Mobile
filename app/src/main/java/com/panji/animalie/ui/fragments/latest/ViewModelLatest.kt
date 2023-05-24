package com.panji.animalie.ui.fragments.latest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.Repository

class ViewModelLatest(application: Application) : AndroidViewModel(application) {
    // inisialisasi repository
    private val repository = Repository(application)

    // mengambil data post
    suspend fun getLatestPost(
        type: String,
        selectedCategory: String? = null,
        selectedTag: String? = null,
        page: Int? = 1
    ) = repository.getLatestPost(type, selectedCategory, selectedTag, page)
}