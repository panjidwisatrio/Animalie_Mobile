package com.panji.animalie.model.response

import com.panji.animalie.model.Tag
import com.squareup.moshi.Json

data class TagStoreResponse(
    @field:Json(name = "success")
    val success: Boolean,
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "tags")
    val tags: Tag
)
