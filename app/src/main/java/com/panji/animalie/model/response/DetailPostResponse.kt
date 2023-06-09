package com.panji.animalie.model.response

import com.panji.animalie.model.Post
import com.squareup.moshi.Json

data class DetailPostResponse(
    @field:Json(name = "success")
    val success: Boolean,
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "post")
    val post: Post,
    @field:Json(name = "liked")
    val liked: Boolean,
    @field:Json(name = "bookmarked")
    val bookmarked: Boolean,
)