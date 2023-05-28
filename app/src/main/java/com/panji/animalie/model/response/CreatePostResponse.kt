package com.panji.animalie.model.response

import com.panji.animalie.model.Category
import com.panji.animalie.model.Tag
import com.squareup.moshi.Json

data class CreatePostResponse(
    @field:Json(name = "success")
    val success: Boolean,
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "categories")
    val categories: List<Category>,
    @field:Json(name = "tags")
    val tags: List<Tag>
)
