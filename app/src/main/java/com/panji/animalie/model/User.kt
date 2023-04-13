package com.panji.animalie.model

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    val workspace: String,
    val job_position: String,
    val avatar: String,
    val remember_token: String,
    val email_verified: String,
    val created_at: String,
    val updated_at: String
)