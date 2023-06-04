package com.panji.animalie.ui.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.panji.animalie.databinding.MessageHistoryItemBinding
import com.panji.animalie.model.MessageHistory

class MessageHistoryAdapter(private val messages: List<MessageHistory>) : RecyclerView.Adapter<MessageHistoryAdapter.MessageViewHolder>() {

    private var onItemClickListener: ((MessageHistory) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun setOnItemClickListener(listener: (MessageHistory) -> Unit) {
        onItemClickListener = listener
    }

    inner class MessageViewHolder(private val binding: MessageHistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bind(message: MessageHistory) {
            // Mengatur data ke tampilan
            binding.senderPhoto.setImageResource(message.photoProfile)
            binding.senderName.text = message.contactName
            binding.previewLastMessage.text = message.lastMessageText

            // Memberikan efek memudar saat item dihover
            binding.root.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.alpha = 0.5f // Ubah alpha menjadi 0.5 saat disentuh
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        v.alpha = 1f // Kembalikan alpha ke 1 setelah tidak disentuh atau batal
                    }
                }
                false
            }

            // Menangani klik item pesan
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(message)
            }
        }
    }
}
