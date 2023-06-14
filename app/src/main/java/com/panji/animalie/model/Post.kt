package com.panji.animalie.model

import com.squareup.moshi.Json

data class PostReady(
    @field:Json(name = "current_page")
    val currentPage: Int,
    @field:Json(name = "first_page_url")
    val firstPageUrl: String,
    @field:Json(name = "next_page_url")
    val nextPageUrl: String,
    @field:Json(name = "last_page")
    val lastPage: Int,
    @field:Json(name = "data")
    val data: List<Post>,
    @field:Json(name = "total")
    val total: Int,
)

data class Post(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "slug")
    val slug: String,
    @field:Json(name = "content")
    val content: String,
    @field:Json(name = "views")
    val views: Int,
    @field:Json(name = "userId")
    val userId: Int,
    @field:Json(name = "categoryId")
    val categoryId: Int,
    @field:Json(name = "created_at")
    val created_at: String,
    @field:Json(name = "updated_at")
    val updated_at: String,
    @field:Json(name = "user")
    val User: User,
    @field:Json(name = "category")
    val Category: Category,
    @field:Json(name = "tag")
    val Tag: List<Tag>,
    @field:Json(name = "comment")
    val Comments: List<Comment>,
    @field:Json(name = "like_count_api")
    val like: List<Like>,
    @field:Json(name = "bookmarked_by")
    val bookmarked_by: List<User>,
)
