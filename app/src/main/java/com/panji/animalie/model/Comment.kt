package com.panji.animalie.model

import com.squareup.moshi.Json

data class Comment(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "user_id")
    val user_id: Int,
    @field:Json(name = "post_id")
    val post_id: Int,
    @field:Json(name = "comment")
    val comment: String,
    @field:Json(name = "user")
    val user: User,
    @field:Json(name = "like_count_api")
    val like: List<Like>,
    @field:Json(name = "created_at")
    val created_at: String,
    @field:Json(name = "updated_at")
    val updated_at: String
)
