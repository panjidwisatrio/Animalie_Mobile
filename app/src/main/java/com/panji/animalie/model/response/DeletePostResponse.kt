package com.panji.animalie.model.response

import com.squareup.moshi.Json

data class DeletePostResponse(
    @field:Json(name = "success")
    val success: Boolean,
    @field:Json(name = "message")
    val message: String
)