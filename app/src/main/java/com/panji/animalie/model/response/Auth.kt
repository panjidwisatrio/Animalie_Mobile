package com.panji.animalie.model.response

import com.panji.animalie.model.User
import com.squareup.moshi.Json

data class Auth(
    @field:Json(name = "user")
    val user: User,
    @field:Json(name = "access_token")
    val access_token: String,
    @field:Json(name = "token_type")
    val token_type: String,
)
