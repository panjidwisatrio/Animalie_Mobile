package com.panji.animalie.model

data class Comment(
    val id: Int,
    val user_id: Int,
    val post_id: Int,
    val comment: String,
    val created_at: String,
    val updated_at: String
)
