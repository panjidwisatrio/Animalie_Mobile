package com.panji.animalie.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.repo.CommentRepository
import com.panji.animalie.data.repo.PostRepository

class ViewModelDetailPost(application: Application): AndroidViewModel(application) {
    // inisialisasi repository
    private val repository = PostRepository(application)
    private val repositoryComment = CommentRepository(application)

    // register dan mengembalikan data ke view
    suspend fun getDetailPost(slug: String, userId: String) = repository.getDetailPost(slug, userId)
    suspend fun likePost(token: String, postId: String) = repository.likePost(token, postId)
    suspend fun bookmarkPost(token: String, postId: String) = repository.bookmarkPost(token, postId)
    suspend fun sendComment(token: String, postId: String, body: String) = repositoryComment.addComment(token, postId, body)
    suspend fun deleteComment(token: String, commentId: String) = repositoryComment.deleteComment(token, commentId)
    suspend fun likeComment(token: String, commentId: String) = repositoryComment.likeComment(token, commentId)
}