package com.panji.animalie.model.response

import com.panji.animalie.model.PostReady
import com.panji.animalie.model.Tag

data class PostResponse(
    val messages: String,
    val posts: PostReady,
    val tags: List<Tag>
)
