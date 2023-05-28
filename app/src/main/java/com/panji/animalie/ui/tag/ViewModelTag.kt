package com.panji.animalie.ui.tag

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.Repository

class ViewModelTag(application: Application) : AndroidViewModel(application) {
    //inisialiasi repository
    private val repository = Repository(application)

    //mengambil data tag
    suspend fun getTag() = repository.getAllTag()
}