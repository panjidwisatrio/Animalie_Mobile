package com.panji.animalie.ui.roomchat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.panji.animalie.databinding.ActivityRoomChatBinding
import com.panji.animalie.ui.adapter.RoomChatAdapter

class RoomChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomChatBinding
    private lateinit var viewModel: MainViewModelRoomChat
    private lateinit var roomChatAdapter: RoomChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModelRoomChat::class.java)

        // Inisialisasi RoomChatAdapter
        roomChatAdapter = RoomChatAdapter(userId = "your_user_id") // Ganti dengan user ID
        binding.itemChatBubble.apply {
            layoutManager = LinearLayoutManager(this@RoomChatActivity)
            adapter = roomChatAdapter
        }

        // Mengamati perubahan pada daftar pesan chat
        viewModel.chatMessages.observe(this) { messages ->
            roomChatAdapter.setChatMessages(messages)
            binding.itemChatBubble.scrollToPosition(messages.size - 1) // Auto scroll ke pesan terbaru
        }

//        // Mengirim pesan saat tombol send ditekan
//        binding.SendMessageButton.setOnClickListener {
//            val messageText = binding.SendingMessageTextView.text.toString().trim()
//            if (messageText.isNotEmpty()) {
//                val message = Message(
//                    messageId = "message_id", // Ganti dengan ID pesan
//                    userId = "your_user_id", // Ganti dengan user ID
//                    receiverId = "receiver_user_id", // Ganti dengan user ID penerima pesan
//                    messageText = messageText,
//                    senderProfileImage = "sender_profile_image_url", // Ganti dengan URL gambar profil pengirim
//                    receiverProfileImage = "receiver_profile_image_url" // Ganti dengan URL gambar profil penerima
//                )
//                viewModel.sendMessage(message)
////                binding.SendingMessageTextView.text.clear() // Menghapus teks pada input pesan
//            } else {
//                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
//            }
//        }

        // Mengambil data kontak yang dipilih dari halaman sebelumnya (misalnya halaman kontak)
        val receiverName = intent.getStringExtra("receiver_name")
        binding.receiverNameChat.text = receiverName // Menampilkan nama penerima pesan pada toolbar
    }
}
