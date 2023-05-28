package com.panji.animalie.ui.tag

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.Repository

class ViewModelDetailTag(application: Application) : AndroidViewModel(application) {
    //inisialisasi repository
    private val repository = Repository(application)

    //mengambil data detail tag
    suspend fun getDetailTag(slug: String) = repository.getDetailTag(slug)

}