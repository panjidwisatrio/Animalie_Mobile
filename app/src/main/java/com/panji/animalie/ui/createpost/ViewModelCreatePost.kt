package com.panji.animalie.ui.createpost

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.repo.PostRepository
import com.panji.animalie.data.repo.TagRepository
import okhttp3.MultipartBody

class ViewModelCreatePost(application: Application): AndroidViewModel(application) {
    private val repository = PostRepository(application)
    private val tagRepository = TagRepository(application)

    suspend fun createPost(
        token: String,
        title: String,
        slug: String,
        categoryId: String,
        content: String,
        tag: List<String>? = null
    ) = repository.createPost(token, title, slug, categoryId, content, tag)

    suspend fun editPost(
        token: String,
        postId: String,
        title: String,
        slug: String,
        categoryId: String,
        content: String,
        tag: List<String>? = null
    ) = repository.editPost(token, postId, title, slug, categoryId, content, tag)

    suspend fun uploadImage(token: String, image: MultipartBody.Part) = repository.uploadImage(token, image)
    suspend fun getCategoriesAndTags(token: String) = repository.getDataPreparedCreatePost(token)
    suspend fun storeTag(token: String, name: String) = tagRepository.createTag(token, name)
    suspend fun deletePost(token: String, postId: String) = repository.deletePost(token, postId)
}