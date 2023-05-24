package com.panji.animalie.model

import com.squareup.moshi.Json

data class Like(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "post_id")
    val post_id: Int,
    @field:Json(name = "post_type")
    val postType: String,
    @field:Json(name = "count")
    val count: Int
)
