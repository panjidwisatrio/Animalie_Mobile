package com.panji.animalie.model

data class Notification(
    val name: String,
    val description: String,
    val type: NotificationType,
    var isRead: Boolean = false,
    val profilePhotoUrl: Int
)

enum class NotificationType {
    MESSAGE,
    LIKE,
    COMMENT
}





