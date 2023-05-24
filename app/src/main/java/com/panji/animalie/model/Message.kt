package com.panji.animalie.model

data class Message(
    val messageId: String,
    val userId: String,
    val receiverId: String,
    val messageText: String,
    val senderProfileImage: String,
    val receiverProfileImage: String
)

