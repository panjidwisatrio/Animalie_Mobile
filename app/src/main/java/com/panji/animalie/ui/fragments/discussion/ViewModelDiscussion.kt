package com.panji.animalie.ui.fragments.discussion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.Repository

class ViewModelDiscussion(application: Application) : AndroidViewModel(application) {
    // inisialisasi repository
    private val repository = Repository(application)

    // register dan mengembalikan data ke view
    suspend fun getDiscussion(
        userId: String,
        page: Int? = 1,
    ) = repository.getDiscussion(userId, page)
}