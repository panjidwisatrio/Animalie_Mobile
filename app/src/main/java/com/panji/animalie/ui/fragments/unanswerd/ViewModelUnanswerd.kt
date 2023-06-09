package com.panji.animalie.ui.fragments.unanswerd

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.repo.PostRepository

class ViewModelUnanswerd(application: Application): AndroidViewModel(application) {
    // inisialisasi repository
     private val repository = PostRepository(application)

     // mengambil data post
     suspend fun getUnanswerdPost(
         type: String,
         selectedCategory: String? = null,
         selectedTag: String? = null,
         query: String? = null,
         page: Int? = 1
     ) = repository.getUnanswerdPost(type, selectedCategory, selectedTag, query, page)
}