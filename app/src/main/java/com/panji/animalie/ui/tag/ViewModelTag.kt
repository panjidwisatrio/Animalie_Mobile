package com.panji.animalie.ui.tag

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.repo.TagRepository

class ViewModelTag(application: Application) : AndroidViewModel(application) {
    //inisialiasi repository
    private val repository = TagRepository(application)

    //mengambil data tag
    suspend fun getTag(query: String? = null) = repository.getAllTag(query)
}