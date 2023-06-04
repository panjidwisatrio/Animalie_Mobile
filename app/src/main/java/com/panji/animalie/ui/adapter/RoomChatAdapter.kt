package com.panji.animalie.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panji.animalie.R
import com.panji.animalie.databinding.ReceiverBubbleChatBinding
import com.panji.animalie.databinding.SenderBubbleChatBinding
import com.panji.animalie.model.Message

class RoomChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val messages = mutableListOf<Message>()

    fun setMessages(messageList: List<Message>) {
        messages.clear()
        messages.addAll(messageList)
        notifyDataSetChanged()
    }
//
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SENDER -> {
                val binding = SenderBubbleChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SenderViewHolder(binding)
            }
            VIEW_TYPE_RECEIVER -> {
                val binding = ReceiverBubbleChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ReceiverViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        when (holder) {
            is SenderViewHolder -> {
                holder.bind(message)
            }
            is ReceiverViewHolder -> {
                holder.bind(message)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.isSentByUser) {
            VIEW_TYPE_SENDER
        } else {
            VIEW_TYPE_RECEIVER
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    class SenderViewHolder(private val binding: SenderBubbleChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.senderMessage.text = message.content
            binding.timeStampSender.text = message.timeStamp

            // Tampilkan foto profil pengirim
            Glide.with(binding.root.context)
                .load(message.photoProfile)
                .placeholder(R.drawable.testingphoto) // Foto profil default jika tidak ada foto profil pengirim
                .into(binding.senderPhotoProfile)
        }
    }

    class ReceiverViewHolder(private val binding: ReceiverBubbleChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.receiverMessage.text = message.content
            binding.timeStampReceiver.text = message.timeStamp

            // Tampilkan foto profil penerima
            Glide.with(binding.root.context)
                .load(message.photoProfile)
                .placeholder(R.drawable.doctor_photo) // Foto profil default jika tidak ada foto profil penerima
                .into(binding.receiverPhotoProfile)
        }
    }

    companion object {
        private const val VIEW_TYPE_SENDER = 0
        private const val VIEW_TYPE_RECEIVER = 1
    }
}













//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.panji.animalie.R
//import com.panji.animalie.databinding.ReceiverBubbleChatBinding
//import com.panji.animalie.databinding.SenderBubbleChatBinding
//import com.panji.animalie.model.Message
//import com.panji.animalie.model.RoomChat
//import java.text.SimpleDateFormat
//import java.util.*
//
//class RoomChatAdapter(private val roomChat: RoomChat) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    companion object {
//        private const val VIEW_TYPE_SENDER = 1
//        private const val VIEW_TYPE_RECEIVER = 2
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//            VIEW_TYPE_SENDER -> {
//                val binding = SenderBubbleChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//                SenderViewHolder(binding)
//            }
//            VIEW_TYPE_RECEIVER -> {
//                val binding = ReceiverBubbleChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//                ReceiverViewHolder(binding)
//            }
//            else -> throw IllegalArgumentException("Invalid view type")
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val message = roomChat.messages[position]
//
//        when (holder) {
//            is SenderViewHolder -> {
//                holder.bind(message)
//            }
//            is ReceiverViewHolder -> {
//                holder.bind(message)
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return roomChat.messages.size
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        val message = roomChat.messages[position]
//        return if (message.sender == roomChat.sender) {
//            VIEW_TYPE_SENDER
//        } else {
//            VIEW_TYPE_RECEIVER
//        }
//    }
//
//    inner class SenderViewHolder(private val binding: SenderBubbleChatBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(message: Message) {
//            binding.senderMessage.text = message.content
//            binding.timeStampSender.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(message.time)
//
//            // Tampilkan foto profil pengirim
//            Glide.with(binding.root.context)
//                .load(message.senderProfilePhoto)
//                .placeholder(R.drawable.testingphoto) // Foto profil default jika tidak ada foto profil pengirim
//                .into(binding.senderPhotoProfile)
//        }
//    }
//
//    inner class ReceiverViewHolder(private val binding: ReceiverBubbleChatBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(message: Message) {
//            binding.receiverMessage.text = message.content
//            binding.timeStampReceiver.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(message.time)
//
//            // Tampilkan foto profil penerima
//            Glide.with(binding.root.context)
//                .load(roomChat.receiverProfilePhoto)
//                .placeholder(R.drawable.testingphoto) // Foto profil default jika tidak ada foto profil penerima
//                .into(binding.receiverPhotoProfile)
//        }
//    }
//}
//
