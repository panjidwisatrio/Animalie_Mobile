package com.panji.animalie.ui.fragments.popular

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.Repository

class ViewModelPopular(application: Application) : AndroidViewModel(application) {
    // inisialisasi repository
    private val repository = Repository(application)

    // mengambil data post
    suspend fun getPopularPost(type: String, selectedCategory: String?, selectedTag: String?) =
        repository.getPopularPost(type, selectedCategory, selectedTag)
}