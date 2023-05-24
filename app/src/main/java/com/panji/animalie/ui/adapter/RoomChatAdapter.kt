package com.panji.animalie.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panji.animalie.R
import com.panji.animalie.model.Message


class RoomChatAdapter(private val userId: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val chatMessages = mutableListOf<Message>()

    fun setChatMessages(messages: List<Message>) {
        chatMessages.clear()
        chatMessages.addAll(messages)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENDER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.sender_bubble_chat, parent, false)
            SenderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.receiver_bubble_chat, parent, false)
            ReceiverViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatMessage = chatMessages[position]

        if (holder.itemViewType == VIEW_TYPE_SENDER) {
            val senderViewHolder = holder as SenderViewHolder
            senderViewHolder.bind(chatMessage)
        } else {
            val receiverViewHolder = holder as ReceiverViewHolder
            receiverViewHolder.bind(chatMessage)
        }
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    override fun getItemViewType(position: Int): Int {
        val chatMessage = chatMessages[position]
        return if (chatMessage.userId == userId) {
            VIEW_TYPE_SENDER
        } else {
            VIEW_TYPE_RECEIVER
        }
    }

    inner class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(chatMessage: Message) {
            itemView.findViewById<TextView>(R.id.sender_message).text = chatMessage.messageText

            // Set profile photo using Glide or your preferred image loading library
            Glide.with(itemView.context)
                .load(chatMessage.senderProfileImage)
                .into(itemView.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.sender_photo_profile))
        }
    }

    inner class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(chatMessage: Message) {
            itemView.findViewById<TextView>(R.id.receiver_message).text = chatMessage.messageText

            // Set profile photo using Glide or your preferred image loading library
            Glide.with(itemView.context)
                .load(chatMessage.receiverProfileImage)
                .into(itemView.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.receiver_photo_profile))
        }
    }

    companion object {
        private const val VIEW_TYPE_SENDER = 1
        private const val VIEW_TYPE_RECEIVER = 2
    }
}
