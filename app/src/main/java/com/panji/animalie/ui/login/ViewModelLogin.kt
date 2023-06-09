package com.panji.animalie.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.repo.UserRepository

class ViewModelLogin(application: Application): AndroidViewModel(application) {
    // inisialisasi repository
    private val repository = UserRepository(application)

    // login dan mengembalikan data ke view
    suspend fun login(email: String, password: String) = repository.login(email, password)
}