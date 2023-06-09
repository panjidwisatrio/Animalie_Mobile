package com.panji.animalie.ui.fragments.discussion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.repo.PostRepository

class ViewModelDiscussion(application: Application) : AndroidViewModel(application) {
    // inisialisasi repository
    private val repository = PostRepository(application)

    // register dan mengembalikan data ke view
    suspend fun getDiscussion(
        userId: String,
        page: Int? = 1,
    ) = repository.getDiscussion(userId, page)
}