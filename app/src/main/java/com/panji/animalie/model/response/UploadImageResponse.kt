package com.panji.animalie.model.response

import com.squareup.moshi.Json

data class UploadImageResponse(
    @field:Json(name = "uploaded")
    val uploaded: Int,
    @field:Json(name = "fileName")
    val fileName: String,
    @field:Json(name = "url")
    val url: String
)