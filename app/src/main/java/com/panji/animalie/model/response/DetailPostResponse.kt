package com.panji.animalie.model.response

import com.panji.animalie.model.Comment
import com.panji.animalie.model.Post
import com.squareup.moshi.Json

data class DetailPostResponse(
    @field:Json(name = "success")
    val success: Boolean,
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "post")
    val post: Post,
    @field:Json(name = "comment")
    val comment: List<Comment>,
)