package com.panji.animalie.model

data class Post(
    val id: Int,
    val userId: Int,
    val categoryId: Int,
    val title: String,
    val slug: String,
    val content: String,
    val created_at: String,
    val updated_at: String
)
