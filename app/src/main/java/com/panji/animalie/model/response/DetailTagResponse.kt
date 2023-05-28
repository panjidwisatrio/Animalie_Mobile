package com.panji.animalie.model.response

import com.panji.animalie.model.PostReady
import com.squareup.moshi.Json

data class DetailTagResponse(
    @field:Json(name = "success")
    val success: Boolean,
    @field:Json(name = "posts")
    val posts: PostReady,
    @field:Json(name = "tag")
    val tag: String,
    )
