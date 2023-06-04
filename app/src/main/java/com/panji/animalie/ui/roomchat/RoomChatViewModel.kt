package com.panji.animalie.ui.roomchat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.panji.animalie.model.Message
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RoomChatViewModel : ViewModel() {
    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    private val messageList = mutableListOf<Message>()

    fun sendMessage(content: String, photoProfile: Int) {
        val message = Message(content, getCurrentTimeStamp(), true, photoProfile)
        messageList.add(message)
        _messages.value = messageList.toList()
    }
//
    private fun getCurrentTimeStamp(): String {
        val timeStampFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = Calendar.getInstance().time
        return timeStampFormat.format(currentTime)
    }
}
