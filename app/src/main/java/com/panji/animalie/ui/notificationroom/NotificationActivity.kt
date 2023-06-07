package com.panji.animalie.ui.notificationroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.panji.animalie.databinding.ActivityNotificationBinding
import com.panji.animalie.model.Notification
import com.panji.animalie.ui.adapter.NotificationAdapter


class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var viewModel: NotificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)

        notificationAdapter = NotificationAdapter { notification ->
            onNotificationClicked(notification)
        }

        binding.notificationItem.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity)
            adapter = notificationAdapter
        }

        viewModel.notifications.observe(this) { notifications ->
            notificationAdapter.setNotifications(notifications)
        }
    }

    private fun onNotificationClicked(notification: Notification) {
        // Handle notification click event
        // For example, navigate to a specific screen based on the notification type
        // You can also perform additional actions here, such as marking the notification as read in the database or updating its status
    }
}
