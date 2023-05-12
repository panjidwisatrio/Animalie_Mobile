package com.panji.animalie.model

import com.squareup.moshi.Json

data class Category(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "category")
    val category: String,
    @field:Json(name = "slug")
    val slug: String,
    @field:Json(name = "created_at")
    val created_at: String,
    @field:Json(name = "updated_at")
    val updated_at: String
)
