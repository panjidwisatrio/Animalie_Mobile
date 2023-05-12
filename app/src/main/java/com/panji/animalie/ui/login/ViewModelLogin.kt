package com.panji.animalie.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.Repository

class ViewModelLogin(application: Application): AndroidViewModel(application) {
    // inisialisasi repository
    private val repository = Repository(application)

    // login dan mengembalikan data ke view
    suspend fun login(email: String, password: String) = repository.login(email, password)
}