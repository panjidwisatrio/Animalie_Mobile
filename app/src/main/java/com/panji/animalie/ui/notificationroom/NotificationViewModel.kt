package com.panji.animalie.ui.notificationroom


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.panji.animalie.R
import com.panji.animalie.model.Notification
import com.panji.animalie.model.NotificationType

class NotificationViewModel : ViewModel() {
    private val _notifications: MutableLiveData<List<Notification>> = MutableLiveData()
    val notifications: LiveData<List<Notification>>
        get() = _notifications

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        // Simulate loading notifications from a data source
        val dummyNotifications = createDummyNotifications()
        _notifications.value = dummyNotifications
    }

    private fun createDummyNotifications(): List<Notification> {
        return listOf(
            Notification("John Doe", getNotificationDescription(NotificationType.LIKE), NotificationType.LIKE, false, R.drawable.doctor_photo),
            Notification("Jane Smith", getNotificationDescription(NotificationType.COMMENT), NotificationType.COMMENT, false, R.drawable.doctor_photo),
            Notification("Adam Johnson", getNotificationDescription(NotificationType.MESSAGE), NotificationType.MESSAGE, false, R.drawable.doctor_photo)
        )
    }


    private fun getNotificationDescription(type: NotificationType): String {
        return when (type) {
            NotificationType.LIKE -> "Menyukai postingan Anda"
            NotificationType.COMMENT -> "Mengomentari postingan Anda"
            NotificationType.MESSAGE -> "Anda memiliki pesan masuk"
        }
    }
}
