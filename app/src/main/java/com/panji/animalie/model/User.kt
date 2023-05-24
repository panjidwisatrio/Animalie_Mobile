package com.panji.animalie.model

import com.squareup.moshi.Json


data class User(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "username")
    val username: String,
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "work_place")
    val work_place: String? = null,
    @field:Json(name = "job_position")
    val job_position: String? = null,
    @field:Json(name = "avatar")
    val avatar: String? = null,
    @field:Json(name = "email_verified_at")
    val email_verified_at: String? = null,
    @field:Json(name = "created_at")
    val created_at: String,
    @field:Json(name = "updated_at")
    val updated_at: String,
)