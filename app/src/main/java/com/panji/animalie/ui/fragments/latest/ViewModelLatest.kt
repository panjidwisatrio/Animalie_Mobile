package com.panji.animalie.ui.fragments.latest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.repo.PostRepository

class ViewModelLatest(application: Application) : AndroidViewModel(application) {
    // inisialisasi repository
    private val repository = PostRepository(application)

    // mengambil data post
    suspend fun getLatestPost(
        type: String,
        selectedCategory: String? = null,
        selectedTag: String? = null,
        query: String? = null,
        page: Int? = 1
    ) = repository.getLatestPost(type, selectedCategory, selectedTag, query, page)
}