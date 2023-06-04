package com.panji.animalie.model.response

import com.panji.animalie.model.PostReady
import com.panji.animalie.model.User

data class MyProfileResponse(
    val user: User,
    val postResponse: PostReady,
)