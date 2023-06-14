package com.panji.animalie.model.response

import com.squareup.moshi.Json

data class EditProfileResponse(
    @field:Json(name = "message")
    val message: String,
)