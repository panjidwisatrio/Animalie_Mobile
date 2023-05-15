package com.panji.animalie.model.response

import com.panji.animalie.model.PostReady

data class PostResponse(
    val success : Boolean,
    val message: String,
    val posts: PostReady,
    val type: String,
)
