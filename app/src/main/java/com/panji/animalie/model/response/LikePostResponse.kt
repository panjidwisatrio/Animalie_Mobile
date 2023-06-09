package com.panji.animalie.model.response

import com.squareup.moshi.Json

data class LikePostResponse(
    @field:Json(name = "likeCount")
    val likeCount: Int,
    @field:Json(name = "liked")
    val liked: Boolean,
)