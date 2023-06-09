package com.panji.animalie.ui.notificationroom

import com.panji.animalie.data.repo.PostRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.panji.animalie.model.Notification

class ViewModelNotification(private val repository: PostRepository) : ViewModel() {
    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> get() = _notifications

//    init {
//        // Memuat notifikasi saat ViewModel dibuat
//        loadNotifications()
//    }

//    private fun loadNotifications() {
//        viewModelScope.launch {
//            try {
//                val notificationList = repository.getNotificationsForUser(user_id)
//                _notifications.value = notificationList
//            } catch (e: Exception) {
//                // Handle error
//            }
//        }
//    }
}
