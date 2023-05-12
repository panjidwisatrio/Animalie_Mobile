package com.panji.animalie.model

import com.squareup.moshi.Json

data class Tag(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name_tag")
    val name_tag: String,
    @field:Json(name = "slug")
    val slug: String,
    @field:Json(name = "tag_counter")
    val tag_counter: Int,
    @field:Json(name = "created_at")
    val created_at: String,
    @field:Json(name = "updated_at")
    val updated_at: String
)
