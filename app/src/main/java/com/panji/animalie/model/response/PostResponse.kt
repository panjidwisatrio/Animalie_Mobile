package com.panji.animalie.model.response

import com.panji.animalie.model.PostReady
import com.squareup.moshi.Json

data class PostResponse(
    @field:Json(name = "success")
    val success: Boolean,
    val page: String?,
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "posts")
    val posts: PostReady,
    @field:Json(name = "type")
    val type: String,
)
