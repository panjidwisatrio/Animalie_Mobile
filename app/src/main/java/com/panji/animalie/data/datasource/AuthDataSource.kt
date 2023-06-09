package com.panji.animalie.data.datasource

import com.google.firebase.auth.FirebaseAuth

class AuthDataSource {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)

    fun register(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)

    fun logout() = auth.signOut()

    fun getCurrentUser() = auth.currentUser
}