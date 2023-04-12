package com.panji.animalie.model

data class Certificate(
    val certificate_id: Int,
    val name: String,
    val organization_issue: String,
    val issue_date: String,
    val expiration_date: String,
    val credential_id: String,
    val credential_url: String,
    val certificate_image: String,
    val created_at: String,
    val updated_at: String
)
