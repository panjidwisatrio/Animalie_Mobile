package com.panji.animalie.ui.fragments.popular

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.repo.PostRepository

class ViewModelPopular(application: Application) : AndroidViewModel(application) {
    // inisialisasi repository
    private val repository = PostRepository(application)

    // mengambil data post
    suspend fun getPopularPost(
        type: String,
        selectedCategory: String? = null,
        selectedTag: String? = null,
        query: String? = null,
        page: Int? = 1
    ) = repository.getPopularPost(type, selectedCategory, selectedTag, query, page)
}