package com.panji.animalie.data.repo.firebase

import com.panji.animalie.data.datasource.AuthDataSource

class AuthRepository {
    private val authDataSource = AuthDataSource()

    fun login(email: String, password: String) = authDataSource.login(email, password)
    fun register(email: String, password: String) = authDataSource.register(email, password)
    fun logout() = authDataSource.logout()
    fun currentUser() = authDataSource.getCurrentUser()
}