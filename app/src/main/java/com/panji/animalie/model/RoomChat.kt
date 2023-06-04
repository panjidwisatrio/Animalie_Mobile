package com.panji.animalie.model

data class RoomChat(
    val sender: String,
    val senderProfilePhoto: Int,
    val receiver: String,
    val receiverProfilePhoto: Int,
    val time: String,
    var messages: MutableList<Message> = mutableListOf()
)
//