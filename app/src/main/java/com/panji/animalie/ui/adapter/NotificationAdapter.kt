package com.panji.animalie.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panji.animalie.R
import com.panji.animalie.model.Notification

class NotificationAdapter : ListAdapter<Notification, NotificationAdapter.NotificationViewHolder>(
    NotificationDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification, parent, false)
        return NotificationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = getItem(position)
        holder.bind(notification)
    }

    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profilePhoto: ImageView = itemView.findViewById(R.id.profile_photo)
        private val personName: TextView = itemView.findViewById(R.id.person_name)
        private val badge: ImageView = itemView.findViewById(R.id.verification_badge)
        private val notificationText: TextView = itemView.findViewById(R.id.Specialist_doctor)

        fun bind(notification: Notification) {
            // Set profile photo using Glide or your preferred image loading library
            Glide.with(itemView.context)
                .load(notification.profileImage)
                .into(profilePhoto)

            personName.text = notification.personName
            badge.visibility = if (notification.isDoctor) View.VISIBLE else View.GONE

            // Set the notification text based on the notification type
            notificationText.text = when (notification.notificationType) {
                Notification.NotificationType.CHAT -> itemView.context.getString(R.string.set_notifications_Chat)
                Notification.NotificationType.LIKE -> itemView.context.getString(R.string.set_notification_like)
                Notification.NotificationType.COMMENT -> itemView.context.getString(R.string.set_notification_comment)
            }
        }
    }
}

class NotificationDiffCallback : DiffUtil.ItemCallback<Notification>() {
    override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem == newItem
    }
}