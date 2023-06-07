package com.panji.animalie.ui.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panji.animalie.R
import com.panji.animalie.databinding.NotificationBinding
import com.panji.animalie.model.Notification
import com.panji.animalie.model.NotificationType

class NotificationAdapter(private val onNotificationClicked: (Notification) -> Unit) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    private val notifications: MutableList<Notification> = mutableListOf()

    fun setNotifications(notificationList: List<Notification>) {
        notifications.clear()
        notifications.addAll(notificationList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NotificationBinding.inflate(inflater, parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]
        holder.bind(notification)
    }

    override fun getItemCount(): Int = notifications.size

    inner class NotificationViewHolder(private val binding: NotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: Notification) {
            binding.nameNotification.text = notification.name
            binding.noficationTypes.text = getNotificationDescription(notification.type)

            Glide.with(binding.root.context)
                .load(notification.profilePhotoUrl)
                .into(binding.photoProfileNotification)

            val backgroundColorRes = if (notification.isRead) {
                R.color.notification_background_read
            } else {
                R.color.notification_background_unread
            }
            val backgroundColor = ContextCompat.getColor(itemView.context, backgroundColorRes)
            binding.backgroundNotification.setCardBackgroundColor(backgroundColor)

            binding.backgroundNotification.setOnClickListener {
                if (!notification.isRead) {
                    notification.isRead = true
                    markAsRead()

                    val backgroundTintColorRes = if (notification.isRead) {
                        R.color.notification_background_read
                    } else {
                        R.color.notification_background_unread
                    }
                    val backgroundTintColor = ContextCompat.getColor(itemView.context, backgroundTintColorRes)
                    binding.backgroundNotification.setCardBackgroundColor(backgroundTintColor)
                }
                onNotificationClicked(notification)
            }
        }

        private fun getNotificationDescription(type: NotificationType): String {
            return when (type) {
                NotificationType.LIKE -> "Menyukai postingan"
                NotificationType.COMMENT -> "Mengomentari postingan Anda"
                NotificationType.MESSAGE -> "Anda memiliki pesan masuk"
            }
        }

        private fun markAsRead() {
            // Mark the notification as read, for example, update the status in the database
        }
    }
}
