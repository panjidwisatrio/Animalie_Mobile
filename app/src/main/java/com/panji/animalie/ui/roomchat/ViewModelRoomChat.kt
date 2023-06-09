package com.panji.animalie.ui.roomchat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.panji.animalie.model.Message

class MainViewModelRoomChat : ViewModel() {
//    private val repository: Repository = Repository()

    private val _chatMessages = MutableLiveData<List<Message>>()
    val chatMessages: LiveData<List<Message>> = _chatMessages

//    fun getChatMessages() {
//        val messages = repository.getChatMessages()
//        _chatMessages.value = messages
//    }
//
//    fun sendMessage(message: Message) {
//        repository.sendMessage(message)
//    }
}

