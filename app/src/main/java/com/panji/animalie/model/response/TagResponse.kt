package com.panji.animalie.model.response

import com.panji.animalie.model.Tag
import com.squareup.moshi.Json

data class TagResponse(
    @field:Json(name = "tags")
    val tag: List<Tag>
)
