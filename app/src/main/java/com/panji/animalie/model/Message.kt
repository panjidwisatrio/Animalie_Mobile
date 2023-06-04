package com.panji.animalie.model

data class Message(
    val content: String,
    val timeStamp: String, val
    isSentByUser: Boolean = true,
    val photoProfile: Int
)
//