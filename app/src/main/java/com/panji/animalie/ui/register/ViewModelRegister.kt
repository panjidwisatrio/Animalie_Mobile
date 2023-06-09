package com.panji.animalie.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.repo.UserRepository

class ViewModelRegister(application: Application): AndroidViewModel(application) {
    // inisialisasi repository
    private val repository = UserRepository(application)

    // register dan mengembalikan data ke view
    suspend fun register(
        name: String,
        username: String,
        email: String,
        password: String,
        confirm_password: String
    ) = repository.register(name, username, email, password, confirm_password)
}