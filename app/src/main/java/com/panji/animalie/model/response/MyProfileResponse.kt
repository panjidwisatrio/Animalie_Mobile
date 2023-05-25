package com.panji.animalie.model.response

import com.panji.animalie.model.PostReady
import com.panji.animalie.model.User
import com.squareup.moshi.Json

data class MyProfileResponse(
    @field:Json(name = "user")
    val user: User,
    @field:Json(name = "post")
    val postResponse: PostReady,
)