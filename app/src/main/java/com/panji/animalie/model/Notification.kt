package com.panji.animalie.model

data class Notification(
    val id: Int,
    val profileImage: Int,
    val personName: String,
    val isDoctor: Boolean,
    val notificationType: NotificationType
) {
    enum class NotificationType {
        CHAT,
        LIKE,
        COMMENT
    }
}
