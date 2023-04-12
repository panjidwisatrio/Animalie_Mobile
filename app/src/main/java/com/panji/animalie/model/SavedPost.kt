package com.panji.animalie.model

data class SavedPost(
    val id: Int,
    val user_id: Int,
    val post_id: Int,
    val created_at: String,
    val updated_at: String
)
