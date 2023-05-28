package com.panji.animalie.ui.createpost

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.Repository

class ViewModelCreatePost(application: Application): AndroidViewModel(application) {
    val repository = Repository(application)

//    suspend fun createPost(
//        token: String,
//        title: String,
//        slug: String,
//        categoryId: String,
//        content: String,
//    ) = repository.createPost(token, title, slug, categoryId, content)
//
//    suspend fun uploadImage(token: String, image: String) = repository.uploadImage(token, image)
//
    suspend fun getCategoriesAndTags(token: String) = repository.getDataPreparedCreatePost(token)
}