package com.panji.animalie.model.response

import com.panji.animalie.model.Comment
import com.squareup.moshi.Json

data class CommentPostResponse(
    @field:Json(name = "success")
    val success: Boolean,
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "comment")
    val data: Comment
)